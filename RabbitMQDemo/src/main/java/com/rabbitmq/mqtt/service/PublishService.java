package com.rabbitmq.mqtt.service;

import com.rabbitmq.mqtt.config.RabbitMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublishService {

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    public Boolean pulish(String topic, String msg, String host, String userName, String password, String clientId, int qos){
        try {
            MqttClient mqttClient = new MqttClient(host, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = getMqttConnectOptions(userName, password);
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            mqttClient.disconnect();
            mqttClient.close();
            System.out.println("success to publish msg:"+msg);
            System.out.println(String.format("success to publish with topic:[%s], msg:[%s]",topic,msg));
        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private MqttConnectOptions getMqttConnectOptions(String userName, String password) {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        connOpts.setUserName(userName);
        connOpts.setPassword(password.toCharArray());
        return connOpts;
    }

    public Boolean pulish(String topic, String msg){
        int qos = 1;
        String host = rabbitMqProperties.getHostUrl();
        String userName = rabbitMqProperties.getUsername();
        String password = rabbitMqProperties.getPassword();
        String clientId = rabbitMqProperties.getClientId();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(host, clientId, persistence);
            MqttConnectOptions connOpts = getMqttConnectOptions(userName, password);
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            mqttClient.disconnect();
            mqttClient.close();
            System.out.println("success to publish msg:"+msg);
            System.out.println(String.format("success to publish with topic:[%s], msg:[%s]",topic,msg));
        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }


}