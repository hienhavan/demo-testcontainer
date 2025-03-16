package com.example.demo_testcontainer.controller;

import com.example.demo_testcontainer.model.UserDao;
import com.example.demo_testcontainer.sevice.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUser userService;

    @GetMapping
    public List<UserDao> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDao getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    public UserDao createUser(@RequestBody UserDao user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserDao updateUser(@PathVariable Long id, @RequestBody UserDao user) {
        return userService.updateUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}