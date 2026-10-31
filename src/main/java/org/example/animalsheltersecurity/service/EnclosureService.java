package org.example.animalsheltersecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.animalsheltersecurity.exception.ResourceNotFoundException;
import org.example.animalsheltersecurity.model.Enclosure;
import org.example.animalsheltersecurity.repository.EnclosureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;

    public List<Enclosure> findAll() {
        return enclosureRepository.findAll();
    }

    public Enclosure findById(Long id) {
        return enclosureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enclosure", id));
    }

    @Transactional
    public Enclosure create(Enclosure enclosure) {
        enclosure.setId(null);
        if (enclosure.getAllowedSpecies() == null || enclosure.getAllowedSpecies().isEmpty()) {
            throw new IllegalArgumentException("allowedSpecies must contain at least one species");
        }
        enclosure.setAllowedSpecies(new LinkedHashSet<>(enclosure.getAllowedSpecies()));
        return enclosureRepository.save(enclosure);
    }

    @Transactional
    public Enclosure update(Long id, Enclosure changes) {
        Enclosure enclosure = findById(id);

        if (changes.getAllowedSpecies() != null) {
            if (changes.getAllowedSpecies().isEmpty()) {
                throw new IllegalArgumentException("allowedSpecies must contain at least one species");
            }
            enclosure.setAllowedSpecies(new LinkedHashSet<>(changes.getAllowedSpecies()));
        }
        if (changes.getCapacity() != null) {
            enclosure.setCapacity(changes.getCapacity());
        }
        return enclosureRepository.save(enclosure);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        enclosureRepository.deleteById(id);
    }
}
