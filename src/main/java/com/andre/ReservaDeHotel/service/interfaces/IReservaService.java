package com.andre.ReservaDeHotel.service.interfaces;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;

import java.util.List;
import java.util.Optional;

public interface IReservaService {
  ReservaResponseDTO insert(ReservaRequestDTO dto);
  Reserva buildReserva(ReservaRequestDTO dto, User user);
  List<ReservaResponseDTO> findAll();
  ReservaResponseDTO findById(Long id);
  void checarReservaConfirmada(Reserva reserva);
  void checarReservaNaoExiste(Optional<Reserva> reservaOptional);
  void delete(Long id);
  void confirmarReserva(Reserva reserva);
  ReservaRequestDTO update(ReservaRequestDTO dto, Long id);
}
