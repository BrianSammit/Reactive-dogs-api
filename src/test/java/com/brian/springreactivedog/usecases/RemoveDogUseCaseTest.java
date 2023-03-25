package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.publisher.DogPublisher;
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
class RemoveDogUseCaseTest {

    @Mock
    private IDogRepository iDogRepository;

    private ModelMapper mapper;
    private RemoveDogUseCase removeDogUseCase;

    @Mock
    private DogPublisher dogPublisher;

    @BeforeEach
    void setup() {
        mapper = new ModelMapper();
        removeDogUseCase = new RemoveDogUseCase(iDogRepository, mapper, dogPublisher);
    }

    @Test
    @DisplayName("Remove Dog error")
    void errorScenario() {

        Dog dog = new Dog();
        dog.setId("5");
        dog.setName("test name");
        dog.setBreed("test breed");
        dog.setAge(11);
        dog.setColor("test color");


        String dogWalkerId = "2";

        Mockito.when(iDogRepository.findById("5")).thenReturn(Mono.just(dog));

        var result = removeDogUseCase.subscribeClass(dog.getId(), dogWalkerId);

        StepVerifier.create(result)
                .expectError()
                .verify();

    }

}
