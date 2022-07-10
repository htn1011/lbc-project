package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.TaskCreateRequest;
import com.kenzie.appserver.controller.model.TaskUpdateRequest;
import com.kenzie.appserver.service.TaskService;
import com.kenzie.appserver.service.model.Task;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskService taskService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getConcert_ConcertExists() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String dateAdded = mockNeat.strings().valStr();
        String completionDate = mockNeat.strings().valStr();

        Task task = new Task(id, name, dateAdded, completionDate, false);
        Task persistedTask = taskService.addNewTask(task);

        // WHEN
        mvc.perform(get("/task/{id}", persistedTask.getId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("dateAdded")
                        .value(is(dateAdded)))
//                .andExpect(jsonPath("completionDate")
//                        .value(is(completionDate)))
                .andExpect(jsonPath("completed")
                        .value(is(false)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTask_TaskDoesNotExist() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        // WHEN
        mvc.perform(get("/task/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTask_CreateSuccessful() throws Exception {
        // GIVEN

        String name = mockNeat.strings().valStr();
        String dateAdded = mockNeat.strings().valStr();
        String completionDate = mockNeat.strings().valStr();

        TaskCreateRequest taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setId(UUID.randomUUID().toString());
        taskCreateRequest.setDateAdded(dateAdded);
        taskCreateRequest.setName(name);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/task")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskCreateRequest)))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(jsonPath("dateAdded")
                        .value(is(dateAdded)))
//                .andExpect(jsonPath("completionDate")
//                        .value(is(completionDate)))
                .andExpect(jsonPath("completed")
                        .value(is(false)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTask_PutSuccessful() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String dateAdded = mockNeat.strings().valStr();
        String completionDate= mockNeat.strings().valStr();

        Task task = new Task(id, name, dateAdded, completionDate, false);
        Task persistedTask = taskService.addNewTask(task);

        String newName = mockNeat.strings().valStr();
        String newCompletionDate = mockNeat.strings().valStr();

        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(id);
        taskUpdateRequest.setDateAdded(dateAdded);
        taskUpdateRequest.setName(newName);
        taskUpdateRequest.setCompletionDate(newCompletionDate);
        taskUpdateRequest.setCompleted(true);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/task")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskUpdateRequest)))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(newName)))
                .andExpect(jsonPath("dateAdded")
                        .value(is(dateAdded)))
//                .andExpect(jsonPath("completionDate")
//                        .value(is(newCompletionDate)))
                .andExpect(jsonPath("completed")
                        .value(is(true)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteConcert_DeleteSuccessful() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        String name = mockNeat.strings().valStr();
        String dateAdded = mockNeat.strings().valStr();
        String completionDate = mockNeat.strings().valStr();

        Task task = new Task(id, name, dateAdded, completionDate, false);
        Task persistedTask = taskService.addNewTask(task);

        // WHEN
        mvc.perform(delete("/task/{id}", persistedTask.getId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNoContent());
        assertThat(taskService.findByTaskId(id)).isNull();
    }
}
