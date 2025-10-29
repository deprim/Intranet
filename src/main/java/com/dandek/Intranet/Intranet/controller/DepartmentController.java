package com.dandek.Intranet.Intranet.controller;

import com.dandek.Intranet.Intranet.model.Department;
import com.dandek.Intranet.Intranet.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public String getDepartmentPage(Model model) {

        model.addAttribute("newDepartment", new Department());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("userCounts", departmentService.userCountByDepartment());



        return "createDepartment";
    }

    @PostMapping("/create")
    public String createDepartment(@ModelAttribute Department department){

        departmentService.createDepartment(department);


        return "redirect:/department";

    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes){
        String message;
        String alertClass;

        try {
            departmentService.deleteDepartment(id);
            message = "Department successfully deleted.";
            alertClass = "alert-success";

        } catch (DataIntegrityViolationException e) {
            message = "Can't delete department, if there are users in it";
            alertClass = "alert-danger";

        } catch (InvalidDataAccessApiUsageException e) {
            message = "Can't delete department, because you are part of it";
            alertClass = "alert-danger";

        } catch (Exception e) {
            message = "An unexpected error occurred during deletion.";
            alertClass = "alert-danger";
        }

        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("alertClass", alertClass);
        return "redirect:/department";


    }


}
