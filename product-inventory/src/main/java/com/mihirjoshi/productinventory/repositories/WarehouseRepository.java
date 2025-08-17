package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByCompanyId(Long companyId);
}