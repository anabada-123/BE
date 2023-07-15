package com.anabada.anabada.item.model.entity;

import com.anabada.anabada.global.model.entity.Auditing;
import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String itemName;

    @Column
    private String itemContent;

    @Column
    private String itemOneContent;

    @Column
    private String tradingPosition;

    @Column
    private String tradingItem;

    @Column
    @Enumerated
    private ItemCate cate;

    @Column
    @Enumerated
    private ItemStatus status;

    @Column
    private String img;

    @Column
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> imgList;

    public void updateMainImg(String img){
        this.img = img;
    }

    public void updateImges(List<String> imgList){
        this.imgList = imgList;
    }

    public void updateItemAll(
            String itemName,
            String itemContent,
            String itemOneContent,
            String tradingPosition,
            String tradingItem,
            ItemCate cate,
            ItemStatus status
            ) {
        this.itemName = itemName;
        this.itemContent = itemContent;
        this.itemOneContent = itemOneContent;
        this.tradingPosition = tradingPosition;
        this.tradingItem = tradingItem;
        this.cate = cate;
        this.status = status;
    }

}
