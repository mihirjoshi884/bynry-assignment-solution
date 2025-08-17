package com.mihirjoshi.productinventory.entities;
import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Represents a product item within an order
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
}