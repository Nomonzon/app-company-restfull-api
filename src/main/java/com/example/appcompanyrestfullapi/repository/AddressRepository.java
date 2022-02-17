package com.example.appcompanyrestfullapi.repository;


import com.example.appcompanyrestfullapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Boolean existsByStreetAndHomeNumber(String street, String homeNumber);

    Boolean existsByStreetAndHomeNumberAndIdNot(String street, String homeNumber, Long id);
}
