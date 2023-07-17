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
               //100개 -> 10개
        return itemService.getItem(page-1, 10);
    }          //1 2 3 4 5 -> 몇번째 페이지까지 있는지.


    //선택한 상품 조회
    @GetMapping("/{id}")
    public ItemFindResponse getItem(@PathVariable Long id){
        return itemService.getItem(id);
    }


    //게시글 삭제
    @DeleteMapping("{id}")
    public Long deleteItem(@PathVariable Long id){
        return itemService.deleteItem(id);
    }


    //TODO: 로그인 기능 추가시 아이디 넣어야함.
    @PostMapping(consumes = {"multipart/form-data"})
    public String itemSave(
            @RequestPart(value = "item", required = false) @Valid ItemCreateRequest request,
            @RequestPart(value = "mainImg") MultipartFile file,
            @RequestPart(value = "img", required = false) List<MultipartFile> files
    ) {
        itemService.itemSave(files, file, request);
        return "저장 성공!";
    }


    @PutMapping("/{id}")
    public String itemUpdate(
            @RequestPart(value = "item", required = false) @Valid ItemUpdateRequest request,
            @RequestPart(value = "mainImg") MultipartFile file,
            @RequestPart(value = "img", required = false) List<MultipartFile> files,
            @PathVariable("id") Long itemId
    ) {
        itemService.itemUpdate(itemId, files, file, request);
        return "수정 성공!";
    }

}
