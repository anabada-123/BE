package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode {

    ITEM_NULL(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 입니다."),
    ITEM_STATUS_ERROR(HttpStatus.BAD_REQUEST, "존재하지 않는 물건 상태 입니다.")
    ;

    private final HttpStatus status;
    private final String errorMsg;

}

