package com.anabada.anabada.item.service;

import com.anabada.anabada.item.repository.ItemRepository;
import com.anabada.anabada.item.response.ItemFindResponse;
import com.anabada.anabada.global.util.FileUtil;
import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import com.anabada.anabada.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final FileUtil fileUtil;
    private final ItemRepository itemRepository;

    private String DEFAULT_IMG = "defulat\\기본이미지.png";

    //상품 전체 조회
    public List<ItemFindResponse> getItem() {
        return itemRepository.findAll().stream().map(ItemFindResponse::new).toList();
    }

    public void itemSave(List<MultipartFile> files, MultipartFile mainImg, ItemCreateRequest request) {

        //TODO: 로그인 기능 도입시 수정.
        String name = "test" + new Date().getTime();
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
}
