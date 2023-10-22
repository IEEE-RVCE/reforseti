package org.ieeervce.api.siterearnouveau.service;

import java.util.List;

import org.ieeervce.api.siterearnouveau.entity.Article;
import org.ieeervce.api.siterearnouveau.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
    @Autowired
    ArticlesRepository articlesRepository;


    public List<Article> list(){
        return articlesRepository.findAll();
    }
}
