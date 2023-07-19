package com.anabada.anabada.security.model.request;

public record EmailSendCheckRequest(
        String email,
        String successKey
) {
}
