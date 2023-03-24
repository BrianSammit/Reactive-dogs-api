package com.brian.springreactivedog.router;

import com.brian.springreactivedog.domain.DTO.DogDTO;
import com.brian.springreactivedog.domain.DTO.DogWalkerDTO;
import com.brian.springreactivedog.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DogRouter {
    private WebClient walkerAPI;

    public DogRouter() {
        walkerAPI = WebClient.create("http://localhost:8081");
    }
    @Bean
    public RouterFunction<ServerResponse> saveDog(SaveDogUseCase saveDogUseCase){
        return route(POST("/dogs").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogDTO.class)
                        .flatMap(dogDTO -> saveDogUseCase.save(dogDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE)
                                        .bodyValue(throwable.getMessage()))));
    }


    @Bean
    public RouterFunction<ServerResponse> getAllDogs(GetAllDogsUseCase getAllDogsUseCase) {
        return route(GET("/dogs"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllDogsUseCase.get(), DogDTO.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getDogsById(GetDogsByIdUseCase getDogsByIdUseCase) {
        return route(GET("/dogs/{id}"),
                request -> getDogsByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(dogDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dogDTO))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteDog(DeleteDogUseCase deleteDogUseCase){
        return  route(DELETE("/dogs/{id}"),
                request -> deleteDogUseCase.delete(request.pathVariable("id"))
                        .flatMap(dogDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Dog Deleted"))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT)
                                .bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> updateDog(UpdateDogUseCase updateDogUseCase){
        return route(PUT("/dogs/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DogDTO.class)
                        .flatMap(dogDTO -> updateDogUseCase.update(request.pathVariable("id"), dogDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addDogToWalker(AddDogUseCase addDogUseCase) {
        return route(
                PUT("/dogs/{dogId}/add_to_walker/{wlkId}"),
                request -> walkerAPI.get()
                        .uri("/dogWalker/{dogId}", request.pathVariable("wlkId"))
                        .retrieve()
                        .bodyToMono(DogWalkerDTO.class)
                        .flatMap(dogWalkerDTO -> addDogUseCase
                                .subscribeClass(request.pathVariable("dogId"),  request.pathVariable("wlkId"))
                                .flatMap(dogDTO -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(dogDTO))
                                .onErrorResume(throwable -> ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> removeToWalker(RemoveDogUseCase removeDogUseCase) {
        return route(
                PUT("/dogs/{dogId}/remove_to_walker/{wlkId}"),
                request -> walkerAPI.get()
                        .uri("/dogWalker/{dogId}", request.pathVariable("wlkId"))
                        .retrieve()
                        .bodyToMono(DogWalkerDTO.class)
                        .flatMap(dogWalkerDTO -> removeDogUseCase
                                .subscribeClass(request.pathVariable("dogId"),  request.pathVariable("wlkId"))
                                .flatMap(dogDTO -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(dogDTO))
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(throwable.getMessage())))
        );
    }
}
