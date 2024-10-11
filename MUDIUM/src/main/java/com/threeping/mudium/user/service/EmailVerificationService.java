package com.threeping.mudium.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
public class EmailVerificationService {

    private final RedisTemplate<String, String> redisTemplate;
    private JavaMailSender javaMailSender;
    @Autowired
    public EmailVerificationService(RedisTemplate<String, String> redisTemplate, JavaMailSender javaMailSender) {
        this.redisTemplate = redisTemplate;
        this.javaMailSender = javaMailSender;
    }

    // 인증 코드 전송 후 Redis에 <email, PENDING> 저장
    public void savePendingStatus(String email){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email+"_status", "PENDING",Duration.ofMinutes(5));
    }

    // 인증 완료 후 <email, VERIFIED> 상태로 업데이트
    public void saveVerifiedStatus(String email){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email+"status", "VERIFIED",Duration.ofMinutes(5));
    }

    // 이메일 인증 상태 확인
    public String getEmailVerificatoinStatus(String email){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(email);
    }

    // Redis에서 해당 이메일 인증상태 삭제
    public void deleteEmailVerificationStatus(String email){
        redisTemplate.delete(email);
    }

    /* 설명. 토큰 생성 및 Redis에 저장 */
    // 랜덤한 인증 코드 생성
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);  // 6자리 랜덤 숫자 생성
        return String.valueOf(code);
    }



    // 인증 코드 생성 및 Redis에 저장 (5분 TTL)
    public String sendVerificationCode(String email) {
        String code = generateVerificationCode();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, code, Duration.ofMinutes(5));  // 5분 동안 유효

        // 인증 코드 이메일 전송
        sendVerificationEmail(email, code);
        return code;
    }


    // 이메일로 인증 코드 전송
    public void sendVerificationEmail(String email, String code) {
        String subject = "이메일 인증 코드";
        String message = "인증 코드: " + code + "\n" + "이 코드는 5분 동안 유효합니다.";

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        emailMessage.setFrom("1etterh.dev@gmail.com");
        log.info("code: {}", code);
        javaMailSender.send(emailMessage);
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedCode = ops.get(email);
        log.info("storedCode: {}", storedCode);
        log.info("code: {}", code);
        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(email); // 인증 성공 시 코드 삭제
            return true;
        }
        return false;
    }
}
