package com.brian.springreactivedog.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DogWalkerDTO {
    private String id;
    private String name;
    private String lastname;
    private Integer age;
    private List<DogDTO> dogsGroup;
}
