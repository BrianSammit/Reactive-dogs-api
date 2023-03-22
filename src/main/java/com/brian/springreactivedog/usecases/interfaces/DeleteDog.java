package com.brian.springreactivedog.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteDog {
    Mono<Void> delete(String id);
}
