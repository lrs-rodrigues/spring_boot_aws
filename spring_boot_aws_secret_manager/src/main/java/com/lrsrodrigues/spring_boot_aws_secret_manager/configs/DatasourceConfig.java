package com.lrsrodrigues.spring_boot_aws_secret_manager.configs;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.secretName}")
    private String secretName;

    @Autowired
    private AWSSecretsManager awsSecretsManager;

    @Bean
    public DataSource getDataSource() throws JsonProcessingException {
        GetSecretValueResult getSecretValueResult = getSecretValueResult();
        DatasourceModel datasourceModel = datasourceModelMapper(getSecretValueResult);

        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(datasourceModel.getUrl())
                .username(datasourceModel.getUsername())
                .password(datasourceModel.getPassword())
                .build();
    }


    private GetSecretValueResult getSecretValueResult() {
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        return awsSecretsManager.getSecretValue(getSecretValueRequest);
    }

    private DatasourceModel datasourceModelMapper(GetSecretValueResult getSecretValueResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getSecretValueResult.getSecretString(), DatasourceModel.class);
    }
}
