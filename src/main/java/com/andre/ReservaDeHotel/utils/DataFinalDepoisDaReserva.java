package com.andre.ReservaDeHotel.utils;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.utils.interfaces.ValidacaoReservaStrategy;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DataFinalDepoisDaReserva implements ValidacaoReservaStrategy {
  @Override
  public void validaDataReserva(ReservaRequestDTO dto, LocalDate data) {
    if (dto.getDiaDaReserva().isAfter(dto.getDataFinalReserva())) {
      throw new DateTimeException("A data final da reserva deve ser depois do dia da reserva");
    }
  }
}
