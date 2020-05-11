package com.rabbitmq.mqtt.demo;

import com.rabbitmq.mqtt.callback.MqttSenderCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

// This class is not built in Spring Boot
public class Subscriber {

	// Define 2 Subscribers that subscribe specified topics
    public static void main(String[] args) throws MqttException {
        String hostUrl = "tcp://localhost:1883";
        int qos = 1;
        String userName = "guest";
        String passWord = "guest";

        try {
            MqttConnectOptions options = getMqttConnectOptions(userName, passWord);

            MqttClient client = new MqttClient(hostUrl, "Client-1", new MemoryPersistence());
            client.setCallback(new MqttSenderCallback());
            client.connect(options);
            client.subscribe("T001/#", qos);

            MqttClient client2 = new MqttClient(hostUrl, "Client-2", new MemoryPersistence());
            client2.setCallback(new MqttSenderCallback());
            client2.connect(options);
            client2.subscribe("T002/+", qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Define configuration 
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