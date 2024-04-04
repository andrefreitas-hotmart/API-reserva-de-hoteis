package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ReservaNaoEstaConfirmadaException extends RuntimeException{
  public ReservaNaoEstaConfirmadaException(String msg) {
    super(msg);
  }
}
