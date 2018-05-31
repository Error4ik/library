package com.voronin.library.controllers;

import com.voronin.library.domain.User;
import com.voronin.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.05.2018.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping("/user/{id}")
    public User updateUser(@PathVariable final UUID id) {
        return this.userService.getUserById(id);
    }
}
