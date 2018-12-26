package com.codingbattle.service.impl;

import com.codingbattle.entity.Session;
import com.codingbattle.entity.User;
import com.codingbattle.repository.SessionRepository;
import com.codingbattle.repository.UserRepository;
import com.codingbattle.service.SessionService;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    private UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository,
                              UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Session save(Session session) {
        Session tempSession  = sessionRepository.findOne(session.getId());
        if(tempSession!=null){
            tempSession.setPlayerSecond(session.getPlayerSecond());
            tempSession.setPlayerFirst(session.getPlayerFirst());
            tempSession.setSessionResult(session.getSessionResult());
            tempSession.setTask(session.getTask());
            return sessionRepository.save(tempSession);
        }
        return sessionRepository.save(session);
    }

    @Override
    public Session findOne(String sessionId) {
        return sessionRepository.findOne(sessionId);
    }

    @Override
    public List<Session> findAllWithOnePlayer() {
        return sessionRepository.findAllWithOnePlayer(null);
    }

    @Override
    public void delete(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public void deleteAll() {
        sessionRepository.deleteAll();
    }

    @Override
    public List<Session> findFinishedByUser(User user) {
        List<Session> ifFirstPlayer = sessionRepository.findByPlayerFirst(user);
        List<Session> ifSecondPlayer = sessionRepository.findByPlayerSecond(user);
        ifFirstPlayer.addAll(ifSecondPlayer);
        return ifFirstPlayer;
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.findAll();
    }
}
