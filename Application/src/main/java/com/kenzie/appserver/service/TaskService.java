package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;

import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();

        Iterable<TaskRecord> taskIterator = taskRepository.findAll();
        for(TaskRecord record : taskIterator) {
            tasks.add(new Task(record.getId(),
                    record.getName(),
                    record.getDateAdded(),
                    record.getCompletionDate(),
                    record.getCompleted()));
        }

        return tasks;
    }

    public Task findByTaskId(String taskId) {
        Task taskFromBackendService = taskRepository
                .findById(taskId)
                .map(task -> new Task(task.getId(),
                        task.getName(),
                        task.getDateAdded(),
                        task.getCompletionDate(),
                        task.getCompleted()))
                .orElse(null);

        return taskFromBackendService;
    }

    public Task addNewTask(Task task) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setId(task.getId());
        taskRecord.setName(task.getName());
        taskRecord.setDateAdded(task.getDateAdded());
        taskRecord.setCompletionDate(task.getCompletionDate());
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
            taskRecord.setCompleted(task.getCompleted());
            taskRepository.save(taskRecord);
        }
    }

    public void deleteTask(String taskId) {
        // Your code here
        taskRepository.deleteById(taskId);
    }
}
