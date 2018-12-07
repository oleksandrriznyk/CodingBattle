package com.codingbattle.service;

import com.codingbattle.entity.User;

public interface UserService {
    User save(User user);

    User findOne(String login);
}
