package com.lrsrodrigues.spring_boot_aws.sender;

import com.lrsrodrigues.spring_boot_aws.model.User;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Value("${cloud.aws.sqs.emailQueue}")
    private String emailQueue;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    public void send(User user) {
        Message<User> msg = MessageBuilder.withPayload(user)
                .setHeader("transactionId", UUID.randomUUID())
                .build();

        messagingTemplate.convertAndSend(emailQueue, msg);
        log.info("Message sent {}", msg.getHeaders()) ;
    }

}
