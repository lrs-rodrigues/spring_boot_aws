package com.lrsrodrigues.spring_boot_aws.receiver;

import com.lrsrodrigues.spring_boot_aws.model.User;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailReceiver {

    private static final Logger log = LoggerFactory.getLogger(EmailReceiver.class);

    @SqsListener(value = "email", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receive(User user) {
        log.info("Message received {}", user);
    }

}
