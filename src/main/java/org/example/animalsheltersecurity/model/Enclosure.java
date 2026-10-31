package org.example.animalsheltersecurity.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Enclosure that can host animals of the allowed species.
 */
@Entity
@Table(name = "enclosures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "enclosure_allowed_species",
            joinColumns = @JoinColumn(name = "enclosure_id")
    )
    @Column(name = "species", nullable = false)
    private Set<String> allowedSpecies;

    @Column(nullable = false)
    private Integer capacity;
}
