package com.eda.springsrc.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eda.springsrc.domain.TaskEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProducerService {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, TaskEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTask(TaskEvent taskEvent) {
        log.info("Sending task from producer: {}", taskEvent);
        kafkaTemplate.send("TASK-TOPIC", taskEvent.getId().toString(), taskEvent)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Task sent to offset: {}", result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send task due to: {}", ex.getMessage());
                }
            });
    }

}
