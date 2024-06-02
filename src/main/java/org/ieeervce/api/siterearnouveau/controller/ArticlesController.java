package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;
import java.util.Optional;

import io.micrometer.core.annotation.Timed;
import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.article.ArticleDTO;
import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
@Timed
public class ArticlesController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticlesController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

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
    ResultsDTO<Article> saveArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);
        Article result = articleService.saveArticle(article);
        return new ResultsDTO<>(result);
    }

    @PutMapping("/{articleId}")
    ResultsDTO<Article> edit(@PathVariable int articleId,@RequestBody ArticleDTO articleDTO) throws DataNotFoundException {
        Article article = modelMapper.map(articleDTO, Article.class);
        Optional<Article> updatedArticle = articleService.editArticle(articleId,article);
        return updatedArticle.map(ResultsDTO::new).orElseThrow(DataNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    ResultsDTO<Void> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return new ResultsDTO<>(null);
    }

}
