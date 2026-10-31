package org.example.animalsheltersecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.exception.ResourceNotFoundException;
import org.example.animalsheltersecurity.model.Animal;
import org.example.animalsheltersecurity.repository.AdoptionApplicationRepository;
import org.example.animalsheltersecurity.repository.AnimalHandoverRecordRepository;
import org.example.animalsheltersecurity.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final AnimalHandoverRecordRepository handoverRecordRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Animal findById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal", id));
    }

    @Transactional
    public Animal create(Animal animal) {
        animal.setId(null);
        if (!StringUtils.hasText(animal.getStatus())) {
            animal.setStatus("AVAILABLE");
        }
        return animalRepository.save(animal);
    }

    @Transactional
    public Animal update(Long id, Animal changes) {
        Animal animal = findById(id);

        if (StringUtils.hasText(changes.getNickname())) {
            animal.setNickname(changes.getNickname());
        }
        if (StringUtils.hasText(changes.getSpecies())) {
            animal.setSpecies(changes.getSpecies());
        }
        if (changes.getAge() != null) {
            animal.setAge(changes.getAge());
        }
        if (StringUtils.hasText(changes.getStatus())) {
            animal.setStatus(changes.getStatus());
        }
        return animalRepository.save(animal);
    }

    /**
     * Deletes the animal together with every application for it and every
     * handover record of those applications, so no dangling references remain.
     */
    @Transactional
    public void delete(Long id) {
        findById(id);

        handoverRecordRepository.deleteByApplicationAnimalId(id);
        adoptionApplicationRepository.deleteByAnimalId(id);
        animalRepository.deleteById(id);
    }
}
