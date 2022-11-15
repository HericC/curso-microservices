package br.com.cursomicroservices.productapi.modules.product.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {

  private String name;
  @JsonPropert("quantity_available")
  private Integer quantityAvailable;
  private Integer supplierId;
  private Integer categoryId;
}
