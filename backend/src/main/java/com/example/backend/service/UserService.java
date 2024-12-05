package com.example.backend.service;

import com.example.backend.model.User;

import java.util.List;

public interface UserService {
    User getUserById(int userId);
    List<User> getAllUsers();
    void deleteUserById(int userId);
    User updateUser( int userId, User userDetails);
    User createUser(User user);
}
