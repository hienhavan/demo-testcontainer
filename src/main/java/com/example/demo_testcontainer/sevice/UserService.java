package com.example.demo_testcontainer.sevice;

import com.example.demo_testcontainer.model.UserDao;
import com.example.demo_testcontainer.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUser {
    private final UserRepository userRepository;

    @Override
    public List<UserDao> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserDao> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDao createUser(UserDao user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserDao> updateUser(Long id, UserDao newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}