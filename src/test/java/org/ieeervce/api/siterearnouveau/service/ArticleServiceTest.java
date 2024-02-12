package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.repository.ArticlesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    public static final int EXAMPLE_ARTICLE_ID = 1;
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
        articleService.deleteArticle(EXAMPLE_ARTICLE_ID);
        verify(articlesRepository).deleteById(EXAMPLE_ARTICLE_ID);
    }

    @Test
    void testDeleteThrowsRuntimeExceptionOnFailure() {
        doThrow(new IllegalArgumentException("Something went wrong")).when(articlesRepository).deleteById(EXAMPLE_ARTICLE_ID);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            articleService.deleteArticle(EXAMPLE_ARTICLE_ID);
        });

        assertTrue(runtimeException.getMessage().contains(ArticleService.FAILED_TO_DELETE_ARTICLE));
        verify(articlesRepository).deleteById(EXAMPLE_ARTICLE_ID);
    }

    @Test
    void testUpdate(){
        when(articlesRepository.existsById(EXAMPLE_ARTICLE_ID)).thenReturn(true);
        doReturn(article1).when(articlesRepository).save(article1);

        Optional<Article> articleOptional = articleService.editArticle(EXAMPLE_ARTICLE_ID,article1);

        assertThat(articleOptional).isPresent().contains(article1);

        verify(articlesRepository).save(article1);
        verify(article1).setArticleId(EXAMPLE_ARTICLE_ID);
    }

    @Test
    void testUpdateIfArticleNotFound(){
        when(articlesRepository.existsById(EXAMPLE_ARTICLE_ID)).thenReturn(false);
        Optional<Article> articleOptional = articleService.editArticle(EXAMPLE_ARTICLE_ID,article1);

        assertThat(articleOptional).isEmpty();

        verify(articlesRepository,never()).save(article1);

    }
}
