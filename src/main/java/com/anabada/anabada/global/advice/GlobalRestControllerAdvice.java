package com.anabada.anabada.global.advice;

import com.anabada.anabada.global.exception.FileException;
import com.anabada.anabada.global.exception.ItemException;
import com.anabada.anabada.global.exception.LoginException;
import com.anabada.anabada.global.exception.RegisterException;
import com.anabada.anabada.global.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "error")
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<?> itemExceptionHandler(ItemException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode().getErrorMsg()));
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<?> registerExceptionHandler(RegisterException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(e.getErrorCode().getErrorMsg()));
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> fileExceptionHandler(FileException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse((e.getErrorCode().getErrorMsg())));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> loginExceptionHandler(LoginException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse((e.getErrorCode().getErrorMsg())));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> vaildationHandler(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getBody());
    }
}
