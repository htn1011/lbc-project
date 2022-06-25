package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Task")
public class TaskRecord {

    private String id;
    private String name;
    private String dateAdded;
    private String completionDate;
    private List<String> suppliesList;
    private boolean completed;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "DateAdded")
    public String getCompletionDate() {
        return completionDate;
    }

    @DynamoDBAttribute(attributeName = "SuppliesList")
    public List<String> getSuppliesList() {
        return suppliesList;
    }

    @DynamoDBAttribute(attributeName = "isCompleted")
    public boolean isCompleted() {
        return completed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setSuppliesList(List<String> suppliesList) {
        this.suppliesList = suppliesList;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskRecord taskRecord = (TaskRecord) o;
        return Objects.equals(id, taskRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
