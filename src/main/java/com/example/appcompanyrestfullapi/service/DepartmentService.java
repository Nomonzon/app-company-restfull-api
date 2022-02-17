package com.example.appcompanyrestfullapi.service;

import com.example.appcompanyrestfullapi.entity.Company;
import com.example.appcompanyrestfullapi.entity.Department;
import com.example.appcompanyrestfullapi.payload.DepartmentDto;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.repository.CompanyRepository;
import com.example.appcompanyrestfullapi.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<Department> getAllDepartment(){
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    public Department getDepartmentById(Long id){
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.orElse(null);
    }

    public Message addDepartment(DepartmentDto departmentDto){
        boolean nameAndCompanyId = departmentRepository
                .existsByNameAndCompany_Id(departmentDto.getDepartmentName(), departmentDto.getCompanyId());
        if (nameAndCompanyId){
            return new Message("Error department is already exists", false);
        }
        Optional<Company> companyOptional = companyRepository.findById(departmentDto.getCompanyId());
        if (companyOptional.isEmpty()){
            return new Message("Error company id is not found", false);
        }
        Department department = new Department(
                null,
                departmentDto.getDepartmentName(),
                companyOptional.get()
        );
        departmentRepository.save(department);
        return new Message("Success department added", true);
    }

    public Message editDepartment(DepartmentDto departmentDto, Long id){
        boolean exists = departmentRepository.existsByNameAndCompany_IdAndIdNot(
                departmentDto.getDepartmentName(),
                departmentDto.getCompanyId(),
                id
        );
        if (exists){
            return new Message("Department is already exists", false);
        }
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()){
            return new Message("Company is not found", false);
        }
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()){
            return new Message("Department is not found", false);
        }
        Department department = departmentOptional.get();
        department.setName(departmentDto.getDepartmentName());
        department.setCompany(companyOptional.get());
        departmentRepository.save(department);
        return new Message("Success drpartment is edited", true);
    }

    public Message deleteDepartment(Long id){
        try {
            departmentRepository.deleteById(id);
            return new Message("Success deleted", true);
        }
        catch (Exception e){
            return new Message("Error department is not deleted", true);
        }
    }
}
