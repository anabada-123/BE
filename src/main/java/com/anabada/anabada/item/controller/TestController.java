package com.anabada.anabada.item.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping
    public void test(HttpServletResponse response){
        Cookie cookie = new Cookie("Authorization", "권조우언 잘생김");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
