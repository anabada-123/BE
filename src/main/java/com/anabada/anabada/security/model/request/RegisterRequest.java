package com.anabada.anabada.security.model.request;

public record RegisterRequest(
        String userid,
        String userpw,
        String email,
        String phonenumber,
        String nickname,
        String successKey
) {
}
