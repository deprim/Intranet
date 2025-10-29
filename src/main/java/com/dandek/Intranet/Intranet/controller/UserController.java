package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.Department;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.service.DepartmentService;
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
    public final DepartmentService departmentService;

    @Autowired
    public UserController(UserService userService,
                          DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }


    @GetMapping()
    public String showUserPage(Model model,
                               @RequestParam(defaultValue = "0", required = true) int page,
                               @RequestParam(defaultValue = "20", required = false) int itemsPerPage,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) Long departmentId, // ⬅️ ПРИНИМАЕМ Integer ID
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(defaultValue = "false") boolean showInactive) {

        // Передаем departmentId в сервис
        Page<User> userPage = userService.findFilteredAndPaginated(page, itemsPerPage, search, departmentId, sortBy, showInactive);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("activeUsers", userService.findActiveUsers());
        model.addAttribute("inactiveUsers", userService.findNotActiveUsers());
        model.addAttribute("departmentCount", departmentService.getDepartmentsCount());
        model.addAttribute("sortBy", userPage.getSort());
        model.addAttribute("allDepartments", departmentService.findAll());
        model.addAttribute("showInactive", showInactive);

        // Также нужно добавить в модель departmentId для сохранения выбранного фильтра в шаблоне
        model.addAttribute("selectedDepartmentId", departmentId);

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
