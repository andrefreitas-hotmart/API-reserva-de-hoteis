package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReservaJaConfirmada extends RuntimeException{

  public ReservaJaConfirmada(String msg){
    super(msg);
  }

}
