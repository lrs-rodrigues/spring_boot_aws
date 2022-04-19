package com.lrsrodrigues.spring_boot_aws_sqs.resources;

import com.lrsrodrigues.spring_boot_aws_sqs.model.Calculator;
import com.lrsrodrigues.spring_boot_aws_sqs.producer.CalculatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculator")
public class CalculatorResource {

    @Autowired
    private CalculatorProducer calculatorProducer;

    @PostMapping(value = "/queuePadrao")
    public void queuePadrao(
            @RequestBody Calculator calculator
    ) {
        calculatorProducer.queuePadrao(calculator);
    }

    @PostMapping(value = "/queueFifo")
    public void queueFifo(
            @RequestBody Calculator calculator
    ) {
        calculatorProducer.queueFifo(calculator);
    }

}
