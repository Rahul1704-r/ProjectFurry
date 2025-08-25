package com.furryhub.pet_service.dto;

import lombok.Data;

@Data
public class PetRequest {
    private String name;
    private String type;
    private int age;
}
