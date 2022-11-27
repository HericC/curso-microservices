package br.com.cursomicroservices.productapi.modules.sales.interfaces;

import br.com.cursomicroservices.productapi.modules.sales.enums.SalesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesConfirmationDTO {

  private String salesId;
  private SalesStatus status;
  private String transactionid;
}
