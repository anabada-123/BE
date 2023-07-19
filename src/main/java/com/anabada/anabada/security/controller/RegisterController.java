package com.anabada.anabada.security.controller;

import com.anabada.anabada.security.model.request.*;
import com.anabada.anabada.security.model.response.RegisterResponse;
import com.anabada.anabada.security.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping()
    public RegisterResponse register(
            @RequestBody RegisterRequest request
    ) {
        return registerService.userRegister(request);
    }

    @PostMapping("/id-check")
    public RegisterResponse userIdCheck(
            @RequestBody IdCheckerRequest request
    ) {
        return registerService.userCheck(request);
    }

    @PostMapping("/nickname-check")
    public RegisterResponse userNicknameCheck(
            @RequestBody NicknameCheckerRequest request
    ) {
        return registerService.nicknameCheck(request);
    }

    @PostMapping("/email-send")
    public RegisterResponse userEmailSend(
            @RequestBody EmailSendRequest request
    ) {
        return registerService.userEmailSend(request);
    }

    @PostMapping("/email-check")
    public RegisterResponse userEmailSendCheck(
            @RequestBody EmailSendCheckRequest request
    ) {
        return registerService.userEmailSendCheck(request);
    }
}
