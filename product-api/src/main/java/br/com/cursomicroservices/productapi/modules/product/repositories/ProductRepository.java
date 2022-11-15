package br.com.cursomicroservices.productapi.modules.product.repositories;

import br.com.cursomicroservices.productapi.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  List<Product> findByNameIgnoreCaseContaining(String name);
  List<Product> findByCategoryId(Integer id);
  List<Product> findBySupplierId(Integer id);

  Boolean existsByCategoryID(Integer id);
  Boolean existsBySupplierID(Integer id);
}
