package com.mihirjoshi.productinventory.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// Represents a product
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private String productType;
    private Long companyId;
}