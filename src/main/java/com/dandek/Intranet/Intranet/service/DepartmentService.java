package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.Department;
import com.dandek.Intranet.Intranet.model.User;
import com.dandek.Intranet.Intranet.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserService userService;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             UserService userService) {
        this.departmentRepository = departmentRepository;
        this.userService = userService;
    }



    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Long getDepartmentsCount() {
        return departmentRepository.count();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Map<Long, Long> userCountByDepartment(){


        List<User> users = userService.findAll();
        return users.stream()
                .collect(Collectors.groupingBy(user -> user.getDepartment().getId(), Collectors.counting()));


    }

    @Transactional
    public void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) throws DataIntegrityViolationException, InvalidDataAccessApiUsageException{


            departmentRepository.deleteById(id);



    }






}
