package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    /**
     * ------------------------------------------------------------------------
     * taskService.findTaskById
     * ------------------------------------------------------------------------
     **/

    @Test
    void findByTaskId() {
        // GIVEN
        String id = randomUUID().toString();

        TaskRecord record = new TaskRecord();
        record.setId(id);
        record.setName("taskname");
        record.setDateAdded("dateadded");
        record.setCompletionDate("completiondate");
        record.setCompleted(false);

        // WHEN
        when(taskRepository.findById(id)).thenReturn(Optional.of(record));
        Task task = taskService.findByTaskId(id);

        // THEN
        Assertions.assertNotNull(task, "The object is returned");
        Assertions.assertEquals(record.getId(), task.getId(), "The id matches");
        Assertions.assertEquals(record.getName(), task.getName(), "The name matches");
        Assertions.assertEquals(record.getDateAdded(), task.getDateAdded(), "The date added matches");
        Assertions.assertEquals(record.getCompletionDate(), task.getCompletionDate(), "The date matches");
        Assertions.assertEquals(record.getCompleted(), task.getCompleted(), "The true or false matches");
    }

    @Test
    void findByTaskId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Task task = taskService.findByTaskId(id);

        // THEN
        Assertions.assertNull(task, "The task is null when not found");
    }

    /**
     * ------------------------------------------------------------------------
     * taskService.addNewTask
     * ------------------------------------------------------------------------
     **/

    @Test
    void addNewTask() {
        // GIVEN
        String taskId = randomUUID().toString();

        Task task = new Task(taskId, "taskname", "dateadded", "completiondate", false);

        ArgumentCaptor<TaskRecord> taskRecordCaptor = ArgumentCaptor.forClass(TaskRecord.class);

        // WHEN
        Task returnedTask = taskService.addNewTask(task);

        // THEN
        Assertions.assertNotNull(returnedTask);

        verify(taskRepository).save(taskRecordCaptor.capture());

        TaskRecord record = taskRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The task record is returned");
        Assertions.assertEquals(record.getId(), task.getId(), "The task id matches");
        Assertions.assertEquals(record.getName(), task.getName(), "The task name matches");
        Assertions.assertEquals(record.getDateAdded(), task.getDateAdded(), "The date added matches");
        Assertions.assertEquals(record.getCompletionDate(), task.getCompletionDate(), "The date matches");
        Assertions.assertEquals(record.getCompleted(), task.getCompleted(), "The true or false matches");
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
    void updateTaskTest() {
        //GIVEN
        String id = randomUUID().toString();

        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setId(id);
        taskRecord.setName("taskname");
        taskRecord.setDateAdded("dateadded");
        taskRecord.setCompletionDate("completiondate");
        taskRecord.setCompleted(false);

        Task task = new Task(
                taskRecord.getId(),
                taskRecord.getName(),
                taskRecord.getDateAdded(),
                taskRecord.getCompletionDate(),
                taskRecord.getCompleted());

        ArgumentCaptor<TaskRecord> recordArgumentCaptor = ArgumentCaptor.forClass(TaskRecord.class);

        //WHEN
        taskService.updateTask(task);

        //THEN
        verify(taskRepository).save(recordArgumentCaptor.capture());
        TaskRecord storedRecord = recordArgumentCaptor.getValue();

        Assertions.assertNotNull(storedRecord, "The task record is returned");
        Assertions.assertEquals(storedRecord.getId(), task.getId(), "The task id matches");
        Assertions.assertEquals(storedRecord.getName(), task.getName(), "The task name matches");
        Assertions.assertEquals(storedRecord.getDateAdded(), task.getDateAdded(), "The date added matches");
        Assertions.assertEquals(storedRecord.getCompletionDate(), task.getCompletionDate(), "The task completion date matches");
        Assertions.assertEquals(storedRecord.getCompleted(), task.getCompleted(), "The task completed flag matches");
    }
}
