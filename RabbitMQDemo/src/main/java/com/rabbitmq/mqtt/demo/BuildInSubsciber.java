package com.rabbitmq.mqtt.demo;

import com.rabbitmq.mqtt.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mqtt.run.mode", havingValue = "demo")
public class BuildInSubsciber implements CommandLineRunner {

	// Determine the length of a topic name
	@Value("${mqtt.topic.longName:false}")
	private boolean topicLongName;
	
    @Autowired
    private SubscribeService subscribeService;

    // Define 2 Subscribers that subscribe all topics
    @Override
    public void run(String... args) throws Exception {
    	if (topicLongName) {
    		subscribeService.subscribe("Energy/#","client-1");
            subscribeService.subscribe("Energy/Field/#","client-2");
    	} else {
    		subscribeService.subscribe("T/#","client-1");
    		subscribeService.subscribe("T/+/a/+","client-2");
    	}
        
    }
}
