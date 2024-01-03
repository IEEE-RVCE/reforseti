package org.ieeervce.api.siterearnouveau.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="articles")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arid")
    private Integer articleId;
    
    @Column(name="ecat")
    private Integer eventCategory;

    @Column
    private String author;

    @Column(name="adate")
    private LocalDateTime addedDateTime;

    @Column
    private String content;

    @Column
    private String keywords;

    @Column
    private String title;
}
