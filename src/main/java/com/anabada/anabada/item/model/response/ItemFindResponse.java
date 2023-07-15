package com.anabada.anabada.item.model.response;

import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ItemFindResponse {
    private Long id;
    private String itemName;
    private String itemContent;
    private String tradingPosition;
    private String tradingItem;
    private String img;
    private List<String> imgList;
    private LocalDateTime day;

    public ItemFindResponse(Item item){
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemContent = item.getItemContent();
        this.tradingPosition = item.getTradingPosition();
        this.tradingItem = item.getTradingItem();
        this.img = item.getImg();
        this.imgList = item.getImgList();
        this.day = item.getCreatedAt();
    }



}
