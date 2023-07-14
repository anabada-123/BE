package com.anabada.anabada.global.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class ResponseJson<T> {

    private String code;
    private T result;

    public static <T> ResponseJson<T> success(T result) {
        return new ResponseJson<>("success", result);
    }
}
