package com.mihirjoshi.productinventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Represents the supplier information in the API response.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private Long id;
    private String name;
    private String contactEmail;
}