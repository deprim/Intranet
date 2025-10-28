package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalAttributesController {

    private final UserService userService;

    @Autowired
    public GlobalAttributesController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("isOOO")
    public boolean isUserOutOfOffice(Principal principal) {

        boolean isOOO;

        if (principal == null) {
            isOOO = false;
            return isOOO;
        } else {

            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            isOOO = user.isOutOfOffice();
            return isOOO;

        }

    }

}
