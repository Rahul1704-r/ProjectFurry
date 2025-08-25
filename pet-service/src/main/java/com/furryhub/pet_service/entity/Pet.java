package com.furryhub.pet_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type;

    private int age;

    @Column(nullable = false)
    private String ownerEmail;
}
