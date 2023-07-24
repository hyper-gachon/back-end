package com.gachon.hypergachon.domain.user.service;

import com.gachon.hypergachon.domain.user.dto.request.EmailCheckDto;
import com.gachon.hypergachon.exception.BusinessException;
import com.gachon.hypergachon.response.ErrorMessage;
import com.gachon.hypergachon.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import static com.gachon.hypergachon.response.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;

    private final RedisUtil redisUtil;

    private String authNum; //랜덤 인증 코드

    //랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<6;i++) {
            key.append(random.nextInt(9));
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); //인증 코드 생성
        String setFrom = "ojy09293@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "HyperGachon 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);

        // redis에 5분간 email 인증 코드 저장(key = email, value = code)
        redisUtil.setDataExpire(toEmail, authNum, 60 * 5L);

        //실제 메일 전송
        emailSender.send(emailForm);

        return redisUtil.getData(toEmail); //인증 코드 반환
    }

    //타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }


    // 이메일 확인
    public Boolean checkEmailCode(EmailCheckDto emailCheckDto){
        if(!emailCheckDto.getCode().equals(redisUtil.getData(emailCheckDto.getEmail())))
            throw new BusinessException(WRONG_EMAIL_CODE);

        return true;
    }

}
