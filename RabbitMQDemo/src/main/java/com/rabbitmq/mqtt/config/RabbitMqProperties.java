package com.rabbitmq.mqtt.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
@Data
public class RabbitMqProperties {
    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.host.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client_id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.timeOut}")
    private int timeOut;

    @Value("${spring.mqtt.keepAlive}")
    private int keepAlive;

    @Value("${spring.mqtt.qos}")
    private int qos;


}
