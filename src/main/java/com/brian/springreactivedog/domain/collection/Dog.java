package com.brian.springreactivedog.domain.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "dogs")
public class Dog {
    @Id
    private String id;

    private String name;
    private String breed;
    private Integer age;
    private String color;


    public Dog(String name, String breed, Integer age, String color) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.color = color;
    }
}
