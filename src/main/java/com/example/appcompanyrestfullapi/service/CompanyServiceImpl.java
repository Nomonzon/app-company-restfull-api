package com.example.appcompanyrestfullapi.service;

import com.example.appcompanyrestfullapi.entity.Address;
import com.example.appcompanyrestfullapi.entity.Company;
import com.example.appcompanyrestfullapi.payload.CompanyDto;
import com.example.appcompanyrestfullapi.payload.Message;
import com.example.appcompanyrestfullapi.repository.AddressRepository;
import com.example.appcompanyrestfullapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;


    /**
     * THIS METHOD FOR GET ALL COMPANIES FROM DATABASE AND SEND TO CLIENT
     *
     * @return List<Company>
     */
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }


    /**
     * THIS METHOD , TO GET COMPANY BY ID FROM DATABASE, AND SEND IT TO CLIENT
     *
     * @param id
     * @return ResponseEntity<Company>
     */
    public Company getCompanyById(Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        return byId.orElse(null);
    }


    /**
     * THIS METHOD, TO ADDING COMPANY TO DATABASE
     * @param companyDto
     * @return ResponseEntity<Message>
     *     IF MESSAGE IS SUCCESS METHOD RETURN "OBJECT(MESSAGE, TRUE)"
     */
    public Message addCompany(CompanyDto companyDto) {
        //To check does exist corpName
        boolean corpNameExists = companyRepository.existsCompanyByCorpName(companyDto.getCorpName());
        if (corpNameExists){
            return new Message("Error corpName is alreadt exists", false);
        }

        // To check does exist address
        Boolean addressExists = addressRepository
                .existsByStreetAndHomeNumber(companyDto.getStreet(), companyDto.getHomeNumber());
        if (addressExists){
            return new Message("Error address is already exists", false);
        }

        // Adding address
        Address address = new Address(null,companyDto.getStreet(), companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        // Adding company
        Company company = new Company(null, companyDto.getCorpName(),companyDto.getDirectorName(), savedAddress);
        companyRepository.save(company);
        return new Message("Success company is added", true);
    }

    public Message editCompany(Long id, CompanyDto companyDto){
        boolean corpNameAndIdNot = companyRepository.existsCompanyByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (corpNameAndIdNot){
            return new Message("Error corpName is already exists by another id.", false);
        }

        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()){
            return new Message("Error company is not found", false);
        }


        Company editedCompany = companyOptional.get();
        Address address = editedCompany.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());

        editedCompany.setDirectorName(companyDto.getDirectorName());
        editedCompany.setCorpName(companyDto.getCorpName());
        companyRepository.save(editedCompany);
        return new Message("Success company eddited.", true);
    }

    public Message deleteCompany(Long id){
        try {
            companyRepository.deleteById(id);
            return new Message("Company deleted", true);
        }
        catch (Exception e){
            return new Message("Error comnay is not deleted", false);
        }
    }

}
