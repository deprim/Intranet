package com.dandek.Intranet.Intranet.repository;

import com.dandek.Intranet.Intranet.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


}
