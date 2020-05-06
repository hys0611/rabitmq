package com.rabbitmq.mqtt.service;

import com.rabbitmq.mqtt.callback.RabbitMqttCallBack;
import com.rabbitmq.mqtt.config.RabbitMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscribeService {

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    public void subscribe(String topic, String clientId) {
        String hostUrl = rabbitMqProperties.getHostUrl();
        int qos = rabbitMqProperties.getQos();
        String userName = rabbitMqProperties.getUsername();
        String passWord = rabbitMqProperties.getPassword();
        try {
            MqttClient client = new MqttClient(hostUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(rabbitMqProperties.getTimeOut());
            options.setKeepAliveInterval(rabbitMqProperties.getKeepAlive());
            client.setCallback(new RabbitMqttCallBack());
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}