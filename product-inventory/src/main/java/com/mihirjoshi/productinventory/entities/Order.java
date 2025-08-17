package com.mihirjoshi.productinventory.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// Represents a customer order
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private LocalDateTime orderDate;
}
