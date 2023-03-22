package com.brian.springreactivedog.router;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.usecases.SaveDogUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DogRouter {
    @Bean
    public RouterFunction<ServerResponse> saveDog(SaveDogUseCase saveDogUseCase){
        return route(POST("/dogs").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogDTO.class)
                        .flatMap(dogDTO -> saveDogUseCase.save(dogDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }
}
