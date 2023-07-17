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
    public void itemUpdate(Long itemId, List<MultipartFile> files, MultipartFile mainImg, ItemUpdateRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    throw new ItemException(ItemErrorCode.ITEM_NULL);
                });

        //TODO: 로그인 기능 도입시 수정.
        String name = "test";

        //TODO: check = 1 면 파일 변화 / 그 외 파일변화 X
        if (request.check()) {
            if (!Objects.isNull(files)) {
                for (String fileName : item.getImgList()) {
                    s3Utill.deleteImage(fileName);
                }
                List<String> images = new ArrayList<>();
                for (MultipartFile file : files) {
                    String fileName = name + System.nanoTime() + getExtension(file);
                    s3Utill.saveFile(file, fileName);
                    images.add(fileName);
                }
                item.updateImges(images); //이미지 파일 수정
            }
            if (!Objects.isNull(mainImg)) {
                s3Utill.deleteImage(item.getImg());
                String fileName = name + System.nanoTime() + getExtension(mainImg);
                s3Utill.saveFile(mainImg, fileName);
                item.updateMainImg(fileName); //이미지 파일 수정
            }
        }

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
    public void itemSave(List<MultipartFile> files, MultipartFile mainImg,
                         ItemCreateRequest request) {

        //TODO: 로그인 기능 도입시 수정.
        String name = "test";

        List<String> images = new ArrayList<>();
        if(!Objects.isNull(files)){
            for (MultipartFile file : files) {
                String fileName = name + System.nanoTime() + getExtension(file);
                s3Utill.saveFile(file, fileName);
                images.add(fileName);
            }
        }

        String img = name + System.nanoTime() + getExtension(mainImg);
        s3Utill.saveFile(mainImg, img);

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
