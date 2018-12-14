package com.codingbattle.controller;

import com.codingbattle.entity.User;
import com.codingbattle.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

}
