package org.example.animalsheltersecurity.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Animal kept in the shelter.
 */
@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private Integer age;

    /**
     * Placement status, for example AVAILABLE, RESERVED, ADOPTED.
     */
    @Column(nullable = false)
    private String status;
}
