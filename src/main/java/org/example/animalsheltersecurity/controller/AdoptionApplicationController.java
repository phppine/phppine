package org.example.animalsheltersecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.dto.AdoptionApplicationCreateRequest;
import org.example.animalsheltersecurity.dto.AdoptionApplicationResponse;
import org.example.animalsheltersecurity.dto.AdoptionApplicationUpdateRequest;
import org.example.animalsheltersecurity.model.AdoptionApplication;
import org.example.animalsheltersecurity.service.AdoptionApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/adoption-applications")
@RequiredArgsConstructor
public class AdoptionApplicationController {

    private final AdoptionApplicationService adoptionApplicationService;

    @GetMapping
    public ResponseEntity<List<AdoptionApplicationResponse>> findAll() {
        List<AdoptionApplicationResponse> applications = adoptionApplicationService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionApplicationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(adoptionApplicationService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AdoptionApplicationResponse> create(
            @Valid @RequestBody AdoptionApplicationCreateRequest request
    ) {
        AdoptionApplication created = adoptionApplicationService.create(
                request.animalId(), request.applicantId(), request.status());
        return ResponseEntity
                .created(URI.create("/api/adoption-applications/" + created.getId()))
                .body(toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdoptionApplicationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AdoptionApplicationUpdateRequest request
    ) {
        AdoptionApplication updated = adoptionApplicationService.update(
                id, request.animalId(), request.applicantId(), request.status());
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adoptionApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private AdoptionApplicationResponse toResponse(AdoptionApplication application) {
        return new AdoptionApplicationResponse(
                application.getId(),
                application.getAnimal().getId(),
                application.getAnimal().getNickname(),
                application.getApplicant().getId(),
                application.getApplicant().getUsername(),
                application.getStatus()
        );
    }
}
