package com.anabada.anabada.security.service;

import com.anabada.anabada.global.exception.RegisterException;
import com.anabada.anabada.global.exception.type.RegisterErrorCode;
import com.anabada.anabada.security.model.entity.Email;
import com.anabada.anabada.security.model.entity.User;
import com.anabada.anabada.security.model.request.*;
import com.anabada.anabada.security.model.response.RegisterResponse;
import com.anabada.anabada.security.repository.EmailRepository;
import com.anabada.anabada.security.repository.UserRepository;
import com.anabada.anabada.security.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository repository;
    private final EmailUtil emailUtil;
    private final EmailRepository emailRepository;

    @Transactional
    public RegisterResponse userRegister(RegisterRequest request){


        return new RegisterResponse("회원가입에 성공하였습니다.");
    }

    @Transactional
    public RegisterResponse userEmailSend(EmailSendRequest request) {

        Optional<Email> email = emailRepository.findByEmail(request.email());

        RegisterResponse response = new RegisterResponse("이메일 인증 코드을 보냈습니다. 확인해 주시길 바랍니다.");

        String successKey = emailUtil.randomSuccess();

        emailUtil.send_email(request.email(), successKey);
        if (email.isEmpty()) {
            emailRepository.save(Email.builder()
                    .email(request.email())
                    .successKey(successKey)
                    .build());
            return response;
        }
        //위에서 null 체크
        email.get().changesSuccessKey(successKey);

        return response;
    }

    @Transactional
    public RegisterResponse userEmailSendCheck(EmailSendCheckRequest request){

        Email email = emailRepository.findByEmail(request.email())
                .orElseThrow(() -> {throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);});

        if(!email.getSuccessKey().equals(request.successKey())){
            throw new RegisterException(RegisterErrorCode.EMAIL_SUCCESS_KEY_FAILED);
        }

        return new RegisterResponse("이메일 인증을 완료했습니다.");
    }

    @Transactional(readOnly = true)
    public RegisterResponse userCheck(IdCheckerRequest request){
        Optional<User> user = repository.findByUserId(request.userid());

        if(user.isEmpty()){
            return new RegisterResponse("사용 가능한 ID입니다.");
        }

        throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);
    }

    @Transactional(readOnly = true)
    public RegisterResponse nicknameCheck(NicknameCheckerRequest request){
        Optional<User> user = repository.findByNickname(request.nickname());

        if(user.isEmpty()){
            return new RegisterResponse("사용 가능한 닉네임입니다.");
        }

        throw new RegisterException(RegisterErrorCode.DUPLICATE_NICKNAME);
    }

}
