package com.mihirjoshi.productinventory;

import com.mihirjoshi.productinventory.entities.*;
import com.mihirjoshi.productinventory.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class ProductInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryApplication.class, args);
    }

    // A command line runner to populate the database with sample data
    @Bean
    public CommandLineRunner demo(CompanyRepository companyRepository,
                                  WarehouseRepository warehouseRepository,
                                  ProductRepository productRepository,
                                  SupplierRepository supplierRepository,
                                  ProductSupplierRepository productSupplierRepository,
                                  InventoryRepository inventoryRepository,
                                  ProductThresholdRepository productThresholdRepository,
                                  OrderRepository orderRepository,
                                  OrderItemRepository orderItemRepository) {
        return (args) -> {
            // Create a company and warehouses
            Company myCompany = companyRepository.save(new Company(null, "MyTech Solutions"));
            Warehouse warehouse1 = warehouseRepository.save(new Warehouse(null, "Main Warehouse", myCompany.getId()));
            Warehouse warehouse2 = warehouseRepository.save(new Warehouse(null, "West Coast Hub", myCompany.getId()));

            // Create products with different types
            Product product1 = productRepository.save(new Product(null, "Laptop Pro", "LAP-001", new BigDecimal("1200.00"), "ELECTRONICS", myCompany.getId()));
            Product product2 = productRepository.save(new Product(null, "Keyboard", "KBD-001", new BigDecimal("80.00"), "PERIPHERAL", myCompany.getId()));
            Product product3 = productRepository.save(new Product(null, "Monitor", "MON-002", new BigDecimal("300.00"), "ELECTRONICS", myCompany.getId()));
            Product product4 = productRepository.save(new Product(null, "Mouse", "MSE-001", new BigDecimal("25.00"), "PERIPHERAL", myCompany.getId()));

            // Create suppliers
            Supplier supplier1 = supplierRepository.save(new Supplier(null, "Supplier A", "contact@supplier-a.com"));
            Supplier supplier2 = supplierRepository.save(new Supplier(null, "Supplier B", "sales@supplier-b.com"));

            // Link products to suppliers
            productSupplierRepository.save(new ProductSupplier(null, product1.getId(), supplier1.getId()));
            productSupplierRepository.save(new ProductSupplier(null, product2.getId(), supplier1.getId()));
            productSupplierRepository.save(new ProductSupplier(null, product3.getId(), supplier2.getId()));
            productSupplierRepository.save(new ProductSupplier(null, product4.getId(), supplier1.getId()));

            // Set low stock thresholds for product types
            productThresholdRepository.save(new ProductThreshold(null, "ELECTRONICS", 10)); // Low threshold for high-value items
            productThresholdRepository.save(new ProductThreshold(null, "PERIPHERAL", 20)); // Higher threshold for low-value items

            // Create inventory records for products
            inventoryRepository.saveAll(Arrays.asList(
                    new Inventory(null, product1.getId(), warehouse1.getId(), 5),  // Low stock alert!
                    new Inventory(null, product1.getId(), warehouse2.getId(), 15), // Not an alert
                    new Inventory(null, product2.getId(), warehouse1.getId(), 15), // Low stock alert!
                    new Inventory(null, product3.getId(), warehouse1.getId(), 25), // Not an alert
                    new Inventory(null, product4.getId(), warehouse1.getId(), 50)  // Not an alert
            ));

            // Create recent sales activity for product1 and product2
            Order recentOrder = orderRepository.save(new Order(null, myCompany.getId(), LocalDateTime.now()));
            orderItemRepository.save(new OrderItem(null, recentOrder.getId(), product1.getId(), 1)); // Sold recently
            orderItemRepository.save(new OrderItem(null, recentOrder.getId(), product2.getId(), 2)); // Sold recently
        };
    }
}
