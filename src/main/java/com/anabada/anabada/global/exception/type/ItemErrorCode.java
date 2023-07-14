package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode {

    ITEM_NULL(HttpStatus.BAD_REQUEST, "존재하지 않는 아이템 입니다.")
    ;

    private final HttpStatus status;
    private final String errorMsg;

}

