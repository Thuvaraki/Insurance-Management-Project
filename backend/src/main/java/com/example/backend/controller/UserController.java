package com.example.backend.controller;

import com.example.backend.exception.UserNotExistException;
import com.example.backend.model.User;
import com.example.backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        try {
            User fetchedUser = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/get_all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> fetchedUsers = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(fetchedUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable int userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User successfully deleted!");
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(userId,userDetails);
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully! " + updatedUser);
        } catch (UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
