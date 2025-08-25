package com.furryhub.pet_service.service;


import com.furryhub.pet_service.dto.PetRequest;
import com.furryhub.pet_service.dto.PetResponse;
import com.furryhub.pet_service.entity.Pet;
import com.furryhub.pet_service.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public PetResponse addPet(PetRequest request, String ownerEmail) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setType(request.getType());
        pet.setAge(request.getAge());
        pet.setOwnerEmail(ownerEmail);
        Pet saved = petRepository.save(pet);
        return new PetResponse(saved.getId(), saved.getName(), saved.getType(), saved.getAge());
    }


    public List<PetResponse> getMyPets(String ownerEmail) {
        return petRepository.findByOwnerEmail(ownerEmail)
                .stream()
                .map(p -> new PetResponse(p.getId(), p.getName(), p.getType(), p.getAge()))
                .collect(Collectors.toList());
    }


    public void deletePet(Long id, String ownerEmail) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        if (!pet.getOwnerEmail().equals(ownerEmail)) {
            throw new RuntimeException("Not allowed");
        }
        petRepository.delete(pet);
    }
}