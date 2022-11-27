package br.com.cursomicroservices.productapi.modules.product.services;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.config.exceptions.ValidationException;
import br.com.cursomicroservices.productapi.modules.category.services.CategoryService;
import br.com.cursomicroservices.productapi.modules.product.interfaces.*;
import br.com.cursomicroservices.productapi.modules.product.models.Product;
import br.com.cursomicroservices.productapi.modules.product.repositories.ProductRepository;
import br.com.cursomicroservices.productapi.modules.sales.client.SalesClient;
import br.com.cursomicroservices.productapi.modules.sales.interfaces.SalesConfirmationDTO;
import br.com.cursomicroservices.productapi.modules.sales.interfaces.SalesProductResponse;
import br.com.cursomicroservices.productapi.modules.sales.enums.SalesStatus;
import br.com.cursomicroservices.productapi.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.cursomicroservices.productapi.modules.supplier.services.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cursomicroservices.productapi.config.RequestUtil.getCurrentRequest;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

  private static final Integer ZERO = 0; 

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SupplierService supplierService;

  @Autowired
  private CategoryService categoryService;

  public ProductResponse findByIdResponse(Integer id) {
    return ProductResponse.of(findById(id));
  }

  public List<ProductResponse> findAll() {
    return productRepository
      .findAll()
      .stream()
      .map(ProductResponse::of)
      .collect(Collectors.toList());
  }

  public List<ProductResponse> findByName(String name) {
    if (isEmpty(name)) {
      throw new ValidationException("The product name must be informed.");
    }

    return productRepository
      .findByNameIgnoreCaseContaining(name)
      .stream()
      .map(ProductResponse::of)
      .collect(Collectors.toList());
  }

  public List<ProductResponse> findBySupplierId(Integer supplierId) {
    if (isEmpty(supplierId)) {
      throw new ValidationException("The product supplier ID must be informed.");
    }

    return productRepository
      .findBySupplierId(supplierId)
      .stream()
      .map(ProductResponse::of)
      .collect(Collectors.toList());
  }

  public List<ProductResponse> findByCategoryId(Integer categoryId) {
    if (isEmpty(categoryId)) {
      throw new ValidationException("The product category ID must be informed.");
    }

    return productRepository
      .findByCategoryId(categoryId)
      .stream()
      .map(ProductResponse::of)
      .collect(Collectors.toList());
  }

  public Product findById(Integer id) {
    validateInformedId(id);

    return productRepository
      .findById(id)
      .orElseThrow(() -> new ValidationException("There's no product for the given ID."));
  }

  public Boolean existsByCategoryID(Integer categoryId) {
    return productRepository.existsByCategoryID(categoryId);
  }

  public Boolean existsBySupplierID(Integer supplierId) {
    return productRepository.existsBySupplierID(supplierId);
  }

  public ProductResponse save(ProductRequest request) {
    validateProductNameInformed(request);
    validateProductQuantityAvailableInformed(request);
    validateProductSupplierInformed(request);
    validateProductCategoryInformed(request);

    var supplier = supplierService.findById(request.getSupplierId());
    var category = categoryService.findById(request.getCategoryId());
    var product = productRepository.save(Product.of(request, category, supplier));

    return ProductResponse.of(product);
  }

  public ProductResponse update(ProductRequest request, Integer id) {
    validateProductNameInformed(request);
    validateProductQuantityAvailableInformed(request);
    validateProductSupplierInformed(request);
    validateProductCategoryInformed(request);
    validateInformedId(id);

    var product = Product.of(request);
    product.setId(id);
    productRepository.save(product);
    return ProductResponse.of(product);
  }

  private void validateProductNameInformed(ProductRequest request) {
    if (isEmpty(request.getName())) {
      throw new ValidationException("The product name was not informed.");
    }
  }

  private void validateProductQuantityAvailableInformed(ProductRequest request) {
    if (isEmpty(request.getQuantityAvailable())) {
      throw new ValidationException("The product quantity available was not informed.");
    }

    if (request.getQuantityAvailable() <= ) {
      throw new ValidationException("The product quantity available should not be less or equal to zero.");
    }
  }

  private void validateProductSupplierInformed(ProductRequest request) {
    if (isEmpty(request.getSupplier())) {
      throw new ValidationException("The product supplier ID was not informed.");
    }
  }

  private void validateProductCategoryInformed(ProductRequest request) {
    if (isEmpty(request.getCategory())) {
      throw new ValidationException("The product category ID was not informed.");
    }
  }

  public SuccessResponse delete(Integer id) {
    validateInformedId(id);

    productRepository.deleteById(id);
    return SuccessResponse.create("The product was deleted.");
  }

  private void validateInformedId(Integer id) {
    if (isEmpty(id)) {
      throw new ValidationException("The product ID must be informed.");
    }
  }


}
