package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.Events;
import com.dandek.Intranet.Intranet.service.EventService;
import com.dandek.Intranet.Intranet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateEventPage(Model model,
                                      @RequestParam(defaultValue = "0", required = true) int page,
                                      @RequestParam(defaultValue = "20", required = false) int itemsPerPage){
        model.addAttribute("newEvent", new Events());
        model.addAttribute("events", eventService.getAllEventsPageable(page, itemsPerPage));
        return "event";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute @Valid Events event){
        eventService.save(event);
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id){
        eventService.deleteEvent(id);
        return "redirect:/event/create";
    }


}
