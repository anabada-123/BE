package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegisterErrorCode {

    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "아이디 중복이 있습니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 중복 있습니다")
    ;

    private final HttpStatus status;
    private final String errorMsg;

}
