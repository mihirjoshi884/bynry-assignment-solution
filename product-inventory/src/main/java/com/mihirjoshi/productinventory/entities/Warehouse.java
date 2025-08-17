package com.mihirjoshi.productinventory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Represents a warehouse
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Warehouse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long companyId;
}

