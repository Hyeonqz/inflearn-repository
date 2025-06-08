package org.hyeonqz.practicaltest.spring.api.service.mail;

import org.hyeonqz.practicaltest.spring.client.mail.MailSendClient;
import org.hyeonqz.practicaltest.spring.domain.history.MailSendHistory;
import org.hyeonqz.practicaltest.spring.domain.history.MailSendHistoryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {
    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail (String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if(result) {
            mailSendHistoryRepository.save(new MailSendHistory(fromEmail, toEmail, subject, content));
            return true;
        }
        return false;
    }


}
