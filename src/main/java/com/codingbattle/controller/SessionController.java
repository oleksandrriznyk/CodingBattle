package com.codingbattle.controller;

import com.codingbattle.entity.Session;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.User;
import com.codingbattle.service.SessionService;
import com.codingbattle.service.TaskService;
import com.codingbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/session")
    public Session save(@RequestParam("playerFirst") String playerFirst,
                        @RequestParam(name = "playerSecond", required = false) String playerSecond) {
        User playerOne = userService.findOne(playerFirst);
        User playerTwo = userService.findOne(playerSecond);
        Task task = taskService.findRandom();
        return null;
//        return sessionService.save(new Session(UUID.randomUUID(), playerOne, playerTwo, task));
    }

}
