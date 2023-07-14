package com.anabada.anabada.item.controller;

import com.anabada.anabada.item.response.ItemFindResponse;
import com.anabada.anabada.item.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //상품 전체 조회
    @GetMapping("/")
    public List<ItemFindResponse> getItem(){
        return itemService.getItem();
    }

}
