package com.codingbattle.controller;

import com.codingbattle.entity.Session;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.User;
import com.codingbattle.service.SessionService;
import com.codingbattle.service.TaskService;
import com.codingbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    CountDownLatch countDownLatch = new CountDownLatch(2);
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostMapping("/prepareSession")
    public UUID prepareSession(@RequestParam("taskName") String taskName) throws InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User playerOne = userService.findOne(currentPrincipalName);
        Task task = taskService.findOne(taskName);
        UUID sessionId = UUID.randomUUID();
        sessionService.save(new Session(sessionId, playerOne, null, task));
        

        return sessionId;
    }

    @GetMapping("/connect")
    public Task connect(@RequestParam("sessionId") String sessionId) throws InterruptedException {

        Session session = sessionService.findOne(sessionId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User playerSecond = userService.findOne(currentPrincipalName);
        session.setPlayerSecond(playerSecond);
        sessionService.save(session);
        return session.getTask();
    }

}
