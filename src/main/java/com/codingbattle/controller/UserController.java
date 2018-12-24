package com.codingbattle.controller;

import com.codingbattle.dto.ResponseDto;
import com.codingbattle.entity.User;
import com.codingbattle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String ALREADY_EXISTS = "User with this login already exists";
    private static final String SUCCESSFUL_REGISTRATION = "User has been registered successful";

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
    public ResponseEntity signUp(@RequestBody User user) {
        User existedUser = userService.findOne(user.getLogin());
        if(existedUser!=null){
            ResponseDto error = new ResponseDto(HttpStatus.CONFLICT.toString(), ALREADY_EXISTS);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        ResponseDto response = new ResponseDto(HttpStatus.OK.toString(), SUCCESSFUL_REGISTRATION);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
