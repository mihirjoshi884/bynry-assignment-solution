package com.mihirjoshi.productinventory.services;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mihirjoshi.productinventory.dtos.AlertDto;
import com.mihirjoshi.productinventory.dtos.SupplierDto;
import com.mihirjoshi.productinventory.entities.*;
import com.mihirjoshi.productinventory.repositories.*;
import org.springframework.stereotype.Service;



@Service
public class AlertService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductThresholdRepository productThresholdRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final OrderItemRepository orderItemRepository;

    // Dependency Injection
    public AlertService(ProductRepository productRepository, InventoryRepository inventoryRepository, ProductThresholdRepository productThresholdRepository, WarehouseRepository warehouseRepository, SupplierRepository supplierRepository, ProductSupplierRepository productSupplierRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.productThresholdRepository = productThresholdRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.productSupplierRepository = productSupplierRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<AlertDto> getLowStockAlerts(Long companyId) {
        // 1. Get IDs of products with recent sales activity for the company (last 30 days)
        LocalDateTime recentDate = LocalDateTime.now().minusDays(30);
        Set<Long> productIdsWithRecentSales = orderItemRepository.findProductsWithRecentSales(recentDate, companyId);
        if (productIdsWithRecentSales.isEmpty()) {
            return List.of(); // No recent sales, no alerts
        }

        // 2. Fetch all products and warehouses for the given company
        List<Product> companyProducts = productRepository.findByCompanyId(companyId);
        List<Warehouse> companyWarehouses = warehouseRepository.findByCompanyId(companyId);

        // 3. Create a map for quick lookups of thresholds and warehouses

        var thresholds = productThresholdRepository.findAll().stream()
                .collect(Collectors.toMap(ProductThreshold::getProductType, ProductThreshold::getLowStockThreshold));
        var warehouseMap = companyWarehouses.stream()
                .collect(Collectors.toMap(Warehouse::getId, Warehouse::getName));

        // 4. Find all inventory items that match products with recent sales and are in the company's warehouses
        List<Inventory> allInventory = inventoryRepository.findAll();
        List<Inventory> relevantInventory = allInventory.stream()
                .filter(inv -> productIdsWithRecentSales.contains(inv.getProductId()) && warehouseMap.containsKey(inv.getWarehouseId()))
                .toList();

        // 5. Build the list of alerts
        return relevantInventory.stream()
                .map(inv -> {
                    // Find the product and threshold for the current inventory item
                    Product product = productRepository.findById(inv.getProductId()).orElse(null);
                    if (product == null) return null; // Skip if product not found

                    int threshold = (int) thresholds.getOrDefault(product.getProductType(), 10); // Default to 10 if no threshold is set

                    // Check if stock is below the threshold
                    if (inv.getQuantity() < threshold) {
                        // Find the supplier for reordering
                        Optional<Supplier> supplier = productSupplierRepository.findByProductId(product.getId())
                                .flatMap(ps -> supplierRepository.findById(ps.getSupplierId()));

                        // Calculate days until stockout (simple approximation)
                        int daysUntilStockout = (int) Math.round((double) inv.getQuantity() / 1.5); // Assumption: average daily sales is 1.5 units

                        // Build the AlertDto
                        return new AlertDto(
                                product.getId(),
                                product.getName(),
                                product.getSku(),
                                inv.getWarehouseId(),
                                warehouseMap.get(inv.getWarehouseId()),
                                inv.getQuantity(),
                                threshold,
                                daysUntilStockout,
                                supplier.map(s -> new SupplierDto(s.getId(), s.getName(), s.getContactEmail())).orElse(null)
                        );
                    }
                    return null; // Not an alert
                })
                .filter(java.util.Objects::nonNull) // Remove nulls from the stream
                .collect(Collectors.toList());
    }
}

