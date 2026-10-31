package org.example.animalsheltersecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.dto.EnclosureCreateRequest;
import org.example.animalsheltersecurity.dto.EnclosureResponse;
import org.example.animalsheltersecurity.dto.EnclosureUpdateRequest;
import org.example.animalsheltersecurity.model.Enclosure;
import org.example.animalsheltersecurity.service.EnclosureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
public class EnclosureController {

    private final EnclosureService enclosureService;

    @GetMapping
    public ResponseEntity<List<EnclosureResponse>> findAll() {
        List<EnclosureResponse> enclosures = enclosureService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(enclosures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnclosureResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(enclosureService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EnclosureResponse> create(@Valid @RequestBody EnclosureCreateRequest request) {
        Enclosure created = enclosureService.create(toEntity(request));
        return ResponseEntity
                .created(URI.create("/api/enclosures/" + created.getId()))
                .body(toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EnclosureResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EnclosureUpdateRequest request
    ) {
        Enclosure updated = enclosureService.update(id, toPatch(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enclosureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Enclosure toEntity(EnclosureCreateRequest request) {
        return Enclosure.builder()
                .allowedSpecies(new LinkedHashSet<>(request.allowedSpecies()))
                .capacity(request.capacity())
                .build();
    }

    private Enclosure toPatch(EnclosureUpdateRequest request) {
        Enclosure enclosure = new Enclosure();
        if (request.allowedSpecies() != null) {
            enclosure.setAllowedSpecies(new LinkedHashSet<>(request.allowedSpecies()));
        }
        enclosure.setCapacity(request.capacity());
        return enclosure;
    }

    private EnclosureResponse toResponse(Enclosure enclosure) {
        return new EnclosureResponse(
                enclosure.getId(),
                enclosure.getAllowedSpecies(),
                enclosure.getCapacity()
        );
    }
}
