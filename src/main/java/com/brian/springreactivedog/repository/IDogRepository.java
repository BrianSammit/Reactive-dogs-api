package com.brian.springreactivedog.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDogRepository extends ReactiveMongoRepository<Dog, String> {
}
