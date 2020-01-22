package by.siarhei.beerfest.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailThread extends Thread {
    private static final Logger logger = LogManager.getLogger();

    private static final String REGISTRATION_MESSAGE = "To continue registration, follow the link: http://178.127.80.102:8089/Beerfest/controller?command=continue_registration&token=%s \nIf you have not registered on the beer festival website, simply ignore this message.";
    private String token;
    private String eMail;
    private MimeMessage message;

    public MailThread(String token, String eMail) {
        this.token = token;
        this.eMail = eMail;
    }

    private void init() {
        Session mailSession = (new SessionCreator().createSession());
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject("Registration confirm");
            message.setContent(String.format(REGISTRATION_MESSAGE, token), "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(eMail));
        } catch (AddressException e) {
            logger.error(String.format("Wrong address: %s throws exception: %s", eMail,e));
        } catch (MessagingException e) {
            logger.error(String.format("Cant form message throws exception: %s",e));
        }
    }

    public void run() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(String.format("Cant send message throws exception: %s",e));
        }
    }
}

