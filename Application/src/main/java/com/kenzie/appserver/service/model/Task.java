package com.kenzie.appserver.service.model;

import java.util.List;

public class Task {
    private final String id;
    private final String name;
    private final String dateAdded;
    private final String completionDate;
    private final List<String> suppliesList;
    private final Boolean completed;


    public Task(String id, String name, String dateAdded, String completionDate, List<String> suppliesList, Boolean completed) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
        this.completionDate = completionDate;
        this.suppliesList = suppliesList;
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

    public List<String> getSuppliesList() {
        return suppliesList;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
