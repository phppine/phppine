package org.example.animalsheltersecurity.repository;

import org.example.animalsheltersecurity.model.AnimalHandoverRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimalHandoverRecordRepository extends JpaRepository<AnimalHandoverRecord, Long> {

    @Modifying(flushAutomatically = true)
    @Query("delete from AnimalHandoverRecord r where r.application.id = :applicationId")
    void deleteByApplicationId(@Param("applicationId") Long applicationId);

    @Modifying(flushAutomatically = true)
    @Query("delete from AnimalHandoverRecord r where r.application.animal.id = :animalId")
    void deleteByApplicationAnimalId(@Param("animalId") Long animalId);

    @Modifying(flushAutomatically = true)
    @Query("delete from AnimalHandoverRecord r where r.application.applicant.id = :applicantId")
    void deleteByApplicationApplicantId(@Param("applicantId") Long applicantId);
}
