package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.CheckinDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Checkin;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.repository.CheckinRepository;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.CheckinAntesDaDataException;
import com.andre.ReservaDeHotel.service.exceptions.QuartoIndisponivelException;
import com.andre.ReservaDeHotel.service.interfaces.ICheckinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckinServiceImpl implements ICheckinService {

  private ReservaRepository reservaRepository;
  private ReservaServiceImpl reservaService;
  private QuartoRepository quartoRepository;
  private CheckinRepository checkinRepository;

  public CheckinDTO checkin(Long id) {
    Reserva reserva = reservaRepository.getReferenceById(id);
    ReservaResponseDTO reservaDTO = new ReservaResponseDTO(reserva);

    validarDataReserva(reservaDTO,LocalDate.now());

    reservaService.confirmarReserva(reserva);

    Checkin checkin = new Checkin();

    adicionarQuartoAReserva(reservaDTO, checkin, reserva);
    checkin.setReserva(reserva);

    checkinRepository.save(checkin);

    return new CheckinDTO(checkin); // Criar um construtor que recebe o id da reserva do path variable

  }


  private void adicionarQuartoAReserva(ReservaResponseDTO reserva, Checkin checkin, Reserva r) {
    Optional<Quarto> quartoOptional = quartoRepository.findQuartoByDadosDaReserva(
        reserva.getPrecoMaximoQuarto(), reserva.getTipoQuartoEscolhido());

    Quarto quarto = quartoOptional.get();

    if (quartoOptional.isEmpty()) {
      throw new QuartoIndisponivelException("Nao existe nenhum quarto com as caracteristicas que voce busca");
    } else {
      checkin.setQuarto(quarto);
      quarto.setDisponivel(false);
      quartoRepository.save(quarto);

      r.setQuarto(quarto);
      reservaRepository.save(r);

    }

  }


  public void validarDataReserva(ReservaResponseDTO reserva, LocalDate data) {
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