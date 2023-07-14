package com.anabada.anabada.item.controller;

import com.anabada.anabada.item.response.ItemFindResponse;
import com.anabada.anabada.item.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.exception.type.ItemErrorCode;
import com.anabada.anabada.global.model.response.ResponseJson;
import com.anabada.anabada.item.model.request.ItemCreateRequest;
import com.anabada.anabada.item.service.ItemService;
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
    @GetMapping("/")
    public List<ItemFindResponse> getItem() {
        return itemService.getItem();
    }

    //TODO: 로그인 기능 추가시 아이디 넣어야함.
    @PostMapping
    public ResponseJson<String> itemSave(
            @RequestPart(value = "item") @Valid ItemCreateRequest request,
            @RequestPart(value = "mainImg") MultipartFile file,
            @RequestPart(value = "img") List<MultipartFile> files
    ) {
        itemService.itemSave(files, file, request);
        return ResponseJson.success("저장 성공!");
    }

}
