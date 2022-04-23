package org.xpdojo.designpatterns._02_structural_patterns._05_facade.email;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    @Disabled
    @DisplayName("이메일 서비스를 여러 가지 쓴다면 Facade 효과를 볼 수 있다")
    void sut_email() {
        EmailSettings emailSettings = new EmailSettings();
        emailSettings.setHost("127.0.0.1");

        EmailSender emailSender = new EmailSender(emailSettings);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFrom("whiteship");
        emailMessage.setTo("markruler");
        emailMessage.setCc("imcxsu");
        emailMessage.setSubject("오징어게임");
        emailMessage.setText("밖은 더 지옥이더라고...");

        emailSender.sendEmail(emailMessage);
    }
}
