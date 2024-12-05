package com.example.backend.service;

import com.example.backend.exception.UserAlreadyExistException;
import com.example.backend.exception.UserNotExistException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not found with ID: " + userId));
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void deleteUserById(int userId) {
        Optional<User> fetchedUser = userRepository.findById(userId);
        if (fetchedUser.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotExistException("User not found with ID: ");
        }
    }
    @Override
    public User updateUser(int userId, User userDetails) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();

            userToUpdate.setUserName(userDetails.getUserName());
            userToUpdate.setEmail(userDetails.getEmail());
            userToUpdate.setDateOfBirth(userDetails.getDateOfBirth());
            userToUpdate.setAddress(userDetails.getAddress());
            userToUpdate.setContactInformation(userDetails.getContactInformation());

            return userRepository.save(userToUpdate);
        } else {
            throw new UserNotExistException("User not found with ID: " + userId);
        }
    }

    @Override
    public User createUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistException("User already exists with email: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
