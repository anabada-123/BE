package com.anabada.anabada.global.advice;

import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.model.response.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "error")
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<?> itemExceptionHandler(ItemException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().getErrorMsg());
    }


}
