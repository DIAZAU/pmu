package com.example.pmu.service.kafka;

import com.example.pmu.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PmuProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmuProducer.class);

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, CourseDTO> kafkaTemplate;
    public void send(CourseDTO course) {
        LOGGER.info(String.format("Message sent -> %s", course.toString()));

        Message<CourseDTO> message = MessageBuilder
                .withPayload(course)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);
    }
}
