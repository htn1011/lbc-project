package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.ExampleRecord;

import com.kenzie.appserver.service.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();

        Iterable<TaskRecord> concertIterator = taskRepository.findAll();
        for(TaskRecord record : concertIterator) {
            tasks.add(new Task(record.getId(),
                    record.getName(),
                    record.getDateAdded(),
                    record.getCompletionDate(),
                    record.getSuppliesList(),
                    record.getCompleted()));
        }

        return tasks;
    }

    public Task findById(String id) {
        Task taskFromBackend = taskRepository
                .findById(id)
                .map(task -> new Task(task.getId(),
                        task.getName(),
                        task.getDateAdded(),
                        task.getCompletionDate(),
                        task.getSuppliesList(),
                        task.getCompleted()))
                .orElse(null);

        return taskFromBackend;
    }

    public Task addNewTask(Task task) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setId(task.getId());
        taskRecord.setName(task.getName());
        taskRecord.setDateAdded(task.getDateAdded());
        taskRecord.setCompletionDate(task.getCompletionDate());
        taskRecord.setSuppliesList(task.getSuppliesList());
        taskRecord.setCompleted(task.getCompleted());
        taskRepository.save(taskRecord);
        return task;
    }

    public void updateTask(Task task) {
        if (taskRepository.existsById(task.getId())) {
            TaskRecord taskRecord = new TaskRecord();
            taskRecord.setId(task.getId());
            taskRecord.setName(task.getName());
            taskRecord.setDateAdded(task.getDateAdded());
            taskRecord.setCompletionDate(task.getCompletionDate());
            taskRecord.setSuppliesList(task.getSuppliesList());
            taskRecord.setCompleted(task.getCompleted());
            taskRepository.save(taskRecord);
        }
    }

    public void deleteTask(String taskId) {
        // Your code here
        taskRepository.deleteById(taskId);
    }
}
