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

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findOne(id);
    }

    @PostMapping
    public User saveUser(@RequestParam(name = "userId") String id) {
        return userRepository.save(new User(id));
    }

}
