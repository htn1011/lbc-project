package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
    void addNewConcert() {
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

}
