package com.anabada.anabada.security.service;

import com.anabada.anabada.global.exception.LoginException;
import com.anabada.anabada.global.exception.type.LoginErrorCode;
import com.anabada.anabada.security.model.entity.User;
import com.anabada.anabada.security.model.request.LoginRequestDto;
import com.anabada.anabada.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public void login(LoginRequestDto dto) {
        User user = userRepository.findByUserId(dto.getUserid())
                .orElseThrow(() -> {
                    throw new LoginException(LoginErrorCode.NO_LOGIN);
                });
        if(!user.getUserPw().equals(dto.getUserpw())){
            throw new LoginException(LoginErrorCode.NO_LOGIN);
        }
    }

}
