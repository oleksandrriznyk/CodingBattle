package com.codingbattle.repository;

import com.codingbattle.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
}
