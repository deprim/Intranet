package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.News;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.NewsService;
import com.dandek.Intranet.Intranet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;

    @Autowired
    public NewsController(NewsService newsService,
                          UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
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

    @PostMapping("/create")
    public String createNews(@ModelAttribute News news,
                             Principal principal) {



        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        newsService.createNews(news, user);

        return "redirect:/news";

    }



}
