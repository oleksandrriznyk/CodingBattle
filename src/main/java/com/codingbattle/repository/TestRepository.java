package com.codingbattle.repository;

import com.codingbattle.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {
}
