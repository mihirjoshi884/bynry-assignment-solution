package com.mihirjoshi.productinventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Represents a single low-stock alert.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto {
    private Long productId;
    private String productName;
    private String sku;
    private Long warehouseId;
    private String warehouseName;
    private int currentStock;
    private int threshold;
    private int daysUntilStockout;
    private SupplierDto supplier;
}