package br.com.cursomicroservices.productapi.modules.product.controllers;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.modules.product.interfaces.*;
import br.com.cursomicroservices.productapi.modules.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public interface ProductController  {

  @Autowired
  private ProductService productService;

  @PostMapping
  public ProductResponse save(@RequestBody ProductRequest request) {
    return productService.save(request);
  }

  @GetMapping
  public List<ProductResponse> findAll() {
    return productService.findAll();
  }

  @GetMapping("{id}")
  public ProductResponse findById(@PathVariable Integer id) {
    return productService.findByIdResponse(id);
  }

  @GetMapping("name/{name}")
  public List<ProductResponse> findByName(@PathVariable String name) {
    return productService.findByName(name);
  }

  @GetMapping("category/{categoryId}")
  public List<ProductResponse> findByCategoryId(@PathVariable String categoryId) {
    return productService.findByCategoryId(categoryId);
  }

  @GetMapping("supplier/{supplierId}")
  public List<ProductResponse> findBySupplierId(@PathVariable String supplierId) {
    return productService.findBySupplierId(supplierId);
  }

  @PutMapping("{id}")
  public ProductResponse update(@RequestBody ProductRequest request, @PathVariable Integer id) {
    return productService.update(request, id);
  }

  @DeleteMapping("{id}")
  public SuccessResponse delete(@PathVariable Integer id) {
    return productService.delete(id);
  }

  @PostMapping("check-stock")
  public SuccessResponse checkProductsStock(@RequestBody ProductCheckStockRequest request) {
    return productService.checkProductsStock(request);
  }

  @GetMapping("{id}/sales")
  public ProductSalesResponse findProductSales(@PathVariable Integer id) {
    return productService.findProductSales(id);
  }
}
