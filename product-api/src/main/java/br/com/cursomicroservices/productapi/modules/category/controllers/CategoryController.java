package br.com.cursomicroservices.productapi.modules.category.controllers;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryRequest;
import br.com.cursomicroservices.productapi.modules.category.interfaces.CategoryResponse;
import br.com.cursomicroservices.productapi.modules.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public interface CategoryController  {

  @Autowired
  private CategoryService categoryService;

  @PostMapping
  public CategoryResponse save(@RequestBody CategoryRequest request) {
    return categoryService.save(request);
  }

  @GetMapping
  public List<CategoryResponse> findAll() {
    return categoryService.findAll();
  }

  @GetMapping("{id}")
  public CategoryResponse findById(@PathVariable Integer id) {
    return categoryService.findByIdResponse(id);
  }

  @GetMapping("description/{description}")
  public List<CategoryResponse> findByDescription(@PathVariable String description) {
    return categoryService.findByDescription(description);
  }

  @PutMapping("{id}")
  public CategoryResponse update(@RequestBody CategoryRequest request, @PathVariable Integer id) {
    return categoryService.update(request, id);
  }

  @DeleteMapping("{id}")
  public SuccessResponse delete(@PathVariable Integer id) {
    return categoryService.delete(id);
  }
}
