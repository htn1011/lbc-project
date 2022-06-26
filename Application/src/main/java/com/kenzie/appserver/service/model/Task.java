package com.kenzie.appserver.service.model;

import java.util.List;

public class Task {
    private final String id;
    private final String name;
    private final String dateAdded;
    private final String completionDate;
    private final Boolean completed;


    public Task(String id, String name, String dateAdded, String completionDate, Boolean completed) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
        this.completionDate = completionDate;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getCompletionDate() {
        return completionDate;
    }


    public Boolean getCompleted() {
        return completed;
    }
}
