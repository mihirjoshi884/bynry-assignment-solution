package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}