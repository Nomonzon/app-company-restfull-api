package com.example.appcompanyrestfullapi.repository;

import com.example.appcompanyrestfullapi.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByNameAndCompany_Id(String name, Long company_id);
    boolean existsByNameAndCompany_IdAndIdNot(String name, Long company_id, Long id);
}
