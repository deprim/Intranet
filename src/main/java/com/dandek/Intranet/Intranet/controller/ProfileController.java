package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.MyUserDetails;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath; // where user avatars will be stored

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
                              BindingResult bindingResult,
                              Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Long userId = user.getUserId();
        User oldUser = userService.findById(userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("profileForm", editedUser);
            return "editMyProfile";
        } else {
            userService.editMyProfile(oldUser, editedUser);
            return "redirect:/profile";
        }

    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatarFile") MultipartFile avatar,
                               Principal principal,
                               Model model) throws IOException {

        if (avatar == null || avatar.isEmpty()) {
            model.addAttribute("error", "Avatar is empty");
            return "editMyProfile";
        }

        //create directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique file(avatar) name
        String avatarName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
        Path path = Paths.get(uploadPath, avatarName);
        avatar.transferTo(path);

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("No user found with this username"));
        user.setAvatarUrl("/userAvatars/" + avatarName);
        userService.save(user);

        return "redirect:/profile";

    }

    @PostMapping("/toggle-ooo")
    public String toogleOoo(Principal principal,
                            Model model) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("No user found with this username"));

        userService.toogleOOO(user);

        return "redirect:/home";

    }

}
