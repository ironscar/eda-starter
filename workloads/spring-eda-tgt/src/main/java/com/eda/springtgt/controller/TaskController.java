package com.eda.springtgt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eda.springtgt.domain.Task;
import com.eda.springtgt.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello from EDA Spring Boot Target!";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam(defaultValue = "10") Integer limit) {
        return taskService.getTasks(limit);
    }

}
