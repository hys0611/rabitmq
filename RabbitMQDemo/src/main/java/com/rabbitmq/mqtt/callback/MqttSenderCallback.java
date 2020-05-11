package com.rabbitmq.mqtt.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttSenderCallback implements MqttCallback {

	// Event when the connection is lost
	@Override
    public void connectionLost(Throwable cause) {
		log.info("connection is lost");
    }

	// Event when a message arrives
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
//		TODO
        log.info("receive msg -- topic:[{}], msg:[{}]",topic,new String(message.getPayload()));
    }

    // Event when a delivery completes
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    	log.info("delivery is completed---------" + token.isComplete());
    }
}