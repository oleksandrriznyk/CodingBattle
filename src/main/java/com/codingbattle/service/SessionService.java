package com.codingbattle.service;

import com.codingbattle.entity.Session;

public interface SessionService {

    Session save(Session session);
    Session findOne(String sessionId);

}
