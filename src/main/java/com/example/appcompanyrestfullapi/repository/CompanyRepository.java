package com.example.appcompanyrestfullapi.repository;

import com.example.appcompanyrestfullapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    boolean existsCompanyByCorpName(String corpName);

    boolean existsCompanyByCorpNameAndIdNot(String corpName, Long id);
}
