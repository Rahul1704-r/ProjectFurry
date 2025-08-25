package com.furryhub.pet_service.repository;


import com.furryhub.pet_service.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerEmail(String ownerEmail);
}