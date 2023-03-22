package com.brian.springreactivedog.repository;

import com.brian.springreactivedog.domain.collection.Dog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDogRepository extends ReactiveMongoRepository<Dog, String> {
}
