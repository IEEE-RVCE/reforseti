package org.ieeervce.api.siterearnouveau.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.repository.ArticlesRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    static final String FAILED_TO_DELETE_ARTICLE = "Failed to delete article";
    private final ArticlesRepository articlesRepository;

    public ArticleService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public List<Article> list() {
        return articlesRepository.findAll();
    }

    public Article getArticleById(Integer id) {
        return articlesRepository.findById(id).orElse(null);
    }

    public Article saveArticle(Article article) {
        return articlesRepository.save(article);
    }

    public boolean deleteArticle(Integer id) {
        try {
            articlesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(FAILED_TO_DELETE_ARTICLE, e);
        }
    }
    @Transactional
    public Optional<Article> editArticle(int articleId, Article article) {
        if(articlesRepository.existsById(articleId)) {
            article.setArticleId(articleId);
            return Optional.of(articlesRepository.save(article));
        }
        return Optional.empty();
    }
}
