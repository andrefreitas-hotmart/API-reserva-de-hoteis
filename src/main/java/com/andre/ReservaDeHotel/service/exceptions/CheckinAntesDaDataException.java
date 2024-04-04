package com.andre.ReservaDeHotel.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CheckinAntesDaDataException extends RuntimeException{
  public CheckinAntesDaDataException(String msg) {
    super(msg);
  }
}
