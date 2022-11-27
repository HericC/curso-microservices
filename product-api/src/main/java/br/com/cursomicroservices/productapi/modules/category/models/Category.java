package br.com.cursomicroservices.productapi.modules.category.models;

import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = 'category')
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(name = 'description', nullable = false)
  private String description;

  public static Category of(CategoryRequest request) {
    var category = new Category();
    BeanUtils.copyProperties(request, category);
    return category;
  }
}
