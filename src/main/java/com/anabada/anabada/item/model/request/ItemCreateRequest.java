package com.anabada.anabada.item.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ItemCreateRequest(
        @NotNull
        @NotBlank
        String itemName,
        @NotNull
        @NotBlank
        String itemContent,

        @NotNull
        @NotBlank
        String itemOneContent,

        @NotNull
        @NotBlank
        String tradingPosition,
        @NotNull
        @NotBlank
        String tradingItem,
        @NotNull
        @NotBlank
        String cate
) {
}
