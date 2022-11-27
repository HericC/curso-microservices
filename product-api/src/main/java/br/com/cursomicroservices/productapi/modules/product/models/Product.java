package br.com.cursomicroservices.productapi.modules.product.models;

import br.com.cursomicroservices.productapi.modules.category.models.Category;
import br.com.cursomicroservices.productapi.modules.product.interfaces.ProductRequest;
import br.com.cursomicroservices.productapi.modules.supplier.models.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = 'product')
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = 'name', nullable = false)  
  private String name;

  @ManyToOne
  @JoinColumn(name = 'fk_category', nullable = false)
  private Category category;

  @ManyToOne
  @JoinColumn(name = 'fk_supplier', nullable = false)
  private Supplier supplier;

  @Column(name = 'quantity_available', nullable = false)
  private Integer quantityAvailable;

  @Column(name = 'created_at', nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }

  public static Product of(ProductRequest request, Category category, Supplier supplier) {
    return Product
      .builder()
      .name(request.getName())
      .quantityAvailable(request.getQuantityAvailable())
      .category(category)
      .supplier(supplier)
      .build();
  }
}
