package com.mihirjoshi.productinventory.repositories;

import com.mihirjoshi.productinventory.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.productId FROM OrderItem oi JOIN Order o ON oi.orderId = o.id WHERE o.orderDate >= ?1 AND o.companyId = ?2 GROUP BY oi.productId")
    Set<Long> findProductsWithRecentSales(LocalDateTime recentDate, Long companyId);
}
