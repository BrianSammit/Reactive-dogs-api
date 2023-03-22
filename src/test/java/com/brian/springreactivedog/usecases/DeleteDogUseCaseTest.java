package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.repository.IDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DeleteDogUseCaseTest {

    @Mock
    IDogRepository repository;
    DeleteDogUseCase deleteDogUseCase;

    @BeforeEach
    void init() {
        deleteDogUseCase = new DeleteDogUseCase(repository);
    }

    @Test
    @DisplayName("delete_Success")
    void deleteDog() {

        Dog dog = new Dog();
        dog.setName("Test name");
        dog.setBreed("Test breed");
        dog.setAge(3);
        dog.setColor("Test color");

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dog);
                });
        Mockito.when(repository.delete(dog)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.empty();
                });

        var response = deleteDogUseCase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).delete(dog);
        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
    }

}