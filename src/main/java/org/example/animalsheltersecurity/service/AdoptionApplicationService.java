package org.example.animalsheltersecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.exception.ResourceNotFoundException;
import org.example.animalsheltersecurity.model.AdoptionApplication;
import org.example.animalsheltersecurity.model.Animal;
import org.example.animalsheltersecurity.model.User;
import org.example.animalsheltersecurity.repository.AdoptionApplicationRepository;
import org.example.animalsheltersecurity.repository.AnimalHandoverRecordRepository;
import org.example.animalsheltersecurity.repository.AnimalRepository;
import org.example.animalsheltersecurity.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptionApplicationService {

    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final AnimalHandoverRecordRepository handoverRecordRepository;

    public List<AdoptionApplication> findAll() {
        return adoptionApplicationRepository.findAll();
    }

    public AdoptionApplication findById(Long id) {
        return adoptionApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption application", id));
    }

    @Transactional
    public AdoptionApplication create(Long animalId, Long applicantId, String status) {
        Animal animal = getAnimal(animalId);
        User applicant = getApplicant(applicantId);

        AdoptionApplication application = AdoptionApplication.builder()
                .animal(animal)
                .applicant(applicant)
                .status(StringUtils.hasText(status) ? status : "PENDING")
                .build();
        return adoptionApplicationRepository.save(application);
    }

    @Transactional
    public AdoptionApplication update(Long id, Long animalId, Long applicantId, String status) {
        AdoptionApplication application = findById(id);

        if (animalId != null) {
            application.setAnimal(getAnimal(animalId));
        }
        if (applicantId != null) {
            application.setApplicant(getApplicant(applicantId));
        }
        if (StringUtils.hasText(status)) {
            application.setStatus(status);
        }
        return adoptionApplicationRepository.save(application);
    }

    /**
     * Deletes the application and all handover records that reference it.
     */
    @Transactional
    public void delete(Long id) {
        findById(id);

        handoverRecordRepository.deleteByApplicationId(id);
        adoptionApplicationRepository.deleteById(id);
    }

    private Animal getAnimal(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal", animalId));
    }

    private User getApplicant(Long applicantId) {
        return userRepository.findById(applicantId)
                .orElseThrow(() -> new ResourceNotFoundException("User", applicantId));
    }
}
