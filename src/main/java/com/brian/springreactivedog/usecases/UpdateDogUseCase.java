package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.domain.collection.Dog;
import com.brian.springreactivedog.repository.IDogRepository;
import com.brian.springreactivedog.usecases.interfaces.UpdateDog;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateDogUseCase implements UpdateDog {
    private final IDogRepository dogRepository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<DogDTO> update(String id, DogDTO dogDTO) {
        return this.dogRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(dog -> {
                    dogDTO.setId(dog.getId());
                    return dogRepository.save(modelMapper.map(dogDTO, Dog.class));
                })
                .map(dog -> modelMapper.map(dog, DogDTO.class))
                .onErrorResume(error -> Mono.error(new RuntimeException("Dog not found")));
    }
}
