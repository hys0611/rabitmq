package com.rabbitmq.mqtt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
@Slf4j
@Data
public class RabbitMQProperties {
	
	// The username of a RabbitMQ account
    @Value("${spring.mqtt.username}")
    private String username;

    // The password of a RabbitMQ account
    @Value("${spring.mqtt.password}")
    private String password;

    // The address of the server to connect MQTT messaging
    @Value("${spring.mqtt.url}")
    private String hostUrl;

    // A client identifier that is unique on the server being connected to
    @Value("${spring.mqtt.client_id}")
    private String clientId;

    // Define the default topic
    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    // Define the timeout
    @Value("${spring.mqtt.timeOut}")
    private int timeOut;

    // Define the keepAlive
    @Value("${spring.mqtt.keepAlive}")
    private int keepAlive;

    // Define the wildcard using a QoS (qualities of service)
    @Value("${spring.mqtt.qos}")
    private int qos;


}
