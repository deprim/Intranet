package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.Department;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.DepartmentService;
import com.dandek.Intranet.Intranet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService,
                              DepartmentService departmentService,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showRegisterPage(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("allDepartments", departmentService.findAll());
        return "register";
    }

    @PostMapping()
    public String register(@Valid @ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/register";
        } else {
            String password = userForm.getPassword();
            userForm.setPassword(passwordEncoder.encode(password));
            userService.registerUser(userForm);
        }
        return "redirect:/home";

    }


}
