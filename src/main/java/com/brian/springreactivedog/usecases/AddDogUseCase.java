package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.publisher.DogPublisher;
import com.brian.springreactivedog.repository.IDogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AddDogUseCase {

    private final IDogRepository repository;
    private final ModelMapper mapper;
    private final DogPublisher dogPublisher;

    public Mono<DogDTO> subscribeClass(String dogId, String dogWlkId){
        return repository
                .findById(dogId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("The dog does not exist")))
                .flatMap(dog -> {
                    if (dog.getIsAdded()) {
                        return Mono.error(new IllegalArgumentException("The dog is already added"));
                    }else {
                        dog.setIsAdded(true);
                        return this.repository.save(dog);
                    }
                        }
                )
                .map(dog -> mapper.map(dog, DogDTO.class))
                .doOnSuccess(dogDTO -> {
                    try {
                        dogPublisher.publish(dogWlkId, dogDTO);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(dogPublisher::publishError)
                .onErrorResume(Mono::error);
    }
}