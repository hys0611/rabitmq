package com.rabbitmq.mqtt.demo;

import com.rabbitmq.mqtt.callback.RabbitMqttCallBack;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscribeDemo {

    public static void main(String[] args) throws MqttException {
        String hostUrl = "tcp://localhost:1883";
        int qos = 1;
        String userName = "guest";
        String passWord = "guest";

        try {
            MqttConnectOptions options = getMqttConnectOptions(userName, passWord);

            MqttClient client = new MqttClient(hostUrl, "ClientT", new MemoryPersistence());
            client.setCallback(new RabbitMqttCallBack());
            client.connect(options);
            client.subscribe("T/#", qos);

            MqttClient client2 = new MqttClient(hostUrl, "Clienta", new MemoryPersistence());
            client2.setCallback(new RabbitMqttCallBack());
            client2.connect(options);
            client2.subscribe("a/#", qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MqttConnectOptions getMqttConnectOptions(String userName, String passWord) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        return options;
    }
}