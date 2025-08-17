package com.mihirjoshi.productinventory.entities;
import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// Junction table for products and warehouses (inventory)
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "inventory")
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long warehouseId;
    private int quantity;
}