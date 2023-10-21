package org.ieeervce.api.siterearnouveau.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="articles")
@Data
public class Article {
    @Id
    @Column(name = "arid")
    Integer articleId;
    
    @Column(name="ecat")
    private Integer eventCategory;

    @Column
    private String author;

    @Column(name="adate")
    private LocalDateTime localDateTime;

    @Column
    String content;

    @Column
    String keywords;

    @Column
    String title;
}
