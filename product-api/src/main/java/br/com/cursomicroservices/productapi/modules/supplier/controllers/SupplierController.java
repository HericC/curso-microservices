package br.com.cursomicroservices.productapi.modules.supplier.controllers;

import br.com.cursomicroservices.productapi.config.exceptions.SuccessResponse;
import br.com.cursomicroservices.productapi.modules.supplier.interfaces.SupplierRequest;
import br.com.cursomicroservices.productapi.modules.supplier.interfaces.SupplierResponse;
import br.com.cursomicroservices.productapi.modules.supplier.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/supplier")
public interface SupplierController  {

  @Autowired
  private SupplierService supplierService;

  @PostMapping
  public SupplierResponse save(@RequestBody SupplierRequest request) {
    return supplierService.save(request);
  }

  @GetMapping
  public List<SupplierResponse> findAll() {
    return supplierService.findAll();
  }

  @GetMapping("{id}")
  public SupplierResponse findById(@PathVariable Integer id) {
    return supplierService.findByIdResponse(id);
  }

  @GetMapping("name/{name}")
  public List<SupplierResponse> findByName(@PathVariable String name) {
    return supplierService.findByName(name);
  }

  @PutMapping("{id}")
  public SupplierResponse update(@RequestBody SupplierRequest request, @PathVariable Integer id) {
    return supplierService.update(request, id);
  }

  @DeleteMapping("{id}")
  public SuccessResponse delete(@PathVariable Integer id) {
    return supplierService.delete(id);
  }
}
