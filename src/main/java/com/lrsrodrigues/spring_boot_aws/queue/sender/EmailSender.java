package com.lrsrodrigues.spring_boot_aws.queue.sender;

import com.lrsrodrigues.spring_boot_aws.model.User;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Value("${cloud.aws.sqs.emailQueue}")
    private String emailQueue;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    public void send(User user) {
        messagingTemplate.convertAndSend(emailQueue, user);
        log.info("Message sent {}", user.getEmail());
    }
}
