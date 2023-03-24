package com.brian.springreactivedog.domain.collection;

import jakarta.validation.constraints.*;
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

    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="bree is required")
    private String breed;
    @Positive
    @Max(value = 40, message = "Age should not be greater than 40")
    private Integer age;
    @NotBlank(message="Empty field error")
    @NotNull(message ="color is required")
    private String color;


    public Dog(String name, String breed, Integer age, String color) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.color = color;
    }
}
