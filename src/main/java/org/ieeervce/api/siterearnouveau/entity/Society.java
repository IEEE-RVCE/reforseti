package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * A society/affinity.
 */
@Entity
@Table(name = "societies")
@Data
public class Society {
    /**
     * An autogenerated id
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer societyGeneratedId;
    @Column(name = "socname", unique = true, nullable = false)
    private String societyName;
    @Column(name = "vision", nullable = false)
    private String vision;
    @Column(name = "mission", nullable = false)
    private String mission;
    @Column(name = "description")
    private String descriptionText;
    @Column(name = "sid", unique = true, nullable = false)
    private short actualSocietyId;
    @Column(name = "is_affinity", nullable = false)
    private boolean affinity = false;

}
