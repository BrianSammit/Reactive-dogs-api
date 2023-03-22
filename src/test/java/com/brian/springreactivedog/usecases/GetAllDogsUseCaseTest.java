package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.repository.IDogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GetAllDogsUseCaseTest {

    @Mock
    IDogRepository repository;
    ModelMapper modelMapper;
    GetAllDogsUseCase getAllDogsUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        getAllDogsUseCase = new GetAllDogsUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAll_Success")
    void getAllDogs() {

        Dog dog = new Dog();
        dog.setName("Test name");
        dog.setBreed("Test breed");
        dog.setAge(3);
        dog.setColor("Test color");

        Dog dog2 = new Dog();
        dog.setName("Test name 2");
        dog.setBreed("Test breed 2");
        dog.setAge(5);
        dog.setColor("Test color 2");

        Mockito.when(repository.findAll()).
                thenAnswer(InvocationOnMock -> {
                    return Flux.just(dog, dog2);
                });

        Flux<DogDTO> response = getAllDogsUseCase.get();

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dog, DogDTO.class))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}