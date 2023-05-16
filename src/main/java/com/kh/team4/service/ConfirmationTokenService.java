package com.kh.team4.service;

import com.kh.team4.entity.ConfirmationToken;
import com.kh.team4.repository.ConfirmationTokenRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    /**
     * 이메일 인증 토큰 생성
     * @return
     */
    public String createEmailConfirmationToken(String userId, String receiverEmail){

        Assert.hasText(userId,"userId는 필수 입니다.");
        Assert.hasText(receiverEmail,"receiverEmail은 필수 입니다.");

        ConfirmationToken emailConfirmationToken = ConfirmationToken.createEmailConfirmationToken(userId);
        confirmationTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("이메일 인증");
        mailMessage.setText("http://localhost:8090/confirm-email?token="+emailConfirmationToken.getId());
        emailSenderService.sendEmail(mailMessage);

        return emailConfirmationToken.getId();
    }

    /**
     * 유효한 토큰 가져오기
     * @param confirmationTokenId
     * @return
     */
    public ConfirmationToken findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId){
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(),false);
        if (confirmationToken.isPresent()) {
            return confirmationToken.get();
        } else {
            throw new BadRequestException(ValidationConstant.TOKEN_NOT_FOUND);
        }
    };

    public class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super("잘못된 접근입니다.");
        }
    }

    public class ValidationConstant {
        public static final String TOKEN_NOT_FOUND = "Token not found";
        // 다른 필요한 상수들을 추가로 정의할 수 있습니다.
    }
}