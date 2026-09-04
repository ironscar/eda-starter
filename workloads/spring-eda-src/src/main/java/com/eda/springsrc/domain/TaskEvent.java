package com.eda.springsrc.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String eventType;
    private Integer id;
    private String title;
    private Boolean completed;

    public TaskEvent(Task task, String eventType) {
        this.userId = task.getUserId();
        this.eventType = eventType;
        this.id = task.getId();
        this.title = task.getTitle();
        this.completed = task.getCompleted();
    }

    public Task getTask() {
        return Task.builder()
                .userId(this.userId)
                .id(this.id)
                .title(this.title)
                .completed(this.completed)
                .build();
    }

}
