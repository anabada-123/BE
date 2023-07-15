package com.anabada.anabada.global.advice;

import com.anabada.anabada.global.exception.ItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j(topic = "error")
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<?> itemExceptionHandler(ItemException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> vaildationHandler(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getBody());
    }
}
