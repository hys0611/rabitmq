package com.rabbitmq.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MqttClientConfig {

	@Autowired
	private RabbitMQProperties rabbitMQProperties;

	/*
	 * create MqttClient Bean
	 */
	@Bean
	public MqttClient mqttClient() {
		MqttClient mqttClient = null;
		String host = rabbitMQProperties.getHostUrl();
		String userName = rabbitMQProperties.getUsername();
		String password = rabbitMQProperties.getPassword();
		String clientId = rabbitMQProperties.getClientId();
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			mqttClient = new MqttClient(host, clientId, persistence);
			MqttConnectOptions connOpts = getMqttConnectOptions(userName, password);
			mqttClient.connect(connOpts);

		} catch (MqttException e) {
			log.error("error to create MqttClient due to:", e);
		}
		return mqttClient;
	}

	private MqttConnectOptions getMqttConnectOptions(String userName, String password) {
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(false);
		connOpts.setUserName(userName);
		connOpts.setPassword(password.toCharArray());
		return connOpts;
	}

}
