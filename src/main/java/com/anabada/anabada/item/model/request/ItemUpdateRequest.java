package com.anabada.anabada.item.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemUpdateRequest(
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
        String cate,
        @NotNull
        @NotBlank
        String status,

        Boolean check
) {
}
