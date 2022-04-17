package com.lrsrodrigues.spring_boot_aws.receiver;

import com.lrsrodrigues.spring_boot_aws.model.User;
import com.lrsrodrigues.spring_boot_aws.services.EmailService;
import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public class EmailReceiver {

    private static final Logger log = LoggerFactory.getLogger(EmailReceiver.class);

    @Autowired
    private EmailService emailService;

    @SqsListener(value = "email", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receive(User user) throws MessagingException {
       emailService.send(user);
       log.info("Email sent to {}", user.getEmail());
    }

}
