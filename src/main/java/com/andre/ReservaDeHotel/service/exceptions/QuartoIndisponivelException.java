package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuartoIndisponivelException extends RuntimeException{
  public QuartoIndisponivelException(String msg){
    super(msg);
  }
}
