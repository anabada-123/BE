package com.anabada.anabada.item.service;

import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.exception.type.ItemErrorCode;
import com.anabada.anabada.global.util.S3Utill;
import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.repository.ItemRepository;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<ItemFindResponse> getItem() {
        return itemRepository.findAll().stream().map(ItemFindResponse::new).toList();
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
                    String fileName = name + System.nanoTime();
                    s3Utill.saveFile(file, fileName);
                    images.add(fileName);
                }
                item.updateImges(images); //이미지 파일 수정
            }
            if (!Objects.isNull(mainImg)) {
                s3Utill.deleteImage(item.getImg());
                String fileName = name + System.nanoTime();
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

        for (MultipartFile file : files) {
            String fileName = name + System.nanoTime();

            String url = s3Utill.saveFile(file, fileName);
            System.out.println(url);

            images.add(fileName);
        }
        String img = name + System.nanoTime();
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
}
