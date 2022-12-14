package br.com.cursomicroservices.productapi.modules.supplier.models;

import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryRequest;
import br.com.cursomicroservices.productapi.modules.category.models.Category;
import br.com.cursomicroservices.productapi.modules.supplier.interfaces.SupplierRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = 'supplier')
public class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = 'name', nullable = false)
  private String name;

  public static Supplier of(SupplierRequest request) {
    var supplier = new Supplier();
    BeanUtils.copyProperties(request, supplier);
    return supplier;
  }
}
