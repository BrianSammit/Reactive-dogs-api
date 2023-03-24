package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.repository.IDogRepository;
import com.brian.springreactivedog.usecases.interfaces.SaveDog;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveDogUseCase implements SaveDog {

    private final IDogRepository dogRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DogDTO> save(DogDTO dogDTO) {
        return this.dogRepository
                .save(mapper.map(dogDTO, Dog.class))
                .switchIfEmpty(Mono.empty())
                .map(dog -> mapper.map(dog, DogDTO.class))
                .onErrorResume(Mono::error);
    }
}
