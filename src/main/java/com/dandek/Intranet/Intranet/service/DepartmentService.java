package com.dandek.Intranet.Intranet.service;

import com.dandek.Intranet.Intranet.model.Department;
import com.dandek.Intranet.Intranet.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }



    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Long getDepartmentsCount() {
        return departmentRepository.count();
    }






}
