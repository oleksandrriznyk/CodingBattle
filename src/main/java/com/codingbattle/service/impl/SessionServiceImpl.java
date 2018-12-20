package com.codingbattle.service.impl;

import com.codingbattle.entity.Session;
import com.codingbattle.repository.SessionRepository;
import com.codingbattle.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session findOne(String sessionId) {
        return sessionRepository.findOne(UUID.fromString(sessionId));
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
}
