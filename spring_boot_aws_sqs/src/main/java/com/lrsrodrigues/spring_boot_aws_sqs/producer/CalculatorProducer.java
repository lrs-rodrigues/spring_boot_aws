package com.lrsrodrigues.spring_boot_aws_sqs.producer;

import com.lrsrodrigues.spring_boot_aws_sqs.model.Calculator;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class CalculatorProducer {

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    public void queuePadrao(Calculator calculator) {
        messagingTemplate.convertAndSend("queue-padrao", calculator);
        log.info("Calculation sent to queue processes: {}", "queue-padra");
    }

    public void queueFifo(Calculator calculator) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("message-group-id", UUID.randomUUID().toString());
        headers.put("message-deduplication-id", UUID.randomUUID().toString());

        messagingTemplate.convertAndSend("queue-fifo.fifo", calculator, headers);
        log.info("Calculation sent to queue processes: {}", "queue-fifo.fifo");
    }


}
