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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SaveDogUseCaseTest {

    @Mock
    IDogRepository repository;
    ModelMapper modelMapper;
    SaveDogUseCase saveDogUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveDogUseCase = new SaveDogUseCase(repository, modelMapper);
    }

    @Test
    @DisplayName("save_Success")
    void getAllBooks() {

        Dog dog = new Dog();
        dog.setName("Test name");
        dog.setBreed("Test breed");
        dog.setAge(3);
        dog.setColor("Test color");

        Mockito.when(repository.save(dog)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(dog);
                });

        Mono<DogDTO> response = saveDogUseCase.save(modelMapper.map(dog, DogDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(dog,DogDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).save(dog);
    }


}
