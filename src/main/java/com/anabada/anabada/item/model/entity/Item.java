package com.anabada.anabada.item.model.entity;

import com.anabada.anabada.item.model.type.ItemCate;
import com.anabada.anabada.item.model.type.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String itemName;

    @Column
    private String itemContent;

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


}
