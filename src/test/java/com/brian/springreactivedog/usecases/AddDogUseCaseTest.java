package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.publisher.DogPublisher;
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
class  AddDogUseCaseTest{

    @Mock
    private IDogRepository iDogRepository;

    private ModelMapper mapper;
    private AddDogUseCase addDogUseCase;

    @Mock
    private DogPublisher dogPublisher;

    @BeforeEach
    void setup() {
        mapper = new ModelMapper();
        addDogUseCase = new AddDogUseCase(iDogRepository, mapper, dogPublisher);
    }

    @Test
    @DisplayName("Add Dog successfully")
    void successScenario() {

        Dog dog = new Dog();
        dog.setId("5");
        dog.setName("test name");
        dog.setBreed("test breed");
        dog.setAge(11);
        dog.setColor("test color");


        String dogWalkerId = "2";

        Mockito.when(iDogRepository.findById("5")).thenReturn(Mono.just(dog));
        Mockito.when(iDogRepository.save(dog)).thenReturn(Mono.just(dog));

        var result = addDogUseCase.subscribeClass(dog.getId(), dogWalkerId);

        StepVerifier.create(result)
                .expectNext(mapper.map(dog, DogDTO.class))
                .verifyComplete();

        Mockito.verify(iDogRepository).save(dog);
        Mockito.verify(iDogRepository).findById(ArgumentMatchers.anyString());
    }

}