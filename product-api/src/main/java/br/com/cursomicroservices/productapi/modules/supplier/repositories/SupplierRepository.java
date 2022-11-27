package br.com.cursomicroservices.productapi.modules.supplier.repositories;

import br.com.cursomicroservices.productapi.modules.supplier.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
  List<Supplier> findByNameIgnoreCaseContaining(String name);
}
