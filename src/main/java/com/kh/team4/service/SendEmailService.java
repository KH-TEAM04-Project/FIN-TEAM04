package com.kh.team4.service;

import com.kh.team4.dto.MailDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailService {

    @Autowired
    MemberRepository memberRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "본인의 이메일 주소를 입력하세요!";

    private final PasswordEncoder passwordEncoder;

    public SendEmailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public SendEmailService() {
        // 기본 생성자 추가
        this.passwordEncoder = null;
    }

    public MailDTO createMailAndChangePassword(String email, String mname) {
        String str = getTempPassword();
        MailDTO dto = new MailDTO();
        dto.setAddress(email);
        dto.setTitle(mname + "님의 꽁머니 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 꽁머니 임시비밀번호 안내 관련 이메일 입니다." + "[" + mname + "]" + "님의 임시 비밀번호는 "
                + str + " 입니다.");

        String pw = passwordEncoder.encode(str);  // 비밀번호 암호화
        String memberId = memberRepository.findByEmail(email).getEmail();
        memberRepository.updatepwd(memberId, pw);
    }*/

    public void updatePassword(String str, String email, PasswordEncoder passwordEncoder) {
        String pw = passwordEncoder.encode(str);  // 비밀번호 암호화
        Member user = memberRepository.findByEmail(email);
        user.setPwd(pw);
        System.out.println("User: " + user.getPwd() + "원래 비번" + pw);
        memberRepository.save(user);
    }

    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx] + "!";
        }
        return str;
    }
    // 메일보내기
    public void mailSend(MailDTO mailDTO) {
        System.out.println("전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());
        message.setFrom("tjrwns4824@naver.com");
        message.setReplyTo("tjrwns4824@naver.com");
        System.out.println("message"+message);
        mailSender.send(message);
    }
}
