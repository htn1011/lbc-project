package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class PutAndGetAllTest {
    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }


    @Test
    void findAllTasks_two_tasks() {
        // GIVEN
        TaskRecord task1 = new TaskRecord();
        task1.setId(randomUUID().toString());
        task1.setName("task1");
        task1.setDateAdded("01/01/2022");
        task1.setCompletionDate("01/01/2022");
        task1.setCompleted(true);

        TaskRecord task2 = new TaskRecord();
        task2.setId(randomUUID().toString());
        task2.setName("task2");
        task2.setDateAdded("01/01/2022");
        task2.setCompletionDate("01/01/2022");
        task2.setCompleted(false);

        List<TaskRecord> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        when(taskRepository.findAll()).thenReturn(taskList);

        // WHEN
        List<Task> tasks = taskService.findAllTasks();

        // THEN
        Assertions.assertNotNull(tasks, "The task list is returned");
        Assertions.assertEquals(2, tasks.size(), "There are two tasks");

        for (Task task : tasks) {
            if (task.getId() == task1.getId()) {
                Assertions.assertEquals(task1.getId(), task.getId(), "The task id matches");
                Assertions.assertEquals(task1.getName(), task.getName(), "The task name matches");
                Assertions.assertEquals(task1.getDateAdded(), task.getDateAdded(), "The task date matches");
                Assertions.assertEquals(task1.getCompletionDate(), task.getCompletionDate(), "The task completion date matches");
                Assertions.assertEquals(task1.getCompleted(), task.getCompleted(), "The task completed flag matches");
            } else if (task.getId() == task2.getId()) {
                Assertions.assertEquals(task2.getId(), task.getId(), "The task id matches");
                Assertions.assertEquals(task2.getName(), task.getName(), "The task name matches");
                Assertions.assertEquals(task2.getDateAdded(), task.getDateAdded(), "The task date matches");
                Assertions.assertEquals(task2.getCompletionDate(), task.getCompletionDate(), "The task completion date matches");
                Assertions.assertEquals(task2.getCompleted(), task.getCompleted(), "The task completed flag matches");
            } else {
                Assertions.assertTrue(false, "Task returned that was not in the records!");
            }
        }
    }

    @Test
    void update_task() {
        //GIVEN

        Task task = new Task(randomUUID().toString(), "task1", "01/01/2022", "01/01/2022", true);

        //WHEN

        //THEN
        taskService.updateTask(task);
        Assertions.assertEquals(task.getCompleted(), true);
    }
}
