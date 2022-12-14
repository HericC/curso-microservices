package br.com.cursomicroservices.productapi.modules.category.services;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.config.exceptions.ValidationException;
import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryRequest;
import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryResponse;
import br.com.cursomicroservices.productapi.modules.category.models.Category;
import br.com.cursomicroservices.productapi.modules.category.repositories.CategoryRepository;
import br.com.cursomicroservices.productapi.modules.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  public CategoryResponse findByIdResponse(Integer id) {
    return CategoryResponse.of(findById(id));
  }

  public List<CategoryResponse> findAll() {
    return categoryRepository
      .findAll()
      .stream()
      .map(CategoryResponse::of)
      .collect(Collectors.toList());
  }

  public List<CategoryResponse> findByDescription(String description) {
    if (isEmpty(description)) {
      throw new ValidationException("The category description must be informed.");
    }

    return categoryRepository
      .findByDescriptionIgnoreCaseContaining(description)
      .stream()
      .map(CategoryResponse::of)
      .collect(Collectors.toList());
  }

  public Category findById(Integer id) {
    validateInformedId(id);

    return categoryRepository
      .findById(id)
      .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
  }

  public CategoryResponse save(CategoryRequest request) {
    validateCategoryNameInformed(request);
    var category = categoryRepository.save(Category.of(request));
    return CategoryResponse.of(category);
  }

  public CategoryResponse update(CategoryRequest request, Integer id) {
    validateCategoryNameInformed(request);
    validateInformedId(id);

    var category = Category.of(request);
    category.setId(id);
    categoryRepository.save(category);
    return CategoryResponse.of(category);
  }

  private void validateCategoryNameInformed(CategoryRequest request) {
    if (isEmpty(request.getDescription())) {
      throw new ValidationException("The category description was not informed.");
    }
  }

  public SuccessResponse delete(Integer id) {
    validateInformedId(id);

    if (productService.existsByCategoryId(id)) {
      throw new ValidationException("You cannot delete this category because it's already defined by a product.");
    }

    categoryRepository.deleteById(id);
    return SuccessResponse.create("The category was deleted.");
  }

  private void validateInformedId(Integer id) {
    if (isEmpty(id)) {
      throw new ValidationException("The category ID must be informed.");
    }
  }
}
