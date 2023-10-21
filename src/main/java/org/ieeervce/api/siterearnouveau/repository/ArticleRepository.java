package org.ieeervce.api.siterearnouveau.repository;

import java.util.stream.Stream;

import org.ieeervce.api.siterearnouveau.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Stream<Article> findByEventCategory(Integer eventCategory);
}
