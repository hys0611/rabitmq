package com.rabbitmq.mqtt.sender;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MqttSenderCallback implements MqttCallback {

    private RabbitmqMqttClient rabbitmqMqttClient;

    @Autowired
    private MqttConnectOptions mqttConfiguration;

    public MqttSenderCallback(RabbitmqMqttClient rabbitmqMqttClient, MqttConnectOptions mqttConfiguration) {
        this.rabbitmqMqttClient = rabbitmqMqttClient;
        this.mqttConfiguration = mqttConfiguration;
    }

    @Override
    public void connectionLost(Throwable cause) {
        if (rabbitmqMqttClient != null) {
            while (true) {
                try {
                    log.info("[MQTT] lost connect，try reconnect after 5s...");
                    Thread.sleep(5000);
                    RabbitmqMqttClient rabbitmqMqttClient = new RabbitmqMqttClient();
                    rabbitmqMqttClient.connect();
                    if (rabbitmqMqttClient.getClient().isConnected()) {
                        System.out.println("reconnect success");

                    }
                    break;
                } catch (Exception e) {
                    log.error("[MQTT] lost connection, fail connect！");
                    continue;
                }
            }
        }
        log.info(cause.getMessage());
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("pushComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("received message topic : " + topic);
        log.info("received message Qos : " + message.getQos());
        log.info("received message content : " + new String(message.getPayload()));
    }


}