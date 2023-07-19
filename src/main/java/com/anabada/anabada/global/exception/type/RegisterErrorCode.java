package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegisterErrorCode {

    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "아이디 중복이 있습니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 중복 있습니다"),
    EMAIL_NOT_SENT(HttpStatus.BAD_REQUEST, "이메일 인증 보내기를 하지 않았습니다. 이메일 인증 보내기를 해주세요."),
    EMAIL_SUCCESS_KEY_FAILED(HttpStatus.BAD_REQUEST, "이메일 인증에 실패하였습니다. 인증 코드를 확인해주세요.")
    ;

    private final HttpStatus status;
    private final String errorMsg;

}
