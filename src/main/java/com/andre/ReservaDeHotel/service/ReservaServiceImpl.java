package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.projections.QuartoProjection;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoEstaConfirmadaException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoExisteException;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import com.andre.ReservaDeHotel.service.interfaces.IReservaService;
import com.andre.ReservaDeHotel.strategy.DataFInalDepoisDeHoje;
import com.andre.ReservaDeHotel.strategy.DataFinalDepoisDaReserva;
import com.andre.ReservaDeHotel.strategy.DiaDaReservaDepoisDeHoje;
import com.andre.ReservaDeHotel.strategy.ValidadorDatasReserva;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ReservaServiceImpl implements IReservaService {

  private ReservaRepository reservaRepository;
  private UserRepository userRepository;
  private ValidadorDatasReserva validadorDatasReserva;
  private QuartoRepository quartoRepository;

  private static final int PRECO_INICIAL_RESERVA = 100;

  public ReservaResponseDTO insert(ReservaRequestDTO dto) {

    User user = userRepository.getReferenceById(dto.getUserId());

    validadorDatasReserva.dataFinalDepoisReserva(new DataFinalDepoisDaReserva(), dto, LocalDate.now());
    validadorDatasReserva.dataFinalDepoisDeHoje(new DataFInalDepoisDeHoje(), dto, LocalDate.now());
    validadorDatasReserva.diaDaReservaDepoisDeHoje(new DiaDaReservaDepoisDeHoje(), dto, LocalDate.now());

    var reserva = buildReserva(dto, user);
    reserva = reservaRepository.save(reserva);

    Long quartoOptional = quartoRepository.existeQuartoDisponivel(dto.getDiaDaReserva(), dto.getDataFinalReserva());

    System.out.println("Dia da reserva: " + dto.getDiaDaReserva() + " DataFinalReserva: " + dto.getDataFinalReserva());

    if (dto.getDiaDaReserva() instanceof LocalDate) {
      System.out.println("É uma data");
    }

    if (quartoOptional > 0) {
      System.out.println("Terá um quarto presente nessa data");
    } else {
      System.out.println("Nao existira um quarto disponivel nessa data");
    }

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

  public List<ReservaResponseDTO> findAll() {
    List<Reserva> reservas = reservaRepository.findAll();

    return reservas.stream().map(ReservaResponseDTO::new).toList();
  }

  public ReservaResponseDTO findById(Long id) {
    Reserva reserva = reservaRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Reserva nao encontrada")
    );

    return new ReservaResponseDTO(reserva);

  }
  public void delete(Long id) {
    reservaRepository.deleteById(id);
  }

  public ReservaRequestDTO update(ReservaRequestDTO dto, Long id) {
    Reserva reserva = reservaRepository.getReferenceById(id);

    reserva.setDataFinalReserva(dto.getDataFinalReserva());

    reservaRepository.save(reserva);

    return new ReservaRequestDTO(reserva);

  }

  public void checarReservaConfirmada(Reserva reserva) {
    if (!reserva.getStatusReserva().equals(StatusReserva.ATIVA)) {
      throw new ReservaNaoEstaConfirmadaException("A reserva nao esta confirmada");
    }
  }

  public void checarReservaNaoExiste(Optional<Reserva> reservaOptional) {
    if (reservaOptional.isEmpty()) {
      throw new ReservaNaoExisteException("Reserva nao existe");
    }
  }

  public void confirmarReserva(Reserva reserva) {
    reserva.setStatusReserva(StatusReserva.ATIVA);
    reservaRepository.save(reserva);
  }
}
