package org.ieeervce.api.siterearnouveau.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Mock
    Article article1;
    @Mock 
    Article article2;
    
    @Spy
    List<Article> articles = Arrays.asList(article1,article2);

    @Test
    void testListAll(){
        when(articlesRepository.findAll()).thenReturn(articles);
        List<Article> articlesReturned = articleService.list();
        assertSame(articles,articlesReturned);
    }
    @Test
    void testGetArticleById(){
        when(articlesRepository.findById(ARTICLE_ID)).thenReturn(Optional.of(article1));
        Article foundArticle = articleService.getArticleById(ARTICLE_ID);
        assertSame(article1,foundArticle);
    }
    @Test
    void testGetArticleByIdWhenArticleDoesNotExist(){
        when(articlesRepository.findById(ARTICLE_ID)).thenReturn(Optional.empty());
        Article foundArticle = articleService.getArticleById(ARTICLE_ID);
        assertNull(foundArticle);
    }

    

}
