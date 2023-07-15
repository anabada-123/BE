package com.anabada.anabada.item.service;

import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.exception.type.ItemErrorCode;
import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.repository.ItemRepository;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.global.util.FileUtil;
import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final FileUtil fileUtil;
    private final ItemRepository itemRepository;

    private String DEFAULT_IMG = "defulat\\기본이미지.png";
    //TODO: EC2 AWS 배포 한후 IP 적어야함.
    private String serverIp = "";

    //상품 전체 조회
    @Transactional(readOnly = true)
    public List<ItemFindResponse> getItem(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction,"");

        Pageable pageable = PageRequest.of(page, size, sort);
        //내가 몇 페이지 에 몇개의 데이터를 어떤 정렬를 해서 가져올거야. 선언.

        Page<Item> itemPage = itemRepository.findAll(pageable);
        //-> Jpa 아! 몇 페이지에 몇개의 데이터를 어떤 정렬로 해서 데이터를 가져온다.

        List<ItemFindResponse> itemFindResponses = itemPage.getContent().stream().map(ItemFindResponse::new).toList();

        return itemFindResponses;
    }

    //선택한 상품 조회
    @Transactional(readOnly = true)
    public ItemFindResponse getItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                ()-> new ItemException(ItemErrorCode.ITEM_NULL));

        ItemFindResponse itemFindResponse = new ItemFindResponse(item);
        return itemFindResponse;
    }

    //게시글 삭제
    public Long deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                ()-> new ItemException(ItemErrorCode.ITEM_NULL));

        itemRepository.delete(item);
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
                    fileUtil.deleteFile(fileName);
                }
                List<String> images = getImages(files, name);
                item.updateImges(images); //이미지 파일 수정
            }
            if (!Objects.isNull(mainImg)) {
                fileUtil.deleteFile(item.getImg());
                String img = getImages(mainImg, name);
                item.updateMainImg(img); //이미지 파일 수정
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
    public void itemSave(List<MultipartFile> files, MultipartFile mainImg, ItemCreateRequest request) {

        //TODO: 로그인 기능 도입시 수정.
        String name = "test";
        List<String> images = getImages(files, name);
        String img = getImages(mainImg, name);
        //

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


    private List<String> getImages(List<MultipartFile> files, String userId) {

        List<String> imageList = new ArrayList<>();
        if (Objects.isNull(files)) {
            imageList.add(DEFAULT_IMG);
        } else {
            return fileUtil.saveFileList(userId, files);
        }
        return imageList;

    }

    private String getImages(MultipartFile files, String userId) {

        if (Objects.isNull(files)) {
            return DEFAULT_IMG;
        }
        return fileUtil.saveFileOne(userId, files);

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
}
