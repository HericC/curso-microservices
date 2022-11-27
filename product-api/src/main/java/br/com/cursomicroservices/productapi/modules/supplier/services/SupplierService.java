package br.com.cursomicroservices.productapi.modules.supplier.services;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.config.exceptions.ValidationException;
import br.com.cursomicroservices.productapi.modules.product.services.ProductService;
import br.com.cursomicroservices.productapi.modules.supplier.interfaces.SupplierRequest;
import br.com.cursomicroservices.productapi.modules.supplier.interfaces.SupplierResponse;
import br.com.cursomicroservices.productapi.modules.supplier.models.Supplier;
import br.com.cursomicroservices.productapi.modules.supplier.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

  @Autowired
  private SupplierRepository supplierRepository;

  @Autowired
  private ProductRepository productRepository;

  public SupplierResponse findByIdResponse(Integer id) {
    return SupplierResponse.of(findById(id));
  }

  public List<SupplierResponse> findAll() {
    return supplierRepository
      .findAll()
      .stream()
      .map(SupplierResponse::of)
      .collect(Collectors.toList());
  }

  public List<SupplierResponse> findByName(String name) {
    if (isEmpty(name)) {
        throw new ValidationException("The supplier name must be informed.");
    }

    return supplierRepository
      .findByNameIgnoreCaseContaining(name)
      .stream()
      .map(SupplierResponse::of)
      .collect(Collectors.toList());
  }

  public Supplier findById(Integer id) {
    validateInformedId(id);

    return supplierRepository
      .findById(id)
      .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
  }

  public SupplierResponse save(SupplierRequest request) {
    validateSupplierNameInformed(request);

    var supplier = supplierRepository.save(Supplier.of(request));
    return SupplierResponse.of(supplier);
  }

  public SupplierResponse update(SupplierRequest request, Integer id) {
    validateSupplierNameInformed(request);
    validateInformedId(id);

    var supplier = Supplier.of(request);
    supplier.setId(id);
    supplierRepository.save(supplier);
    return SupplierResponse.of(supplier);
  }

  private void validateSupplierNameInformed(SupplierRequest request) {
    if (isEmpty(request.getName())) {
      throw new ValidationException("The supplier name was not informed.");
    }
  }

  public SuccessResponse delete(Integer id) {
    validateInformedId(id);

    if (productService.existsBySupplierId(id)) {
      throw new ValidationException("You cannot delete this supplier because it's already defined by a product.");
    }

    supplierRepository.deleteById(id);
    return SuccessResponse.create("The supplier was deleted.");
  }

  private void validateInformedId(Integer id) {
    if (isEmpty(id)) {
      throw new ValidationException("The supplier ID must be informed.");
    }
  }
}
