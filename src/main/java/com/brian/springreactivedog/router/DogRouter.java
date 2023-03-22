package com.brian.springreactivedog.router;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
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


    @Bean
    public RouterFunction<ServerResponse> getAllDogs(GetAllDogsUseCase getAllDogsUseCase) {
        return route(GET("/dogs"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllDogsUseCase.get(), DogDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getDogsById(GetDogsByIdUseCase getDogsByIdUseCase) {
        return route(GET("/dogs/{id}"),
                request -> getDogsByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(dogDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dogDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteStudents(DeleteDogUseCase deleteDogUseCase){
        return  route(DELETE("/dogs/{id}"),
                request -> deleteDogUseCase.delete(request.pathVariable("id"))
                        .flatMap(dogDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Dog Deleted"))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateDog(UpdateDogUseCase updateDogUseCase){
        return route(PUT("/dogs/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogDTO.class)
                        .flatMap(dogDTO -> updateDogUseCase.update(request.pathVariable("id"), dogDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
                        )
        );
    }
}
