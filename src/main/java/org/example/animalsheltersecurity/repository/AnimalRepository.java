package org.example.animalsheltersecurity.repository;

import org.example.animalsheltersecurity.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
