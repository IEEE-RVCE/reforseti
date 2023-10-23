package org.ieeervce.api.siterearnouveau.service;

import java.util.List;

import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    ArticlesRepository articlesRepository;

    public List<Article> list() {
        return articlesRepository.findAll();
    }

    public Article getArticleById(Integer id) {
        return articlesRepository.findById(id).orElse(null);
    }
}
