package com.eda.springtgt.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eda.springtgt.domain.Task;
import com.eda.springtgt.domain.TaskEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerService {

    private final TaskService taskService;

    @Autowired
    public ConsumerService(TaskService taskService) {
        this.taskService = taskService;
    }

    @KafkaListener(topics = "TASK-TOPIC", groupId = "spring-task-group")
    public void consumeTask(ConsumerRecord<String, TaskEvent> consumerRecord) {
        log.info("Received task on consumer: key={}, value={}, offset={}, partition={}", consumerRecord.key(), consumerRecord.value(), consumerRecord.offset(), consumerRecord.partition());

        TaskEvent taskEvent = consumerRecord.value();
        Task task = taskEvent.getTask();
        switch(taskEvent.getEventType()) {
            case "TASK_CREATE":
                taskService.createTask(task);
                break;
            case "TASK_UPDATE":
                taskService.updateTask(task.getId(), task);
                break;
            case "TASK_DELETE":
                taskService.deleteTask(task.getId());
                break;
            default:
                log.warn("Unknown event type: {}", taskEvent.getEventType());
        }
    }
    
}
