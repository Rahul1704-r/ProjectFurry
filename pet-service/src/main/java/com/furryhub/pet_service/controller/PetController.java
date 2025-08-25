package com.furryhub.pet_service.controller;

import com.furryhub.pet_service.dto.PetRequest;
import com.furryhub.pet_service.dto.PetResponse;
import com.furryhub.pet_service.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {


    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetResponse> addPet(@RequestBody PetRequest request,
                                              @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(petService.addPet(request, email));
    }


    @GetMapping
    public ResponseEntity<List<PetResponse>> getMyPets(@RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(petService.getMyPets(email));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Long id,
                                            @RequestHeader("X-User-Email") String email) {
        petService.deletePet(id, email);
        return ResponseEntity.ok("Pet deleted");
    }
}