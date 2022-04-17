package com.lrsrodrigues.spring_boot_aws.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.lrsrodrigues.spring_boot_aws.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${cloud.aws.ses.atendimento}")
    private String atendimentoEmail;

    public void send(User user) throws MessagingException {
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSource(atendimentoEmail);
        sendEmailRequest.setDestination(new Destination(List.of(user.getEmail())));

        Message message = new Message();
        message.setSubject(new Content("Nova conta"));
        message.setBody(new Body(new Content("Bem vindo " + user.getName())));

        sendEmailRequest.setMessage(message);
        log.info("Message {}", sendEmailRequest);
        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }

}
