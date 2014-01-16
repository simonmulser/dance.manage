package at.danceandfun.util;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import at.danceandfun.entity.Person;

public class EMailAuthentication {

    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Person p) {

        SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);

        String URL = "http://localhost:8080/dancemanage/validation?access="
                + p.getActivationUUID();
        message.setText(String.format(simpleMailMessage.getText(),
                p.getLastname(), URL));

        message.setTo(p.getEmail());
        mailSender.send(message);

    }

}
