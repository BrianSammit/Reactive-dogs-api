package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
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
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateDogUseCaseTest {

    @Mock
    IDogRepository repository;
    ModelMapper modelMapper;
    UpdateDogUseCase updateDogUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateDogUseCase = new UpdateDogUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("update_Success")
    void updateDog() {

        Dog dog = new Dog();
        dog.setName("Test name");
        dog.setBreed("Test breed");
        dog.setAge(3);
        dog.setColor("Test color");

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dog);
                });
        Mockito.when(repository.save(dog)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dog);
                });

        Mono<DogDTO> response = updateDogUseCase.update("Test id", modelMapper.map(dog, DogDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dog, DogDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).save(dog);
        Mockito.verify(repository).findById("Test id");
    }


}