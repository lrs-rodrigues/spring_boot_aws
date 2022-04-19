package com.lrsrodrigues.spring_boot_aws_sqs.model;

import lombok.Data;

@Data
public class Calculator {

    private float a;
    private float b;
    private CalculatorType calculatorType;

}


