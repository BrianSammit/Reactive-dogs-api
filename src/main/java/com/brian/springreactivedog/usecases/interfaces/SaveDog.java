package com.brian.springreactivedog.usecases.interfaces;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveDog {
    Mono<DogDTO> save(DogDTO dogDTO);
}
