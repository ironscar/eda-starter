package com.eda.springtgt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eda.springtgt.domain.Task;
import com.eda.springtgt.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(Integer limit) {
        return taskRepository.findAll(limit);
    }
    
    public Task createTask(Task task) {
        return taskRepository.insert(task.getTitle());
    }
    
    public Task updateTask(int id, Task task) {
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request body cannot be null");
        }
        return taskRepository.update(id, task);
    }

    public void deleteTask(int id) {
        int deletedCount = taskRepository.delete(id);
        if (deletedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }
    
}
