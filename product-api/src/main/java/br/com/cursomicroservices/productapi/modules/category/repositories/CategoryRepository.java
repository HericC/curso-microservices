package br.com.cursomicroservices.productapi.modules.category.repositories;

import br.com.cursomicroservices.productapi.modules.category.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
  List<Category> findByDescriptionIgnoreCaseContaining(String description);
}
