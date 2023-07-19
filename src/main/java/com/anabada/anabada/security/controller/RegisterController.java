package com.anabada.anabada.security.controller;

import com.anabada.anabada.security.model.request.IdCheckerRequest;
import com.anabada.anabada.security.model.request.NicknameCheckerRequest;
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




}
