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

    public Mono<DogDTO> subscribeClass(String dogId, String dogWalkerId){
        return repository
                .findById(dogId)
                .switchIfEmpty(Mono.empty())
                .flatMap(repository::save)
                .map(dog -> mapper.map(dog, DogDTO.class))
                .doOnSuccess(dogDTO -> {
                    try {
                        dogPublisher.publish(dogWalkerId, dogDTO);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}