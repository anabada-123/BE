package com.anabada.anabada.global.exception;

import com.anabada.anabada.global.exception.type.RegisterErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterException extends RuntimeException{

    private final RegisterErrorCode errorCode;

}
