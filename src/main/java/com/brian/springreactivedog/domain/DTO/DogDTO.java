package com.brian.springreactivedog.domain.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogDTO {
    private String id;
    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="breed is required")
    private String breed;
    @Positive
    @Max(value = 40, message = "Age should not be greater than 40")
    private Integer age;
    @NotBlank(message="Empty field error")
    @NotNull(message ="color is required")
    private String color;

    private Boolean isAdded;

    public DogDTO(String name, String breed, Integer age, String color) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.color = color;
    }
}
