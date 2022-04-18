package com.lrsrodrigues.spring_boot_aws_ses.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrsrodrigues.spring_boot_aws_ses.model.Email;
import com.lrsrodrigues.spring_boot_aws_ses.model.EmailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${cloud.aws.ses.atendimento}")
    private String atendimentoEmail;

    public void sendEmail(Email email) throws JsonProcessingException {
        if (email.getEmailType().equals(EmailType.BASIC))
            emailBasic(email);
        if (email.getEmailType().equals(EmailType.TEMPLATE))
            emailTemplate(email);
    }

    private void emailBasic(Email email) {
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSource(atendimentoEmail);
        sendEmailRequest.setDestination(new Destination(List.of(email.getDestination())));

        Message message = new Message();
        message.setSubject(new Content(email.getSubject()));
        message.setBody(new Body(new Content(email.getBody())));

        sendEmailRequest.setMessage(message);
        log.info("Email successfully sent {}", sendEmailRequest);
        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }

    private void emailTemplate(Email email) throws JsonProcessingException {
        SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
        sendTemplatedEmailRequest.setSource(atendimentoEmail);
        sendTemplatedEmailRequest.setDestination(new Destination(List.of(email.getDestination())));

        sendTemplatedEmailRequest.setTemplate("test-template");

        ObjectMapper objectMapper = new ObjectMapper();
        sendTemplatedEmailRequest.setTemplateData(objectMapper.writeValueAsString(email.getData()));

        log.info("Email successfully sent {}", sendTemplatedEmailRequest);
        amazonSimpleEmailService.sendTemplatedEmail(sendTemplatedEmailRequest);

    }

}
