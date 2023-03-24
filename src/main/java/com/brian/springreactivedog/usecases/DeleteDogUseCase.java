package com.brian.springreactivedog.usecases;

import com.brian.springreactivedog.repository.IDogRepository;
import com.brian.springreactivedog.usecases.interfaces.DeleteDog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteDogUseCase implements DeleteDog {
    private final IDogRepository dogRepository;

    @Override
    public Mono<Void> delete(String id) {
        return dogRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Dog not found")))
                .flatMap(dogRepository::delete)
                .onErrorResume(Mono::error);

    }
}
