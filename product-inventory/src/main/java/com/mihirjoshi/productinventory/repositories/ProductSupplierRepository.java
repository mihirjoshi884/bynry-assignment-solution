package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
    Optional<ProductSupplier> findByProductId(Long productId);
}