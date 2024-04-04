package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.QuartoIndisponivelException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoEstaConfirmadaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservaServiceTest {
  @InjectMocks
  private ReservaService reservaService;

  @Mock
  private QuartoRepository quartoRepository;
  @Mock
  private ReservaRepository reservaRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private QuartoDTO quarto;
  private ReservaDTO reserva;
  private Reserva r;
  private LocalDate dataFinalReserva;
  private LocalDate diaDaReserva;

  @BeforeEach
  void init() {
    quarto = new QuartoDTO(1L, 203, TipoQuarto.duplo,
        "Quarto mais top que existe", 50.0);

    dataFinalReserva = LocalDate.of(2024, 3, 30);
    diaDaReserva = LocalDate.of(2024, 3, 1);

    reserva = new ReservaDTO(1L, 1L, diaDaReserva, dataFinalReserva, 1L, StatusReserva.AGUARDANDO_CHECKIN,100);
    r = new Reserva(1L, new User(), diaDaReserva, dataFinalReserva, StatusReserva.AGUARDANDO_CHECKIN,100, new Quarto());

  }


  @Test
  void testarDataFinalDaReserva() {
    LocalDate dia = LocalDate.of(2024, 04, 01);

    assertThrows(DateTimeException.class, () -> {
      reservaService.validarReserva(reserva, dia);
    });

  }

  @Test
  void testarDataFinalAntesDoDiaDaReserva() {
    LocalDate dia = LocalDate.of(2024, 03, 15);

    reserva.setDiaDaReserva(dataFinalReserva);
    reserva.setDataFinalReserva(diaDaReserva);

    assertThrows(DateTimeException.class, () -> {
      reservaService.validarReserva(reserva, dia);
    });

  }

  @Test
  void testarSeExisteUmaReservaNoPeriodo() {

    List<Reserva> reservas = new ArrayList<>();
    reservas.add(r);

    when(reservaRepository.findReservasByQuartoId(1L)).thenReturn(reservas);

    ReservaDTO reserveMeioAteMeio = reservaBuilder(LocalDate.of(2024, 3, 10),
        LocalDate.of(2024, 3, 15));

    ReservaDTO reservaAntesAteDepois = reservaBuilder(LocalDate.of(2024, 2, 10),
        LocalDate.of(2024, 4, 20));

    ReservaDTO reservaMeioAteDepois = reservaBuilder(LocalDate.of(2024, 3, 15),
        LocalDate.of(2024,4,1));

    ReservaDTO reservaAntesAteMeio = reservaBuilder(LocalDate.of(2024, 2, 10),
        LocalDate.of(2024,3,15));


    assertTrue(reservaService.existeReservaNessePeriodo(reserveMeioAteMeio));
    assertTrue(reservaService.existeReservaNessePeriodo(reservaAntesAteDepois));
    assertTrue(reservaService.existeReservaNessePeriodo(reservaMeioAteDepois));
    assertTrue(reservaService.existeReservaNessePeriodo(reservaAntesAteMeio));

  }

  public ReservaDTO reservaBuilder(LocalDate diaDaReserva, LocalDate dataFinalReserva) {
    return new ReservaDTO(1L, 1L, diaDaReserva,dataFinalReserva,1L,StatusReserva.AGUARDANDO_CHECKIN,100);
  }

}
