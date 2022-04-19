package com.lrsrodrigues.spring_boot_aws_sqs.configs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SQSConfigDev {

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.defaultClient();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(
            AmazonSQSAsync amazonSQSAsync
    ) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQS){
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQS);
        factory.setMaxNumberOfMessages(10);
        return factory;
    }

    @Bean
    public QueueMessageHandler queueMessageHandler() {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync());
        return queueMessageHandlerFactory.createQueueMessageHandler();
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            SimpleMessageListenerContainerFactory factory, QueueMessageHandler messageHandler)
    {
        SimpleMessageListenerContainer container = factory.createSimpleMessageListenerContainer();
        container.setMaxNumberOfMessages(5);
        container.setMessageHandler(messageHandler);
        return container;
    }

}
