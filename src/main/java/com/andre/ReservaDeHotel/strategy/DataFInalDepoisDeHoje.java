package com.andre.ReservaDeHotel.strategy;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.strategy.interfaces.ValidacaoReservaStrategy;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DataFInalDepoisDeHoje implements ValidacaoReservaStrategy {
  @Override
  public void validaDataReserva(ReservaRequestDTO dto, LocalDate data) {
    if(dto.getDataFinalReserva().isBefore(data)){
      throw new DateTimeException("A data final da reserva deve ser depois do dia de hoje");
    }
  }
}
