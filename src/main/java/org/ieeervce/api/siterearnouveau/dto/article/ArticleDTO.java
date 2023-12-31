package org.ieeervce.api.siterearnouveau.dto.article;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDTO {
    Integer eventCategory;
    String content;
    String keywords;
    String title;
    String author;
}
