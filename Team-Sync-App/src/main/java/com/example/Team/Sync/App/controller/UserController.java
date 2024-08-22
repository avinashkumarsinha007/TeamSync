package com.example.Team.Sync.App.controller;

import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(
            user.getUser_name(),
            user.getEmail(),
            user.getPhone(),
            user.getPassword(),
            user.getRole(),
            user.getDepartment_id()
        );
        return ResponseEntity.ok(createdUser);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get a user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        User user = userService.getUserByUserName(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<Map<Long, User>> getAllUsers() {
        Map<Long, User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint to get the role of a user by ID
    @GetMapping("/{id}/role")
    public ResponseEntity<User.Role> getUserRoleById(@PathVariable Long id) {
        User.Role role = userService.getUserRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
