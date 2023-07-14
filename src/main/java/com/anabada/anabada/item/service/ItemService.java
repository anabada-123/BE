package com.anabada.anabada.item.service;

import com.anabada.anabada.item.repository.ItemRepository;
import com.anabada.anabada.item.response.ItemFindResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //상품 전체 조회
    public List<ItemFindResponse> getItem() {
        return itemRepository.findAll().stream().map(ItemFindResponse::new).toList();
    }
}
