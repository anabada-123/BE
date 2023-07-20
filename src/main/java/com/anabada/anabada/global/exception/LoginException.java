package com.anabada.anabada.global.exception;

import com.anabada.anabada.global.exception.type.LoginErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginException extends RuntimeException {

    private final LoginErrorCode errorCode;

}
