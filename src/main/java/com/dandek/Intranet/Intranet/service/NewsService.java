package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.News;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.repository.NewsRepository;
import com.dandek.Intranet.Intranet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;


    @Autowired
    public NewsService(NewsRepository newsRepository,
                       UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News findById(Long id) {

        // TODO: It shouldn't return null!!!
        return newsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createNews(News news,
                           User user) {
        news.setCreatedAt(LocalDateTime.now());
        news.setUpdatedAt(LocalDateTime.now());
        news.setPublishedDate(LocalDate.now());

        int preview = Math.min(news.getContent().length(), 500);

        news.setPreview(news.getContent().substring(0, preview));
        news.setAuthor(user);
        newsRepository.save(news);

    }



}
