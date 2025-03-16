package com.example.demo_testcontainer;


import com.example.demo_testcontainer.model.UserDao;
import com.example.demo_testcontainer.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends BaseTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDao testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Xóa dữ liệu trước mỗi test
        testUser = new UserDao(null, "hien", "abcd@gmail.com");
        testUser = userRepository.save(testUser);
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testUser.getName())))
                .andExpect(jsonPath("$[0].email", is(testUser.getEmail())));
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testUser.getName())))
                .andExpect(jsonPath("$.email", is(testUser.getEmail())));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        mockMvc.perform(get("/users/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDao newUser = new UserDao(null, "Alice", "alice@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is(newUser.getName())))
                .andExpect(jsonPath("$.email", is(newUser.getEmail())));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDao updatedUser = new UserDao(null, "Updated Name", "updated@example.com");

        mockMvc.perform(put("/users/{id}", testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        UserDao updatedUser = new UserDao(null, "bbbb", "bbbb@gmail.com");

        mockMvc.perform(put("/users/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", testUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        mockMvc.perform(delete("/users/{id}", 999))
                .andExpect(status().isNoContent());
    }
}