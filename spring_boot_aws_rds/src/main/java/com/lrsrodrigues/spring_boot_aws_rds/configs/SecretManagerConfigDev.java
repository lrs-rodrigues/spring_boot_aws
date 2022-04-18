package com.lrsrodrigues.spring_boot_aws_rds.configs;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SecretManagerConfigDev {

    @Bean
    @Primary
    public AWSSecretsManager awsSecretsManager() {
        return AWSSecretsManagerClientBuilder.defaultClient();
    }

}
