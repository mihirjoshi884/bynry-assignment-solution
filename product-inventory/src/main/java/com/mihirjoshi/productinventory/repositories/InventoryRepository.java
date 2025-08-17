package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}
