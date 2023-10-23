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

    public Boolean saveArticle(Article article) {
        try {
            articlesRepository.save(article);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error while saving article", e);
        }
    }

    public Boolean deleteArticle(Integer id) {
        try {
            articlesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete article", e);
        }
    }

}
