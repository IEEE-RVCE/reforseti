package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.ieeervce.api.siterearnouveau.dto.article.ArticleDTO;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticlesControllerTest {
    private static final String KEYWORD_SET = "this is a large keyword set";
    @Mock
    ArticleService articleService;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Spy
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @InjectMocks
    ArticlesController articlesController;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(articlesController).build();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testList() throws Exception {

        Integer articleId = 1;
        Integer eventCategory = 45;
        String title = "Article " + 1;
        String author = "Author " + 1;
        String content = "Content " + 1;

        var article = new Article();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setAuthor(author);
        article.setAddedDateTime(LocalDateTime.now());
        article.setEventCategory(eventCategory);
        article.setKeywords(KEYWORD_SET);
        article.setContent(content);

        when(articleService.list()).thenReturn(Collections.singletonList(article));

        mvc.perform(MockMvcRequestBuilders.get("/api/article"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))

                .andExpect(jsonPath("$.response[0].articleId", Matchers.equalTo(articleId)))
                .andExpect(jsonPath("$.response[0].eventCategory", Matchers.equalTo(eventCategory)))
                .andExpect(jsonPath("$.response[0].author", Matchers.equalTo(author)))
                .andExpect(jsonPath("$.response[0].addedDateTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response[0].content", Matchers.equalTo(content)))
                .andExpect(jsonPath("$.response[0].keywords", Matchers.equalTo(KEYWORD_SET)))
                .andExpect(jsonPath("$.response[0].title", Matchers.equalTo(title)));
    }

    @Test
    void testGetArticleById() throws Exception {

        Integer articleId = 1;
        Integer eventCategory = 45;
        String title = "Article " + 1;
        String author = "Author " + 1;
        String content = "Content " + 1;

        Article article = new Article();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setAuthor(author);
        article.setAddedDateTime(LocalDateTime.now());
        article.setEventCategory(eventCategory);
        article.setKeywords(KEYWORD_SET);
        article.setContent(content);
        when(articleService.getArticleById(1)).thenReturn(article);

        mvc.perform(MockMvcRequestBuilders.get("/api/article/{articleId}",articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.notNullValue()))

                .andExpect(jsonPath("$.response.articleId", Matchers.equalTo(articleId)))
                .andExpect(jsonPath("$.response.eventCategory", Matchers.equalTo(eventCategory)))
                .andExpect(jsonPath("$.response.author", Matchers.equalTo(author)))
                .andExpect(jsonPath("$.response.addedDateTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response.content", Matchers.equalTo(content)))
                .andExpect(jsonPath("$.response.keywords", Matchers.equalTo(KEYWORD_SET)))
                .andExpect(jsonPath("$.response.title", Matchers.equalTo(title)));
    }

    @Test
    void testCreateArticle() throws Exception {
        Integer articleId = 1;
        Integer eventCategory = 45;
        String title = "Article " + 1;
        String author = "Author " + 1;
        String content = "Content " + 1;

        ArticleDTO article = new ArticleDTO();

        article.setTitle(title);
        article.setAuthor(author);
        article.setEventCategory(eventCategory);
        article.setKeywords(KEYWORD_SET);
        article.setContent(content);

        Article articleSaved = modelMapper.map(article, Article.class);
        articleSaved.setArticleId(articleId);
        articleSaved.setAddedDateTime(LocalDateTime.now());

        when(articleService.saveArticle(any())).thenReturn(articleSaved);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article).getBytes(Charset.defaultCharset())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.notNullValue()))

                .andExpect(jsonPath("$.response.articleId", Matchers.equalTo(articleId)))
                .andExpect(jsonPath("$.response.eventCategory", Matchers.equalTo(eventCategory)))
                .andExpect(jsonPath("$.response.author", Matchers.equalTo(author)))
                .andExpect(jsonPath("$.response.addedDateTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response.content", Matchers.equalTo(content)))
                .andExpect(jsonPath("$.response.keywords", Matchers.equalTo(KEYWORD_SET)))
                .andExpect(jsonPath("$.response.title", Matchers.equalTo(title)));
    }

    @Test
    void testDeleteArticle() throws Exception {
        Integer articleId = 1;

        when(articleService.deleteArticle(articleId)).thenReturn(true);

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/article/{articleId}",articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.equalTo(true)));
    }

    @Test
    void testEditArticle() throws Exception {
        Integer articleId = 1;
        Integer eventCategory = 45;
        String title = "Article " + 1;
        String author = "Author " + 1;
        String content = "Content " + 1;

        ArticleDTO article = new ArticleDTO();

        article.setTitle(title);
        article.setAuthor(author);
        article.setEventCategory(eventCategory);
        article.setKeywords(KEYWORD_SET);
        article.setContent(content);

        Article articleSaved = modelMapper.map(article, Article.class);
        articleSaved.setArticleId(articleId);
        articleSaved.setAddedDateTime(LocalDateTime.now());

        when(articleService.editArticle(eq(articleId),any())).thenReturn(Optional.of(articleSaved));

        mvc.perform(
                        put("/api/article/{articleId}",articleId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(article).getBytes(Charset.defaultCharset())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.notNullValue()))

                .andExpect(jsonPath("$.response.articleId", Matchers.equalTo(articleId)))
                .andExpect(jsonPath("$.response.eventCategory", Matchers.equalTo(eventCategory)))
                .andExpect(jsonPath("$.response.author", Matchers.equalTo(author)))
                .andExpect(jsonPath("$.response.addedDateTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response.content", Matchers.equalTo(content)))
                .andExpect(jsonPath("$.response.keywords", Matchers.equalTo(KEYWORD_SET)))
                .andExpect(jsonPath("$.response.title", Matchers.equalTo(title)));
    }

    @Test
    void testEditArticleReturnsFalseOnNoArticle() throws Exception {
        Integer articleId = 1;
        Integer eventCategory = 45;
        String title = "Article " + 1;
        String author = "Author " + 1;
        String content = "Content " + 1;

        ArticleDTO article = new ArticleDTO();

        article.setTitle(title);
        article.setAuthor(author);
        article.setEventCategory(eventCategory);
        article.setKeywords(KEYWORD_SET);
        article.setContent(content);

        Article articleSaved = modelMapper.map(article, Article.class);
        articleSaved.setArticleId(articleId);
        articleSaved.setAddedDateTime(LocalDateTime.now());

        when(articleService.editArticle(eq(articleId),any())).thenReturn(Optional.empty());

        mvc.perform(
                        put("/api/article/{articleId}",articleId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(article).getBytes(Charset.defaultCharset())))
                .andExpect(status().isNotFound());
    }

}
