package com.lrsrodrigues.spring_boot_aws_ses.configs;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import io.awspring.cloud.ses.SimpleEmailServiceJavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@Profile("dev")
public class SESConfigDev {

    @Primary
    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.defaultClient();
    }

    @Bean
    public JavaMailSender javaMailSender(
            AmazonSimpleEmailService amazonSimpleEmailService
    ) {
        return new SimpleEmailServiceJavaMailSender(amazonSimpleEmailService);
    }
}
