package com.mihirjoshi.productinventory.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Low stock thresholds based on product type
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "product_thresholds")
public class ProductThreshold {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productType;
    private int lowStockThreshold;
}