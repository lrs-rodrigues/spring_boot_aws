package com.lrsrodrigues.spring_boot_aws_sqs.consumer;

import com.lrsrodrigues.spring_boot_aws_sqs.model.Calculator;
import com.lrsrodrigues.spring_boot_aws_sqs.model.CalculatorType;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculatorConsumer {

    @SqsListener(value = "queue-padrao", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void queuePadrao(Calculator calculator) throws MessagingException {
        Float response = calculator(calculator);
        calculatorResponse("queuePadrao", calculator.getCalculatorType(), response);
    }

    @SqsListener(value = "queue-fifo.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void queueFifo(Calculator calculator) throws MessagingException {
        Float response = calculator(calculator);
        calculatorResponse("queueFifo", calculator.getCalculatorType(), response);
    }


    private Float calculator(Calculator calculator) {
        if (calculator.getCalculatorType().equals(CalculatorType.ADDITION))
            return calculator.getA() + calculator.getB();
        if (calculator.getCalculatorType().equals(CalculatorType.SUBTRACTION))
            return calculator.getA() - calculator.getB();
        if (calculator.getCalculatorType().equals(CalculatorType.MULTIPLICATION))
            return calculator.getA() * calculator.getB();
        if (calculator.getCalculatorType().equals(CalculatorType.DIVISION))
            return calculator.getA() * calculator.getB();
        return null;
    }

    private void calculatorResponse(String queueName, CalculatorType calculatorType, Float response) {
        if (response != null) {
            log.info("[{}] The answer of {} is {}", queueName, calculatorType, response);
        } else {
            log.info("[{}] This option was not found in the calculator!", queueName);
        }
    }
}
