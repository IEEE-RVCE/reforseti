package org.ieeervce.api.siterearnouveau.dto.article;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDTO {
    private Integer eventCategory;
    private String content;
    private String keywords;
    private String title;
    private String author;
}
