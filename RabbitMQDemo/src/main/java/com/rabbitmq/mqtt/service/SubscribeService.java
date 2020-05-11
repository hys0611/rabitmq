package com.rabbitmq.mqtt.service;

import com.rabbitmq.mqtt.callback.MqttSenderCallback;
import com.rabbitmq.mqtt.config.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscribeService {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    // Configure the process to subscribe a message
    public void subscribe(String topic, String clientId) {
        String hostUrl = rabbitMQProperties.getHostUrl();
        int qos = rabbitMQProperties.getQos();
        String userName = rabbitMQProperties.getUsername();
        String passWord = rabbitMQProperties.getPassword();
        try {
            MqttClient client = new MqttClient(hostUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(rabbitMQProperties.getTimeOut());
            options.setKeepAliveInterval(rabbitMQProperties.getKeepAlive());
            client.setCallback(new MqttSenderCallback());
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
        	log.error("error to subsribe topic:{} with clienId:{} due to:", topic, clientId, e);
        }
    }

}