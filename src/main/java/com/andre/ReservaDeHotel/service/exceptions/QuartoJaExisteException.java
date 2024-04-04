package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class QuartoJaExisteException extends RuntimeException{

  public QuartoJaExisteException(String msg) {
    super(msg);
  }

}
