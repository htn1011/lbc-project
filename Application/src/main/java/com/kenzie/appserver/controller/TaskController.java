package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskCreateRequest;
import com.kenzie.appserver.controller.model.TaskResponse;

import com.kenzie.appserver.controller.model.TaskUpdateRequest;
import com.kenzie.appserver.service.TaskService;
import com.kenzie.appserver.service.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("id") String id) {

        Task task = taskService.findByTaskId(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        TaskResponse taskResponse = createTaskResponse(task);
        return ResponseEntity.ok(taskResponse);
    }


    @PostMapping
    public ResponseEntity<TaskResponse> addNewTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        Task task = new Task(randomUUID().toString(),
                taskCreateRequest.getName(),
                taskCreateRequest.getDateAdded(),
                randomUUID().toString(),
                false);

        taskService.addNewTask(task);

        TaskResponse taskResponse = createTaskResponse(task);

        return ResponseEntity.created(URI.create("/task/" + taskResponse.getId())).body(taskResponse);
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        Task task = new Task(taskUpdateRequest.getId(),
                taskUpdateRequest.getName(),
                taskUpdateRequest.getDateAdded(),
                taskUpdateRequest.getCompletionDate(),
                taskUpdateRequest.getCompleted());
        taskService.updateTask(task);

        TaskResponse taskResponse = createTaskResponse(task);

        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TaskResponse> response = new ArrayList<>();
        for (Task task : tasks) {
            response.add(this.createTaskResponse(task));
        }

        return ResponseEntity.ok(response);
    }

    private TaskResponse createTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDateAdded(task.getDateAdded());
        taskResponse.setCompleted(task.getCompleted());
        taskResponse.setCompleted(task.getCompleted());
        return taskResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskById(@PathVariable("id") String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
