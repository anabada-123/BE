package com.anabada.anabada.security.service;

import com.anabada.anabada.global.exception.RegisterException;
import com.anabada.anabada.global.exception.type.RegisterErrorCode;
import com.anabada.anabada.security.model.entity.User;
import com.anabada.anabada.security.model.request.IdCheckerRequest;
import com.anabada.anabada.security.model.request.NicknameCheckerRequest;
import com.anabada.anabada.security.model.response.RegisterResponse;
import com.anabada.anabada.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository repository;

    public RegisterResponse userCheck(IdCheckerRequest request){
        Optional<User> user = repository.findByUserId(request.userid());

        if(user.isEmpty()){
            return new RegisterResponse("사용 가능한 ID입니다.");
        }

        throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);
    }

    public RegisterResponse nicknameCheck(NicknameCheckerRequest request){
        Optional<User> user = repository.findByNickname(request.nickname());

        if(!user.isEmpty()){
            throw new RegisterException(RegisterErrorCode.DUPLICATE_NICKNAME);
        }

        return new RegisterResponse("사용 가능한 닉네임입니다.");
    }

}
