package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.service.EventService;
import com.dandek.Intranet.Intranet.service.NewsService;
import com.dandek.Intranet.Intranet.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/home")
public class HomeController {


    private final UserService userService;
    private final NewsService newsService;
    private  final EventService eventService;

    @Autowired
    public HomeController(UserService userService,
                          NewsService newsService,
                          EventService eventService) {
        this.userService = userService;
        this.newsService = newsService;
        this.eventService = eventService;
    }

    @GetMapping()
    public String home(Model model) {
        try {
            model.addAttribute("monthBirthday", userService.birthdayThisMonth());
            model.addAttribute("monthToday", userService.birthdayToday());
            model.addAttribute("month", capitalizeFirstLetter(LocalDate.now().getMonth().toString()));
            model.addAttribute("news", newsService.findAll());
            model.addAttribute("oooUsers", userService.getOutOfOfficeUsers());

            String eventsJson = eventService.getAllEventsInJSON();
            model.addAttribute("eventsJson", eventsJson);
            System.out.println(eventsJson); // Теперь выведет правильный JSON

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("eventsJson", "[]"); // Пустой массив в случае ошибки
        }

        return "home";

    }



    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        str = str.toLowerCase();
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }




}
