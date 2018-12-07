package com.codingbattle.controller;

import com.codingbattle.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.codingbattle.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{login}")
    public User getUser(@PathVariable("login") String login) {
        return userRepository.findOne(login);
    }

    @PostMapping
    public User saveUser(@RequestParam(name = "login") String login,
                         @RequestParam(name = "email") String email,
                         @RequestParam(name = "password") String password) {
        return userRepository.save(new User(login, email, password));
    }

}
