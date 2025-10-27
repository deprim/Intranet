package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.MyUserDetails;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String profile(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Long userId = user.getUserId();
        User currentUser = userService.findById(userId);

        model.addAttribute("currentUser", currentUser);

        return "myprofile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Long userId = user.getUserId();
        User currentUser = userService.findById(userId);

        model.addAttribute("profileForm", currentUser);

        return "editMyProfile";
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("profileForm") @Valid User editedUser,
                              BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Long userId = user.getUserId();
        User oldUser = userService.findById(userId);

        if (bindingResult.hasErrors()) {
            return "editMyProfile";
        } else {
            userService.editMyProfile(oldUser, editedUser);
            return "redirect:/profile";
        }




    }

}
