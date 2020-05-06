package com.rabbitmq.mqtt.controller;

import com.rabbitmq.mqtt.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

	@Autowired
	private PublishService publishService;

	@RequestMapping("/publish")
	public Boolean publish(String topic, String message) {
		return publishService.pulish(topic, message);
	}
}
