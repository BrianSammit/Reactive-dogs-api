package com.brian.springreactivedog.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogDTO {
    private String id;
    private String name;
    private String breed;
    private Integer age;
    private String color;


    public DogDTO(String name, String breed, Integer age, String color) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.color = color;
    }
}
