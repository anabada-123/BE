package com.anabada.anabada.security.controller;

import com.anabada.anabada.security.model.request.LoginRequestDto;
import com.anabada.anabada.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/login")
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public void login(
            @RequestBody LoginRequestDto request
    ) {
        loginService.login(request);
    }

}
