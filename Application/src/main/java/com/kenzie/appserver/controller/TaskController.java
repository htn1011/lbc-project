package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskCreateRequest;
import com.kenzie.appserver.controller.model.TaskResponse;

import com.kenzie.appserver.service.model.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

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

        Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        TaskResponse taskResponse = createTaskReponse(task);
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addNewTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        Task task = new Task(randomUUID().toString(),
                taskCreateRequest.getName(),
                taskCreateRequest.getDateAdded());

        TaskService.addNewTask(task);

        TaskResponse taskResponse = new createTaskResponse(task);

        return ResponseEntity.created(URI.create("/task/" + taskResponse.getId())).body(taskResponse);
    }

    private TaskResponse createTaskResponse(Task task) {
        TaskResponse taskResponse = new taskReponse();
        taskResponse.setId(task.getId());
        taskResponse.setDateAdded(task.getDateAdded());
        taskResponse.setCompleted(task.getCompleted());
        return taskResponse;

    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        Task task = new Task(taskUpdateRequest.getId(),
                taskUpdateRequest.getName(),
                taskUpdateRequest.getDateAdded(),
                taskUpdateRequest.getCompleted());
        taskService.updateTask(task);

        TaskResponse taskResponse = createTaskResponse(task);

        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        if(tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TaskResponse> response = new ArrayList<>();
        for(Task task: tasks) {
            response.add(this.createTaskResponse(task));
        }

        return ResponseEntity.ok(response);
    }
}
