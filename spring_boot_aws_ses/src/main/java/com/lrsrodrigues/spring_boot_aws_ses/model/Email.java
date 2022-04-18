package com.lrsrodrigues.spring_boot_aws_ses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Email {

    private String destination;
    private String subject;
    private String body;
    private EmailType emailType;

    private Map<String, String> data;

}
