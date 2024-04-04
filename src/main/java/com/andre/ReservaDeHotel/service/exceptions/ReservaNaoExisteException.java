package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservaNaoExisteException extends RuntimeException{

  public ReservaNaoExisteException(String msg){
    super(msg);
  }

}
