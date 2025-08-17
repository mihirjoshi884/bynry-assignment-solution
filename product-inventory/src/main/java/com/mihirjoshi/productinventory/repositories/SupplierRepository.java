package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {}
