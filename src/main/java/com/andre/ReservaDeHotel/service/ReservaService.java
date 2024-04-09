package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoEstaConfirmadaException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private QuartoRepository quartoRepository;

  private static final int PRECO_INICIAL_RESERVA = 100;

  public ReservaResponseDTO insert(ReservaRequestDTO dto) throws Exception {

    User user = userRepository.getReferenceById(dto.getUserId());

    validarReserva(dto, LocalDate.now());

    var reserva = buildReserva(dto, user);
    reserva = reservaRepository.save(reserva);

    return new ReservaResponseDTO(reserva);

  }

  public Reserva buildReserva(ReservaRequestDTO dto, User user) {
    Reserva reserva = new Reserva();

    reserva.setDiaDaReserva(dto.getDiaDaReserva());
    reserva.setDataFinalReserva(dto.getDataFinalReserva());
    reserva.setUser(user);
    reserva.setTipoQuartoEscolhido(dto.getTipoQuartoEscolhido());
    reserva.setPrecoMaximoQuarto(dto.getPrecoMaximoQuarto());
    reserva.setStatusReserva(StatusReserva.AGUARDANDO_CHECKIN);
    reserva.setPrecoInicialReserva(PRECO_INICIAL_RESERVA);

    return reserva;
  }

  public void validarReserva(ReservaRequestDTO dto, LocalDate data) {
    if(dto.getDataFinalReserva().isBefore(data)){
      throw new DateTimeException("A data final da reserva deve ser depois do dia de hoje");
    }
    if (dto.getDiaDaReserva().isAfter(dto.getDataFinalReserva())) {
      throw new DateTimeException("A data final da reserva deve ser depois do dia da reserva");
    }
//    if (existeReservaNessePeriodo(dto)) {
//      throw new DateTimeException("Ja existe uma reserva nesse período");
//    }
  }

//  public boolean existeReservaNessePeriodo(ReservaDTO dto) {
//    List<Reserva> reservas = reservaRepository.findReservasByQuartoId(dto.getQuartoId());
//
//    for (Reserva r: reservas) {
//      if(isOverlapUsingLocalDateAndDuration(dto.getDiaDaReserva(),dto.getDataFinalReserva(),
//      r.getDiaDaReserva(),r.getDataFinalReserva())) {
//        return true;
//      }
//    }
//
//    return false;
//  }
//
//  boolean isOverlapUsingLocalDateAndDuration(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
//    long overlap = Math.min(end1.toEpochDay(), end2.toEpochDay()) -
//        Math.max(start1.toEpochDay(), start2.toEpochDay());
//
//    return overlap >= 0;
//  }

  public List<ReservaResponseDTO> findAll(){
    List<Reserva> reservas = reservaRepository.findAll();

    return reservas.stream().map(ReservaResponseDTO::new).toList();
  }

  public ReservaResponseDTO findById(Long id){
    var reserva = reservaRepository.findById(id).get();

    return new ReservaResponseDTO(reserva);

  }
  public void delete(Long id){
    reservaRepository.deleteById(id);
  }

  public ReservaRequestDTO update(ReservaRequestDTO dto, Long id){
    Reserva reserva = reservaRepository.getReferenceById(id);

    reserva.setDataFinalReserva(dto.getDataFinalReserva());
    //reserva.setStatusReserva(dto.getStatusReserva());

    reservaRepository.save(reserva);

    return new ReservaRequestDTO(reserva);

  }

  public static void checarReservaConfirmada(Reserva reserva) {
    if (!reserva.getStatusReserva().equals(StatusReserva.ATIVA)) {
      throw new ReservaNaoEstaConfirmadaException("A reserva nao esta confirmada");
    }
  }

  public static void checarReservaNaoExiste(Optional<Reserva> reservaOptional) {
    if (reservaOptional.isEmpty()) {
      throw new ReservaNaoExisteException("Reserva nao existe");
    }
  }
}
