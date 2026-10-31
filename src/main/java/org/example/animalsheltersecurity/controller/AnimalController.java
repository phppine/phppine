package org.example.animalsheltersecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.dto.AnimalCreateRequest;
import org.example.animalsheltersecurity.dto.AnimalResponse;
import org.example.animalsheltersecurity.dto.AnimalUpdateRequest;
import org.example.animalsheltersecurity.model.Animal;
import org.example.animalsheltersecurity.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> findAll() {
        List<AnimalResponse> animals = animalService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(animalService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> create(@Valid @RequestBody AnimalCreateRequest request) {
        Animal created = animalService.create(toEntity(request));
        return ResponseEntity
                .created(URI.create("/api/animals/" + created.getId()))
                .body(toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnimalResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AnimalUpdateRequest request
    ) {
        Animal updated = animalService.update(id, toPatch(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Animal toEntity(AnimalCreateRequest request) {
        return Animal.builder()
                .nickname(request.nickname())
                .species(request.species())
                .age(request.age())
                .status(request.status())
                .build();
    }

    private Animal toPatch(AnimalUpdateRequest request) {
        Animal animal = new Animal();
        animal.setNickname(request.nickname());
        animal.setSpecies(request.species());
        animal.setAge(request.age());
        animal.setStatus(request.status());
        return animal;
    }

    private AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getNickname(),
                animal.getSpecies(),
                animal.getAge(),
                animal.getStatus()
        );
    }
}
