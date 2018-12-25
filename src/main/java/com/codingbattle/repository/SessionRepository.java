package com.codingbattle.repository;

import com.codingbattle.entity.Session;
import com.codingbattle.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

    @Query("{ 'playerSecond': ?0}")
    List<Session> findAllWithOnePlayer(User playerSecond);
}
