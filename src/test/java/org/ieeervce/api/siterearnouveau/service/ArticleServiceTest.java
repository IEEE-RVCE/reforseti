package org.ieeervce.api.siterearnouveau.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.dto.article.ArticleDTO;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.repository.ArticlesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    static final int ARTICLE_ID = 1;
    @Mock
    ArticlesRepository articlesRepository;
    @InjectMocks
    ArticleService articleService;

    @Spy
    Article article1;
    @Spy
    Article article2;

    @Spy
    List<Article> articles = Arrays.asList(article1, article2);

    @Test
    void testListAll() {
        when(articlesRepository.findAll()).thenReturn(articles);
        List<Article> articlesReturned = articleService.list();
        assertSame(articles, articlesReturned);
    }

    @Test
    void testGetArticleById() {
        when(articlesRepository.findById(ARTICLE_ID)).thenReturn(Optional.of(article1));
        Article foundArticle = articleService.getArticleById(ARTICLE_ID);
        assertSame(article1, foundArticle);
    }

    @Test
    void testGetArticleByIdWhenArticleDoesNotExist() {
        when(articlesRepository.findById(ARTICLE_ID)).thenReturn(Optional.empty());
        Article foundArticle = articleService.getArticleById(ARTICLE_ID);
        assertNull(foundArticle);
    }

    @Test
    void testSaveArticle() {
        when(articlesRepository.save(article1)).thenReturn(article2);
        Article returnedArticle = articleService.saveArticle(article1);
        assertSame(article2, returnedArticle);
    }

    @Test
    void testDelete() {
        int id = 1;
        articleService.deleteArticle(id);
        verify(articlesRepository).deleteById(id);
    }

    @Test
    void testDeleteThrowsRuntimeExceptionOnFailure() {
        int id = 1;
        doThrow(new IllegalArgumentException("Something went wrong")).when(articlesRepository).deleteById(id);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            articleService.deleteArticle(id);
        });

        assertTrue(runtimeException.getMessage().contains(ArticleService.FAILED_TO_DELETE_ARTICLE));
        verify(articlesRepository).deleteById(id);
    }
}
