package com.rabbitmq.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.rabbitmq.mqtt")
public class RabbitMQMqttApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQMqttApplication.class, args);
    }
}
