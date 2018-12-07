package com.codingbattle.controller;

import com.codingbattle.entity.User;
import com.codingbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{login}")
    public User getUser(@PathVariable("login") String login) {
        return userService.findOne(login);
    }

    @PostMapping
    public User saveUser(@RequestParam(name = "login") String login,
                         @RequestParam(name = "email") String email,
                         @RequestParam(name = "password") String password) {
        return userService.save(new User(login, email, password));
    }

}
