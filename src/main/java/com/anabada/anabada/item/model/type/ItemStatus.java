package com.anabada.anabada.item.model.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemStatus {

    SELLING("판매중"),
    SELL("판매 완료")
    ;

    private final String status;

}
