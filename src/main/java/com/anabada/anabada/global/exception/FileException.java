package com.anabada.anabada.global.exception;

import com.anabada.anabada.global.exception.type.FileErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException{

    private final FileErrorCode errorCode;

}
