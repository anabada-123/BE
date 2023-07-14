package com.anabada.anabada.global.exception;

import com.anabada.anabada.global.exception.type.ItemErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ItemException extends RuntimeException{

    private final ItemErrorCode errorCode;

}
