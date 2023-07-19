package com.anabada.anabada.security.util;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;

@Component
public class EmailUtil {

    @Value("${email.secret.id}")
    private String id;
    @Value("${email.secret.pw}")
    private String password;


    public String randomSuccess(){
        String randomStr = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            randomStr+=random.nextInt(9);
        }
        return randomStr;
    }

    public void send_email(String email, String random) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);

        javaMailSender.setPort(465);

        javaMailSender.setJavaMailProperties(getMailProperties());


        MimeMessage m = javaMailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
        try {
            h.setFrom(id);
            h.setTo(email);
            h.setSubject("아나바다 사이트 회원가입");
            h.setText("인증번호 : " + random);
        } catch (Exception e) {
            System.out.println(e);
        }
        javaMailSender.send(m);
    }


    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        return properties;
    }

}
