package com.anabada.anabada.item.model.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto {
    private int pageSize;
    private List<ItemFindResponse> items;

    public PageResponseDto(int pageSize, List<ItemFindResponse> items){
        this.pageSize = pageSize;
        this.items = items;
    }

}
