package com.rabbitmq.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.mqtt.gateway.MqttGateway;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

	@Autowired
	private MqttGateway mqttGateway;
	
	@RequestMapping("/publish")
	public String publish(String message) {
		mqttGateway.sendToMqtt(message, "T1");
		return "OK";
	}
}
