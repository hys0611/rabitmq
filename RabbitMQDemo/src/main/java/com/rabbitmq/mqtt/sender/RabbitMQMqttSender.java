package com.rabbitmq.mqtt.sender;

import com.rabbitmq.mqtt.gateway.MqttGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RabbitMQMqttSender implements CommandLineRunner {

    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private RabbitmqMqttClient rabbitmqMqttClient;

    private AtomicInteger totalCnt = new AtomicInteger();
    private AtomicInteger sucCnt = new AtomicInteger();
    private AtomicInteger failCnt = new AtomicInteger();

    @Override
    public void run(String... args) throws Exception {

        List<String> topicPrefixs = Arrays.asList(new String[]{"test", "test"});
        List<Integer> topicSuffixs = getTopicSuffixs();

//        rabbitmqMqttClient.connect();

        topicSuffixs.forEach(s -> {
            topicPrefixs.forEach(p -> {
                String topic = p;
                String content = s+p;
                Payload payload = Payload.getPayloadBuider().setTopic("test").setContent(content).bulid();
                sendMqttByGateway(payload.toString(), topic);
            });
        });
    }

    private void sendMqttByGateway(String sendData, String topic) {
        try {
            mqttGateway.sendToMqtt(sendData, topic);
            log.info("succe send msg:" + topic);
        } catch (Exception e) {
            log.error("erro to publish with topic:{}, msg:{}", topic, sendData, e);
        }
    }

    private void sendMqttByMqttClient(String sendData, String topic) {
        try {
            rabbitmqMqttClient.publish(topic, sendData);
            log.info("success send msg:{} to topic:{}", sendData,topic);
        } catch (Exception e) {
            log.error("error to publish with topic:{}, msg:{}", topic, sendData, e);
        }
    }

    private List<Integer> getTopicSuffixs() {
        List<Integer> suffixs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            suffixs.add(i);
        }
        return suffixs;
    }


}
