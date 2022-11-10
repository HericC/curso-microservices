package br.com.cursomicroservices.productapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class StatusController {

  @GetMapping("status")
  public ResponseEntity<HashMap<string, Object>> getApiStatus() {
    var response = new HashMap<string, Object>();

    response.put("service", "Product-API");
    response.put("status", "up");
    response.put("httpStatus", HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }
}
