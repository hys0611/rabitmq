package com.rabbitmq.mqtt.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class RabbitMqttCallBack implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("connectionLost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("receive msg -- topic:[%s], msg:[%s]",topic,new String(message.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}
