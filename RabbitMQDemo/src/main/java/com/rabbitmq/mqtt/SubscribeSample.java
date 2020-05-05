package com.rabbitmq.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscribeSample {

    public static void main(String[] args) throws MqttException {
        String HOST = "tcp://localhost:1883";
        String TOPIC = "mqtt/test";
        int qos = 1;
        String clientid = "subClient";
        String userName = "guest";
        String passWord = "guest";

        try {
            MqttClient client = new MqttClient(HOST, clientid, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("message content:"+new String(message.getPayload()));
                    
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------"+ token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(TOPIC, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}