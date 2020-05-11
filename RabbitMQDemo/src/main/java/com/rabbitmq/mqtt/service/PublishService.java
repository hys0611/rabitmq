package com.rabbitmq.mqtt.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.mqtt.config.RabbitMQProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PublishService {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;
    
    
    @Autowired
    private MqttClient mqttClient;
    
    // Configure the process to publish a message
    public Boolean publish(String topic, String msg){
    	try {
    		 MqttMessage message = new MqttMessage(msg.getBytes());
             message.setQos(rabbitMQProperties.getQos());
             mqttClient.publish(topic, message);
//				TODO
             log.info(String.format("publish msg -- topic:[%s], msg:[%s]", topic, msg));
		} catch (MqttException e) {
			log.error("reason " + e.getReasonCode());
            log.error("msg " + e.getMessage());
            log.error("loc " + e.getLocalizedMessage());
            log.error("cause " + e.getCause());
            log.error("excep " + e);
            return false;
		}
    	return true;
        
    }
    
    // Close the connection of mqtt client
    public void close() {
    	try {
			mqttClient.disconnect();
			mqttClient.close();
			log.info("close mqtt client connection");
		} catch (MqttException e) {
			log.error("error to close mqtt client connection due to:", e);
		}
    	
    }
    
    // Refresh a connection
    public void refresh() {
		String host = rabbitMQProperties.getHostUrl();
		String userName = rabbitMQProperties.getUsername();
		String password = rabbitMQProperties.getPassword();
		String clientId = rabbitMQProperties.getClientId();
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			mqttClient = new MqttClient(host, clientId, persistence);
			MqttConnectOptions connOpts = getMqttConnectOptions(userName, password);
			mqttClient.connect(connOpts);
			log.info("refresh mqtt client");
		} catch (MqttException e) {
			log.error("error to refresh MqttClient due to:", e);
		}
    }

    // Get parameters of mqtt connections
    private MqttConnectOptions getMqttConnectOptions(String userName, String password) {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        connOpts.setUserName(userName);
        connOpts.setPassword(password.toCharArray());
        return connOpts;
    }
    
    
    
    

}