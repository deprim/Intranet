package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class UserController {


    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String showUserPage(Model model,
                               @RequestParam(defaultValue = "0", required = true) int page,
                               @RequestParam(defaultValue = "20", required = false) int itemsPerPage,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String department,
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(defaultValue = "false") boolean showInactive) {

        Page<User> userPage = userService.findFilteredAndPaginated(page, itemsPerPage, search, department, sortBy, showInactive);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("activeUsers", userService.findActiveUsers());
        model.addAttribute("inactiveUsers", userService.findNotActiveUsers());
        model.addAttribute("departmentCount", userService.departmentCount());
        model.addAttribute("sortBy", userPage.getSort());
        model.addAttribute("allDepartments", userService.getDepartments());
        model.addAttribute("showInactive", showInactive);

        return "users";
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable Long id, Model model) {

        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("yearsAtCompany", userService.timeInCompany(user));

        return "profile";
    }

}
