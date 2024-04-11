package com.andre.ReservaDeHotel.service.interfaces;

import com.andre.ReservaDeHotel.DTO.CheckinDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Checkin;
import com.andre.ReservaDeHotel.entity.Reserva;

import java.time.LocalDate;


public interface ICheckinService {
  public CheckinDTO checkin(Long id);
  private void adicionarQuartoAReserva(ReservaResponseDTO reserva, Checkin checkin, Reserva r) {}
  void validarDataReserva(ReservaResponseDTO reserva, LocalDate data);
  void deletarReservasExpiradas();
}
