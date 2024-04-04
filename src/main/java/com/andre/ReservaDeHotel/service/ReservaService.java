package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.QuartoIndisponivelException;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;



@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private QuartoRepository quartoRepository;

  private static final int PRECO_INICIAL_RESERVA = 100;

  public ReservaDTO insert(ReservaDTO dto) throws Exception {

    User user = userRepository.getReferenceById(dto.getUserId());
    Quarto quarto = quartoRepository.getReferenceById(dto.getQuartoId());


    validarReserva(dto, LocalDate.now());

    var reserva = buildReserva(dto, user, quarto);
    reserva = reservaRepository.save(reserva);

    return new ReservaDTO(reserva);

  }

  public Reserva buildReserva(ReservaDTO dto, User user, Quarto quarto) {
    Reserva reserva = new Reserva();

    reserva.setDiaDaReserva(dto.getDiaDaReserva());
    reserva.setDataFinalReserva(dto.getDataFinalReserva());
    reserva.setUser(user);
    reserva.setQuarto(quarto);
    reserva.setStatusReserva(StatusReserva.AGUARDANDO_CHECKIN);
    reserva.setPrecoInicialReserva(PRECO_INICIAL_RESERVA);

    return reserva;
  }

  public void validarReserva(ReservaDTO dto, LocalDate data) {
    if(dto.getDataFinalReserva().isBefore(data)){
      throw new DateTimeException("A data final da reserva deve ser depois do dia de hoje");
    }
    if (dto.getDiaDaReserva().isAfter(dto.getDataFinalReserva())) {
      throw new DateTimeException("A data final da reserva deve ser depois do dia da reserva");
    }
    if (existeReservaNessePeriodo(dto)) {
      throw new DateTimeException("Ja existe uma reserva nesse período");
    }
  }

  public boolean existeReservaNessePeriodo(ReservaDTO dto) {
    List<Reserva> reservas = reservaRepository.findReservasByQuartoId(dto.getQuartoId());

    for (Reserva r: reservas) {
      if(isOverlapUsingLocalDateAndDuration(dto.getDiaDaReserva(),dto.getDataFinalReserva(),
      r.getDiaDaReserva(),r.getDataFinalReserva())) {
        return true;
      }
    }

    return false;
  }

  boolean isOverlapUsingLocalDateAndDuration(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
    long overlap = Math.min(end1.toEpochDay(), end2.toEpochDay()) -
        Math.max(start1.toEpochDay(), start2.toEpochDay());

    return overlap >= 0;
  }

  public List<ReservaDTO> findAll(){
    List<Reserva> reservas = reservaRepository.findAll();

    return reservas.stream().map(ReservaDTO::new).toList();
  }

  public ReservaDTO findById(Long id){
    var reserva = reservaRepository.findById(id).get();

    return new ReservaDTO(reserva);

  }
  public void delete(Long id){
    reservaRepository.deleteById(id);
  }

  public ReservaDTO update(ReservaDTO dto, Long id){
    Reserva reserva = reservaRepository.getReferenceById(id);

    reserva.setDataFinalReserva(dto.getDataFinalReserva());
    reserva.setStatusReserva(dto.getStatusReserva());

    reservaRepository.save(reserva);

    return new ReservaDTO(reserva);

  }

}
