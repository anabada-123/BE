package com.anabada.anabada.item.model.response;

import com.anabada.anabada.item.model.entity.Item;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemFindResponse {

    private Long id;
    private String itemName;
    private String itemContent;
    private String itemOneContent;
    private String tradingPosition;
    private String tradingItem;
    private String img;
    private List<String> imgList;
    private LocalDateTime day;

    public ItemFindResponse(Item item){
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemContent = item.getItemContent();
        this.itemOneContent = item.getItemOneContent();
        this.tradingPosition = item.getTradingPosition();
        this.tradingItem = item.getTradingItem();
        this.img = "https://anabada123.s3.ap-northeast-2.amazonaws.com/" + item.getImg();
        this.imgList = new ArrayList<>();
        for (String imgUrl : item.getImgList()) {
            String s = "https://anabada123.s3.ap-northeast-2.amazonaws.com/" + imgUrl;
            this.imgList.add(s);
        }
        this.day = item.getCreatedAt();
    }

}
