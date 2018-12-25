package com.codingbattle.controller;

import com.codingbattle.dto.TaskDto;
import com.codingbattle.entity.Session;
import com.codingbattle.entity.SessionResult;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.User;
import com.codingbattle.security.service.SecurityService;
import com.codingbattle.service.SessionService;
import com.codingbattle.service.TaskService;
import com.codingbattle.worker.PlayersSync;
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
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private static final String SESSION_RESULT_DRAW = "DRAW";
    private static final Long THREE_MINUTES = 1000 * 60 * 3L;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PlayersSync playersSync;

    @PostMapping("/prepareSession")
    public DeferredResult<ResponseEntity<?>> prepareSession() {

        DeferredResult<ResponseEntity<?>> unconnectedSession = new DeferredResult<>(THREE_MINUTES);
        User playerOne = securityService.getCurrentUser();
        Task task = taskService.findRandom();
        UUID sessionId = UUID.randomUUID();
        Session session = sessionService.save(new Session(sessionId, playerOne, null, task));
        playersSync.add(sessionId.toString(), unconnectedSession);
        unconnectedSession.onTimeout(() -> {
            playersSync.delete(sessionId.toString());
            sessionService.delete(session);
            unconnectedSession.setResult(ResponseEntity.ok("Session has been expired"));
        });
        unconnectedSession.onCompletion(() -> ResponseEntity.ok(task));

        return unconnectedSession;
    }

    @GetMapping("/{sessionId}")
    public Session getById(@PathVariable String sessionId) {
        return sessionService.findOne(sessionId);
    }

    @GetMapping("/connect")
    public DeferredResult<ResponseEntity<?>> connect(@RequestParam("sessionId") String sessionId) {
        DeferredResult deferredResult = playersSync.get(sessionId);
        if (deferredResult != null) {
            Session session = sessionService.findOne(sessionId);
            User playerSecond = securityService.getCurrentUser();
            session.setPlayerSecond(playerSecond);
            deferredResult.setResult(ResponseEntity.ok(session.getTask()));
        }
        return deferredResult;
    }

    @GetMapping("/connectByLogin")
    public DeferredResult<ResponseEntity<?>> conncetByLogin(@RequestParam("login") String login) {
        Session session = sessionService.findAllWithOnePlayer().stream().filter(s -> s.getPlayerFirst().getLogin().equals(login)).findFirst().get();

        DeferredResult deferredResult = playersSync.get(session.getId().toString());
        if (deferredResult != null) {
            User playerSecond = securityService.getCurrentUser();
            session.setPlayerSecond(playerSecond);
            deferredResult.setResult(ResponseEntity.ok(session.getTask()));
        }
        return deferredResult;
    }

    @MessageMapping("/gs-codingbattle/{topic}")
    @SendTo("/topic/messages")
    public TaskDto sessionStart(@DestinationVariable String topic) {
        Session session = sessionService.findOne(topic);
        User playerOne = securityService.getCurrentUser();
        if (session == null) {
            Task task = taskService.findRandom();
            sessionService.save(new Session(UUID.fromString(topic), playerOne, null, task));
        } else {
            session.setPlayerSecond(playerOne);
            sessionService.save(session);
        }
        return new TaskDto(session.getTask(), session.getId().toString());

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

    @GetMapping("/delete/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId) {
        User currentUser = securityService.getCurrentUser();
        Session session = sessionService.findOne(sessionId);
        if (session.getPlayerFirst().equals(currentUser)) {
            sessionService.delete(session);
            return ResponseEntity.ok("Session has been deleted successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This session was created by another user");
        }
    }

    @GetMapping("/delete/all")
    public void deleteAllSessions() {
        List<Session> sessions = sessionService.findAll();
        sessionService.deleteAll();
    }


}
