package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="images")
@Data
public class Image {
    @Column(name="iid")
    @Id
    private int imageId;

    @Column(name="image")
    private byte[] imageBytes;

    @Column(name="ecat")
    private int eventCategory;

    @Column(name="alt")
    private String altText;
    
}
