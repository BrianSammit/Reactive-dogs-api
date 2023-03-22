package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.repository.IDogRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetDogsByIdUseCase implements Function<String, Mono<DogDTO>> {
    private final IDogRepository dogRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DogDTO> apply(String id) {
        return this.dogRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(dog-> mapper.map(dog, DogDTO.class));
    }
}
