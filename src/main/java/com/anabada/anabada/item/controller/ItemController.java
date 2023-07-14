package com.anabada.anabada.item.controller;

import com.anabada.anabada.item.model.request.ItemUpdateRequest;
import com.anabada.anabada.item.model.response.ItemFindResponse;
import com.anabada.anabada.item.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anabada.anabada.global.model.response.ResponseJson;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    //상품 전체 조회
    @GetMapping
    public ResponseJson<List<ItemFindResponse>> getItem() {
        return ResponseJson.success(itemService.getItem());
    }

    //TODO: 로그인 기능 추가시 아이디 넣어야함.
    @PostMapping
    public ResponseJson<String> itemSave(
            @RequestPart(value = "item", required = false) @Valid ItemCreateRequest request,
            @RequestPart(value = "mainImg", required = false) MultipartFile file,
            @RequestPart(value = "img", required = false) List<MultipartFile> files
    ) {
        itemService.itemSave(files, file, request);
        return ResponseJson.success("저장 성공!");
    }

    @PutMapping("/{id}")
    public ResponseJson<String> itemUpdate(
            @RequestPart(value = "item", required = false) @Valid ItemUpdateRequest request,
            @RequestPart(value = "mainImg", required = false) MultipartFile file,
            @RequestPart(value = "img", required = false) List<MultipartFile> files,
            @PathVariable("id") Long itemId
    ) {
        itemService.itemUpdate(itemId,files, file, request);
        return ResponseJson.success("수정 성공!");
    }

}
