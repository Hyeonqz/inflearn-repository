package org.hyeonqz.practicaltest.spring.client.mail;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MailSendClient {

    public boolean sendEmail (String fromEmail, String toEmail, String subject, String content) {
        log.info("이메일 전송 시작");
        throw new IllegalArgumentException("메일 전송 예외");
    }

}
