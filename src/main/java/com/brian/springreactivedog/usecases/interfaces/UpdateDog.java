package com.brian.springreactivedog.usecases.interfaces;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateDog {
    Mono<DogDTO> update(String id, DogDTO dogDTO);
}
