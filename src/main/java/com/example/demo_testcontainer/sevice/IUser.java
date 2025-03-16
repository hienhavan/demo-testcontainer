package com.example.demo_testcontainer.sevice;

import com.example.demo_testcontainer.model.UserDao;

import java.util.List;
import java.util.Optional;

public interface IUser {
    List<UserDao> getAllUsers();
    Optional<UserDao> getUserById(Long id);
    UserDao createUser(UserDao user);
    Optional<UserDao> updateUser(Long id, UserDao newUser);
    void deleteUser(Long id);
}
