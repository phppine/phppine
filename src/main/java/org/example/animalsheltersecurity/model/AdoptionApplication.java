package org.example.animalsheltersecurity.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Application of a user to adopt a concrete animal.
 */
@Entity
@Table(name = "adoption_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    /**
     * Application status, for example PENDING, APPROVED, REJECTED.
     */
    @Column(nullable = false)
    private String status;
}
