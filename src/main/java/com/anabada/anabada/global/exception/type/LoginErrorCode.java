package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode {
    NO_LOGIN(HttpStatus.BAD_REQUEST, "회원을 찾을수 없습니다."),
    ;

    private final HttpStatus status;
    private final String errorMsg;
}
