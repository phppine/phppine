package org.example.animalsheltersecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.dto.AnimalHandoverRecordCreateRequest;
import org.example.animalsheltersecurity.dto.AnimalHandoverRecordResponse;
import org.example.animalsheltersecurity.dto.AnimalHandoverRecordUpdateRequest;
import org.example.animalsheltersecurity.model.AnimalHandoverRecord;
import org.example.animalsheltersecurity.service.AnimalHandoverRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/animal-handover-records")
@RequiredArgsConstructor
public class AnimalHandoverRecordController {

    private final AnimalHandoverRecordService handoverRecordService;

    @GetMapping
    public ResponseEntity<List<AnimalHandoverRecordResponse>> findAll() {
        List<AnimalHandoverRecordResponse> records = handoverRecordService.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalHandoverRecordResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(handoverRecordService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AnimalHandoverRecordResponse> create(
            @Valid @RequestBody AnimalHandoverRecordCreateRequest request
    ) {
        AnimalHandoverRecord created = handoverRecordService.create(
                request.applicationId(), request.status());
        return ResponseEntity
                .created(URI.create("/api/animal-handover-records/" + created.getId()))
                .body(toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnimalHandoverRecordResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AnimalHandoverRecordUpdateRequest request
    ) {
        AnimalHandoverRecord updated = handoverRecordService.update(
                id, request.applicationId(), request.status());
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        handoverRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private AnimalHandoverRecordResponse toResponse(AnimalHandoverRecord record) {
        return new AnimalHandoverRecordResponse(
                record.getId(),
                record.getApplication().getId(),
                record.getPreparationDate(),
                record.getConfirmationDate(),
                record.getStatus()
        );
    }
}
