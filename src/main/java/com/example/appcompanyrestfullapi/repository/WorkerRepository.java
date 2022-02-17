package com.example.appcompanyrestfullapi.repository;

import com.example.appcompanyrestfullapi.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    boolean existsByFullNameAndDepartment_id(String fullName, Long department_id);

    boolean existsByFullNameAndDepartment_IdAndIdNot(String fullName, Long department_id, Long id);
}
