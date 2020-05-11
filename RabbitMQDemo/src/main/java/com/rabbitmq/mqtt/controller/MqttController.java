package com.rabbitmq.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.mqtt.service.PublishService;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

	@Autowired
	private PublishService publishService;

	// Define an API to publish a message
	@RequestMapping("/publish")
	public Boolean publish(String topic, String message) {
		if (!publishService.publish(topic, message)) {
//			publishService.close();
			publishService.refresh();
			return publishService.publish(topic, message);
		}
		return true;
	}
}
