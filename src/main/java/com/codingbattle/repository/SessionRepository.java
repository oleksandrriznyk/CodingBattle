package com.codingbattle.repository;

import com.codingbattle.entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends MongoRepository<Session, UUID> {
}
