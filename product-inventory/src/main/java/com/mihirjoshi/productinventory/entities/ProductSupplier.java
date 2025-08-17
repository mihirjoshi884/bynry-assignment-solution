package com.mihirjoshi.productinventory.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Junction table for products and suppliers
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "product_suppliers")
public class ProductSupplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long supplierId;
}
