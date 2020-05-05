package com.rabbitmq.mqtt.sender;

import com.rabbitmq.mqtt.config.RabbitMqttSenderConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class RabbitmqMqttClient {

    @Autowired
    private MqttClient client;

    @Autowired
    private RabbitMqttSenderConfig rabbitMqttSenderConfig;

    public void connect() {
        try {
            MqttConnectOptions options = rabbitMqttSenderConfig.getMqttConnectOptions();
            try {
                client.setCallback(new MqttSenderCallback(this, rabbitMqttSenderConfig.getMqttConnectOptions()));
                if (!client.isConnected()) {
                    client.connect(options);
                    log.info("MQTT connect successfully");
                    subscribe(rabbitMqttSenderConfig.getTopics(), rabbitMqttSenderConfig.getQos());
                } else {
                    client.disconnect();
                    client.connect(options);
                    log.info("MQTT reconnect");
                }
            } catch (Exception e) {
                log.info("error", e);
            }
        } catch (Exception e) {
            log.info("error", e);
        }
    }


    public Boolean reConnect() throws Exception {
        Boolean isConnected = false;
        if (null != client) {
            client.connect();
            if (client.isConnected()) {
                isConnected = true;
            }
        }
        return isConnected;
    }


    public void publish(String topic, String pushMessage) {
        publish(0, false, topic, pushMessage);
    }

    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = this.getClient().getTopic(topic);
        if (null == mTopic) {
            log.error("MQTT topic not exists");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (Exception e) {
            throw new RuntimeException("error pulbish:",e);
        }
    }


    public void publish(int qos, String topic, String pushMessage) {
        publish(qos, false, topic, pushMessage);
    }

    public void subscribe(String[] topic, int[] qos) {
        try {
            client.unsubscribe(topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            log.info("error", e);
        }
    }

}
