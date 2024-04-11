package com.andre.ReservaDeHotel.utils.interfaces;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;

import java.time.LocalDate;

public interface ValidacaoReservaStrategy {
  void validaDataReserva(ReservaRequestDTO dto, LocalDate data);

}