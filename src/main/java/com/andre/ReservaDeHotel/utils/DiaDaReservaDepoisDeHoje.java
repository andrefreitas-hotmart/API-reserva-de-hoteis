package com.andre.ReservaDeHotel.utils;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.utils.interfaces.ValidacaoReservaStrategy;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DiaDaReservaDepoisDeHoje implements ValidacaoReservaStrategy {
  @Override
  public void validaDataReserva(ReservaRequestDTO dto, LocalDate data) {
    if (dto.getDiaDaReserva().isBefore(data)) {
      throw new DateTimeException("O dia da reserva deve ser depois de hoje");
    }
  }
}
