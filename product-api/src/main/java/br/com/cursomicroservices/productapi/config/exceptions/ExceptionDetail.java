package br.com.cursomicroservices.config.exceptions;

import lombok.Data;

@Data
public class ExceptionDetail {

  private int status;
  private String message;
}
