package com.codingbattle.service;

import com.codingbattle.entity.Session;

import java.util.List;

public interface SessionService {

    Session save(Session session);
    Session findOne(String sessionId);

    List<Session> findAllWithOnePlayer();

    void delete(Session session);

    List<Session> findAll();

    void deleteAll();
}
