package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.ProductThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductThresholdRepository extends JpaRepository<ProductThreshold, Long> {}