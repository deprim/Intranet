package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.News;
import com.dandek.Intranet.Intranet.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping()
    public String news(Model model) {
        model.addAttribute("newsList", newsService.findAll());

        return "news";
    }

    @GetMapping("/{id}")
    public String newsById(Model model,
                           @PathVariable("id") Long id) {

        model.addAttribute("news", newsService.findById(id));
        return "showNews";

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("news", new News());
        return "createNews";
    }



}
