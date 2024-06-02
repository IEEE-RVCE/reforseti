package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Image {
    @Column(name = "iid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer imageId;

    @Column(name = "image", nullable = false)
    private byte[] imageBytes;

    @Column(name = "ecat")
    private int eventCategory;

    @Column(name = "alt")
    private String altText;

}
