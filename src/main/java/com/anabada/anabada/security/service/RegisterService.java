package com.anabada.anabada.security.service;

import com.anabada.anabada.global.exception.RegisterException;
import com.anabada.anabada.global.exception.type.RegisterErrorCode;
import com.anabada.anabada.security.config.PasswordConfig;
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

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final EmailUtil emailUtil;

    private final PasswordConfig config;


    @Transactional
    public RegisterResponse userRegister(RegisterRequest request) {

        if (checkUserName(request.nickname())) {
            throw new RegisterException(RegisterErrorCode.DUPLICATE_NICKNAME);
        }
        if (checkId(request.userid())) {
            throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);
        }
        long emailId = checkEmailSuccessKey(request.email(), request.successKey());

        emailRepository.deleteById(emailId);

        userRepository.save(
                User.builder()
                        .userId(request.userid())
                        .userPw(config.passwordEncoder().encode(request.userpw()))
                        .email(request.email())
                        .nickname(request.nickname())
                        .phonenumber(request.phonenumber())
                        .build()
        );

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
    public RegisterResponse userEmailSendCheck(EmailSendCheckRequest request) {

        //Email 확인하기.
        checkEmailSuccessKey(request.email(), request.successKey());

        return new RegisterResponse("이메일 인증을 완료했습니다.");
    }

    @Transactional(readOnly = true)
    public RegisterResponse userIdCheck(IdCheckerRequest request) {

        if (checkId(request.userid())) {
            throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);
        }

        return new RegisterResponse("사용 가능한 ID입니다.");
    }

    @Transactional(readOnly = true)
    public RegisterResponse nicknameCheck(NicknameCheckerRequest request) {

        if (checkUserName(request.nickname())) {
            throw new RegisterException(RegisterErrorCode.DUPLICATE_NICKNAME);
        }

        return new RegisterResponse("사용 가능한 닉네임입니다.");
    }

    private boolean checkId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean checkUserName(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);

        if (user.isEmpty()) {
            return false;
        }

        return true;
    }

    private long checkEmailSuccessKey(String requestEmail, String successKey) {

        Email email = emailRepository.findByEmail(requestEmail)
                .orElseThrow(() -> {
                    throw new RegisterException(RegisterErrorCode.DUPLICATE_ID);
                });

        if (!email.getSuccessKey().equals(successKey)) {
            throw new RegisterException(RegisterErrorCode.EMAIL_SUCCESS_KEY_FAILED);
        }

        return email.getId();
    }

}
