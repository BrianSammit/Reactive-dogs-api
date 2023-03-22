package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.repository.IDogRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllDogsUseCase implements Supplier<Flux<DogDTO>> {
    private final IDogRepository dogRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<DogDTO> get() {
        return this.dogRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(dog -> mapper.map(dog, DogDTO.class));
    }
}
