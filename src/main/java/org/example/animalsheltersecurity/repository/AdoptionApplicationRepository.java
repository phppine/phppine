package org.example.animalsheltersecurity.repository;

import org.example.animalsheltersecurity.model.AdoptionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {

    @Modifying(flushAutomatically = true)
    @Query("delete from AdoptionApplication a where a.animal.id = :animalId")
    void deleteByAnimalId(@Param("animalId") Long animalId);

    @Modifying(flushAutomatically = true)
    @Query("delete from AdoptionApplication a where a.applicant.id = :applicantId")
    void deleteByApplicantId(@Param("applicantId") Long applicantId);
}
