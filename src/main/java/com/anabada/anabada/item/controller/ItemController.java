package com.anabada.anabada.item.controller;

import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.item.model.response.PageResponseDto;
import com.anabada.anabada.item.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //상품 조회
    @GetMapping("")
    public PageResponseDto getItem(
            @RequestParam("page") int page)
    {
        return itemService.getItem(page-1, 10);
    }


    //선택한 상품 조회
    @GetMapping("/{id}")
    public ItemFindResponse getItem(@PathVariable Long id){
        return itemService.getItem(id);
    }


    //게시글 삭제
    @DeleteMapping("/{id}")
    public Long deleteItem(@PathVariable Long id){
        return itemService.deleteItem(id);
    }


    //TODO: 로그인 기능 추가시 아이디 넣어야함.
    @PostMapping
    public void itemSave(
            @RequestPart(value = "item", required = false) @Valid ItemCreateRequest request,
            @RequestPart(value = "img") List<MultipartFile> files
    ) {
        itemService.itemSave(files, request);
    }


    @PutMapping("/{id}")
    public void itemUpdate(
            @RequestPart(value = "item", required = false) @Valid ItemUpdateRequest request,
            @RequestPart(value = "img", required = false) List<MultipartFile> files,
            @PathVariable("id") Long itemId
    ) {
        itemService.itemUpdate(itemId, files, request);
    }


}
