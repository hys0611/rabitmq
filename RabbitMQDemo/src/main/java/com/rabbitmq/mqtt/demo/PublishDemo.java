package com.rabbitmq.mqtt.demo;

import com.rabbitmq.mqtt.service.PublishService;
import com.rabbitmq.mqtt.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ConditionalOnProperty(name = "mqtt.run.mode", havingValue = "demo")
public class PublishDemo implements CommandLineRunner {

    @Autowired
    private PublishService publishService;

    @Autowired
    private SubscribeService subscribeService;

    @Override
    public void run(String... args) throws Exception {
//        subscribeService.subscribe("T/#","client-T");
//        subscribeService.subscribe("a/#","client-a");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        list.forEach(i->{
            publishService.pulish("T/"+i,String.format("this is %d message", i));
            publishService.pulish("a/"+i,String.format("this is %d message", i));
        });
        System.exit(0);
    }
}
