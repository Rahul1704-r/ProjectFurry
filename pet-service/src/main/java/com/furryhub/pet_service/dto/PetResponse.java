package com.furryhub.pet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetResponse {
    private Long id;
    private String name;
    private String type;
    private int age;
}
