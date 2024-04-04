package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.CheckinDTO;
import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.entity.Checkin;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.CheckinAntesDaDataException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaJaConfirmada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckinService {

  @Autowired
  private ReservaRepository reservaRepository;
  @Autowired
  private ReservaService reservaService;
  @Autowired
  private QuartoRepository quartoRepository;

  public CheckinDTO checkin(Long id) {
    Reserva reserva = reservaRepository.getReferenceById(id);
    ReservaDTO reservaDTO = new ReservaDTO(reserva);

    validarDataReserva(reservaDTO,LocalDate.now());

    confirmarReserva(reservaDTO);

    Checkin checkin = new Checkin();

    checkin.setReserva(reserva);

    return new CheckinDTO(checkin); // Criar um construtor que recebe o id da reserva do path variable

  }

  public void confirmarReserva(ReservaDTO reserva) {
      reserva.setStatusReserva(StatusReserva.ATIVA);
      reservaService.update(reserva, reserva.getId());
  }

  public void validarDataReserva(ReservaDTO reserva, LocalDate data) {
    if (reserva.getDiaDaReserva().isAfter(data)) {
      throw new CheckinAntesDaDataException("Voce nao pode fazer o checkin antes do dia");
    }
  }

  public void deletarReservasExpiradas() {
    reservaRepository.deletarReservasExpiradas(LocalDate.now());

    List<Quarto> quartos = quartoRepository.findAll();

//    for (Quarto q: quartos) {
//      if (!q.isDisponivel()) {
//        q.setDisponivel(true);
//        quartoRepository.save(q);
//      }
//    }

  }


}
