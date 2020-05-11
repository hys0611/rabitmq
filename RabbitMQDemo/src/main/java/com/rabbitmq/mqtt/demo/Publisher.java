package com.rabbitmq.mqtt.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.rabbitmq.mqtt.service.PublishService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "mqtt.run.mode", havingValue = "demo")
public class Publisher implements CommandLineRunner {

	// Determine the size of messages
	@Value("${mqtt.topic.size:100}")
	private Integer topicSize;

	// Determine the length of a topic name
	@Value("${mqtt.topic.longName:false}")
	private boolean topicLongName;
	
	@Autowired
	private PublishService publishService;

	// Publish messages to different types of topics
	@Override
	public void run(String... args) throws Exception {

		String topic = "";
		String msg = "";
		for (int i = 0; i < (topicSize); i++) {
			try {
				long start = System.currentTimeMillis();
				if (topicLongName) {
//					log.info(Boolean.toString(topicLongName));
					topic = "Energy/Field/" + i + "/Station/" + i + "/Departure/Measurement/Voltage";
				} else {
					topic = "T/" + i + "/a/" + i;
				}
				msg = String.format("this is %d message", i);
				publishService.publish(topic, msg);
//				TODO
				System.out.println("duration:" + (System.currentTimeMillis() - start));
			} catch (Exception e) {
				publishService.close();
				publishService.refresh();
				if (!publishService.publish(topic, msg)) {
					log.error("erro to pulish msg:{} to topic:{}", msg, topic);
				}
			}
		}
//		publishService.close();
		// For usage of REST API
		// System.exit(0);
	}

}
