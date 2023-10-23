package org.ieeervce.api.siterearnouveau.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ArticlesControllerTest {
    private static final String KEYWORD_SET = "this is a large keyword set";
    @InjectMocks
    ArticlesController articlesController;
    @Mock
    ArticleService articleService;

    static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(articlesController).build();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testList() throws Exception {
        var article = new Article();
        article.setArticleId(1);
        article.setTitle("Article " + 1);
        article.setAuthor("Author " + 1);
        article.setAddedDateTime(LocalDateTime.now());
        article.setEventCategory(45);
        article.setKeywords(KEYWORD_SET);
        article.setContent("Content " + 1);

        when(articleService.list()).thenReturn(Collections.singletonList(article));

        mvc.perform(MockMvcRequestBuilders.get("/api/article"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.response", Matchers.iterableWithSize(1)))

                .andExpect(jsonPath("$.response[0].articleId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.response[0].eventCategory", Matchers.equalTo(45)))
                .andExpect(jsonPath("$.response[0].author", Matchers.equalTo("Author 1")))
                .andExpect(jsonPath("$.response[0].addedDateTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response[0].content", Matchers.equalTo("Content 1")))
                .andExpect(jsonPath("$.response[0].keywords", Matchers.equalTo(KEYWORD_SET)))
                .andExpect(jsonPath("$.response[0].title", Matchers.equalTo("Article 1")));
    }

    @Test
    void testGetArticleById() throws Exception {

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
        when(articleService.getArticleById(1)).thenReturn(article);

        mvc.perform(MockMvcRequestBuilders.get("/api/article/1"))
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
}
