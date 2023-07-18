package com.anabada.anabada.item.service;

import com.anabada.anabada.global.exception.FileException;
import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.exception.type.FileErrorCode;
import com.anabada.anabada.global.exception.type.ItemErrorCode;
import com.anabada.anabada.global.util.S3Utill;
import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.repository.ItemRepository;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.item.model.response.PageResponseDto;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import com.anabada.anabada.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final S3Utill s3Utill;

    private String DEFAULT_IMG = "defulat\\기본이미지.png";

    //상품 전체 조회
    @Transactional(readOnly = true)
    public PageResponseDto getItem(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Item> itemPage = itemRepository.findAll(pageable);
        List<ItemFindResponse> itemFindResponses = itemPage.getContent().stream()
                .map(ItemFindResponse::new)
                .toList();

        return new PageResponseDto(itemPage.getTotalPages(), itemFindResponses);
    }

    //선택한 상품 조회
    @Transactional(readOnly = true)
    public ItemFindResponse getItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemException(ItemErrorCode.ITEM_NULL));

        ItemFindResponse itemFindResponse = new ItemFindResponse(item);
        itemFindResponse.getImgList().add(itemFindResponse.getImg());
        return itemFindResponse;
    }

    //게시글 삭제
    public Long deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemException(ItemErrorCode.ITEM_NULL));

        itemRepository.delete(item);

        for (String s : item.getImgList()) {
            s3Utill.deleteImage(s);
        }
        s3Utill.deleteImage(item.getTradingItem());

        return id;
    }


    @Transactional
    public void itemUpdate(Long itemId, List<MultipartFile> files, ItemUpdateRequest request) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    throw new ItemException(ItemErrorCode.ITEM_NULL);
                });

        //TODO: 로그인 기능 도입시 수정.
        String name = "test";

        List<String> imgList = new ArrayList<>();

        String img = "";

        for (String itemimg : item.getImgList()) {
            boolean check = true;
            for (String inputImgName : request.imgNameList()) {

                if (itemimg.equals(inputImgName)) {
                    check = false;
                }

            }
            //mainImg 체크
            if (itemimg.equals(request.mainImgName())) {
                img = itemimg;
            }

            //해당 경로의 사진을 다시 업데이트할때 안올리게 된다면.
            if (check) {
                s3Utill.deleteImage(itemimg);
                continue;
            }

            imgList.add(itemimg);

        }
        System.out.println("files : "+files);
        if (!Objects.isNull(files)) {
            for (MultipartFile file : files) {

                //메인 이미지만 따로 처리 하기 위한 작업.
                System.out.println(file.getOriginalFilename());
                System.out.println(request.mainImgName());
                if (file.getOriginalFilename().equals(request.mainImgName())) {
                    img = name + System.nanoTime() + getExtension(file);
                    s3Utill.saveFile(file, img);
                    continue;
                }

                String fileName = name + System.nanoTime() + getExtension(file);
                s3Utill.saveFile(file, fileName);
                imgList.add(fileName);
            }

        }

        //이미지 수정 쿼리 더티 체킹
        item.updateImges(imgList);
        item.updateMainImg(img);
        item.updateItemAll(
                request.itemName(),
                request.itemContent(),
                request.itemOneContent(),
                request.tradingPosition(),
                request.tradingItem(),
                getCate(request.cate()),
                getStatus(request.status())
        );

    }

    @Transactional
    public void itemSave(List<MultipartFile> files,
                         ItemCreateRequest request) {

        //TODO: 로그인 기능 도입시 수정.
        String name = "test";
        String img = "";

        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            System.out.println(" : "+file.getOriginalFilename());
            //메인 이미지만 따로 처리 하기 위한 작업.
            if (file.getOriginalFilename().equals(request.mainImgName())) {
                img = name + System.nanoTime() + getExtension(file);
                s3Utill.saveFile(file, img);
                continue;
            }

            String fileName = name + System.nanoTime() + getExtension(file);
            s3Utill.saveFile(file, fileName);
            images.add(fileName);
        }


        itemRepository.save(
                Item.builder()
                        .itemName(request.itemName())
                        .itemContent(request.itemContent())
                        .itemOneContent(request.itemOneContent())
                        .tradingItem(request.tradingItem())
                        .tradingPosition(request.tradingPosition())
                        .img(img)
                        .imgList(images)
                        .cate(getCate(request.cate()))
                        .status(ItemStatus.SELLING)
                        .build()
        );

    }

    private ItemCate getCate(String cate) {

        //TODO:추가할 예정 부가 기능.
        return switch (cate) {
            default -> ItemCate.ETC;
        };
    }

    private ItemStatus getStatus(String status) {
        return switch (status) {
            case "판매중" -> ItemStatus.SELL;
            case "판매완료" -> ItemStatus.SELLING;
            default -> throw new ItemException(ItemErrorCode.ITEM_STATUS_ERROR);
        };
    }

    private String getExtension(MultipartFile file) {
        String contextType = file.getContentType();
        String extension = null;
        if (StringUtils.hasText(contextType)) {
            if (contextType.contains("image/jpeg")) {
                extension = ".jpeg";
            } else if (contextType.contains("image/png")) {
                extension = ".png";
            } else if (contextType.contains("image/jpg")) {
                extension = ".jpg";
            }
            return extension;
        }
        throw new FileException(FileErrorCode.FILE_ERROR);
    }

}
