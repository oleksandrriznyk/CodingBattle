package com.codingbattle.controller;

import com.codingbattle.dto.TaskDto;
import com.codingbattle.entity.Session;
import com.codingbattle.entity.SessionResult;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.User;
import com.codingbattle.security.service.SecurityService;
import com.codingbattle.service.SessionService;
import com.codingbattle.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private static final String SESSION_RESULT_DRAW = "DRAW";

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityService securityService;

    CountDownLatch countDownLatch = new CountDownLatch(2);
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostMapping("/prepareSession")
    public String prepareSession(@RequestParam(name = "taskName") String taskName) throws InterruptedException {

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

    @MessageMapping("/gs-codingbattle")
    @SendTo("/session/{sessionId}")
    public TaskDto sessionStart(@DestinationVariable String sessionId) {
        Session session = sessionService.findOne(sessionId);
        Task task = session.getTask();
        return new TaskDto(task, sessionId);

    }

    @GetMapping("/{sessionId}/end")
    public SessionResult sessionEnd(@RequestParam String sessionId) {
        Session session = sessionService.findOne(sessionId);
        if (session.getSessionResult().getFirstPlayerExecutionTime()
                < session.getSessionResult().getSecondPlayerExecutionTime()) {
            session.getSessionResult().setWinnerLogin(session.getSessionResult().getFirstPlayerLogin());
        } else if (session.getSessionResult().getFirstPlayerExecutionTime()
                > session.getSessionResult().getSecondPlayerExecutionTime()) {
            session.getSessionResult().setWinnerLogin(session.getSessionResult().getSecondPlayerLogin());
        } else {
            session.getSessionResult().setWinnerLogin(SESSION_RESULT_DRAW);
        }
        return session.getSessionResult();
    }

    @GetMapping("/all")
    public List<Session> getAll() {
        return sessionService.findAllWithOnePlayer();
    }

    @GetMapping("/delete/{sesionId}")
    public ResponseEntity deleteSession(@PathVariable String sessionId) {
        User currentUser = securityService.getCurrentUser();
        Session session = sessionService.findOne(sessionId);
        if (session.getPlayerFirst().equals(currentUser)) {
            sessionService.delete(session);
            return ResponseEntity.ok("Session has been deleted successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This session was created by another user");
        }
    }

}
