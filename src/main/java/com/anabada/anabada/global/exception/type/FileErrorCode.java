package com.anabada.anabada.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode {

    FILE_ERROR(HttpStatus.BAD_REQUEST, "파일 저장하는데 오류가 생겼습니다."),
    FILE_NOT_EXTENSION(HttpStatus.BAD_REQUEST, "확장자가 존재하지 않습니다."),
    FILE_ERROR_EXTENSION(HttpStatus.BAD_REQUEST, "확장자가 존재하지 않습니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "파일이 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String errorMsg;
}
