package com.codingbattle.controller;

import com.codingbattle.dto.TaskDto;
import com.codingbattle.entity.Session;
import com.codingbattle.entity.SessionResult;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.User;
import com.codingbattle.security.service.SecurityService;
import com.codingbattle.service.SessionService;
import com.codingbattle.service.TaskService;
import com.codingbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @Autowired
    private SecurityService securityService;

    CountDownLatch countDownLatch = new CountDownLatch(2);
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostMapping("/prepareSession")
    public String prepareSession(@RequestParam("taskName") String taskName) throws InterruptedException {

        User playerOne = securityService.getCurrentUser();
        Task task = taskService.findOne(taskName);
        UUID sessionId = UUID.randomUUID();
        sessionService.save(new Session(sessionId, playerOne, null, task));
        

        return sessionId.toString();
    }

    @GetMapping("/connect")
    public TaskDto connect(@RequestParam("sessionId") String sessionId) throws InterruptedException {


        Session session = sessionService.findOne(sessionId);

        User playerSecond = securityService.getCurrentUser();
        session.setPlayerSecond(playerSecond);
        sessionService.save(session);
        return sessionStart(sessionId);
    }

    @SendTo("/session/{sessionId}")
    public TaskDto sessionStart(@DestinationVariable String sessionId){
        Session session = sessionService.findOne(sessionId);
        Task task = session.getTask();
        return new TaskDto(task, sessionId);

    }

    @GetMapping("/session/{sessionId}/end")
    public SessionResult sessionEnd(@RequestParam String sessionId){
        return sessionService.findOne(sessionId).getSessionResult();
    }

}
