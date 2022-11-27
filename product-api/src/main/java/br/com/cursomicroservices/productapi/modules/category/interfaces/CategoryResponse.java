package br.com.cursomicroservices.productapi.modules.category.interfaces;

import br.com.cursomicroservices.productapi.modules.category.models.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryResponse {

  private Integer id;
  private String description;

  public static CategoryResponse of(Category category) {
    var response = new CategoryResponse();
    BeanUtils.copyProperties(category, response);
    return response;
  }
}
