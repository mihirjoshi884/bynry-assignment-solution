package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}