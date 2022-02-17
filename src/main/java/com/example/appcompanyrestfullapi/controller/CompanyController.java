package com.example.appcompanyrestfullapi.controller;

import com.example.appcompanyrestfullapi.payload.CompanyDto;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.service.CompanyServiceImpl;
import com.example.appcompanyrestfullapi.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;

    /**
     * THIS METHOD FOR GET ALL COMPANIES FROM DATABASE AND SEND TO CLIENT
     * @return List<Company>
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> allCompany = companyService.getAllCompany();
        return allCompany != null && !allCompany.isEmpty()
                ? new ResponseEntity<>(allCompany, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * THIS METHOD , TO GET COMPANY BY ID FROM DATABASE, AND SEND IT TO CLIENT
     * @param id
     * @return ResponseEntity<Company>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company companyById = companyService.getCompanyById(id);
        return companyById != null
                ? new ResponseEntity<>(companyById,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * THIS METHOD, TO ADDING COMPANY TO DATABASE
     * @param companyDto
     * @return ResponseEntity<Message>
     *     IF MESSAGE IS SUCCESS METHOD RETURN "OBJECT(MESSAGE, TRUE)"
     */
    @PostMapping
    public ResponseEntity<Message> addCompany(@RequestBody CompanyDto companyDto){
        Message message = companyService.addCompany(companyDto);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * THIS METHOD, TO EDIT COMPANY
     * @param companyDto
     * @return ResponseEntity<Message>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCompany(@RequestBody CompanyDto companyDto, @PathVariable Long id){
        Message message = companyService.editCompany(id, companyDto);
        return message.isSuccess()
                ? new ResponseEntity<>(message,HttpStatus.OK)
                : new ResponseEntity<>(message,HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteCompany(@PathVariable Long id){
        Message message = companyService.deleteCompany(id);
        return message.isSuccess()
                ? new ResponseEntity<>(message, HttpStatus.ACCEPTED)
                : new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
}
