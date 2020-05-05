package com.rabbitmq.mqtt.sender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class Payload {

    private String clientId;
    private String topic;
    private String content;

    public Payload(String clientId, String topic, String content) {
        this.clientId = clientId;
        this.topic = topic;
        this.content = content;
    }

    public static class Builder {
        private String clientId;
        private String topic;
        private String content;

        public Builder setClinetId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Payload bulid() {
            return new Payload(clientId, topic, content);
        }
    }

    public static Builder getPayloadBuider() {
        return new Builder();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
