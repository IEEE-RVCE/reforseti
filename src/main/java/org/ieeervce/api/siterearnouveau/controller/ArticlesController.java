package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticlesController {
    @Autowired
    ArticleService articleService;

    @GetMapping()
    ResultsDTO<List<Article>> list() {
        var articles = articleService.list();
        return new ResultsDTO<>(articles);
    }

    @GetMapping("/{id}")
    ResultsDTO<Article> getArticleById(@PathVariable("id") Integer id) {
        var article = articleService.getArticleById(id);
        return new ResultsDTO<>(article);
    }

    @PostMapping()
    ResultsDTO<Boolean> saveArticle(@RequestBody Article article) {
        var result = articleService.saveArticle(article);
        return new ResultsDTO<>(result);
    }

    @DeleteMapping("/{id}")
    ResultsDTO<Boolean> deleteArticle(@PathVariable("id") Integer id) {
        var result = articleService.deleteArticle(id);
        return new ResultsDTO<>(result);
    }

}
