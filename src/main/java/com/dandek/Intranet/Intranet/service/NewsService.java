package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.News;
import com.dandek.Intranet.Intranet.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;


    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News findById(Long id) {

        // TODO: It shouldn't return null!!!
        return newsRepository.findById(id).orElse(null);
    }

}
