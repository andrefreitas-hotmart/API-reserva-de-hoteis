package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.service.exceptions.CheckinAntesDaDataException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaJaConfirmada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CheckinServiceTest {

  @InjectMocks
  private CheckinServiceImpl checkinService;
  @Mock
  private ReservaServiceImpl reservaService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private static ReservaRequestDTO reserva;

  @BeforeEach
  void init() {

    LocalDate dataFinalReserva = LocalDate.of(2024, 3, 20);
    LocalDate diaDaReserva = LocalDate.of(2024, 3, 10);

    reserva = new ReservaRequestDTO(1L, 1L, diaDaReserva, dataFinalReserva, 1L, StatusReserva.AGUARDANDO_CHECKIN,100);
  }

  @Test
  void testarSeOServiceConfirmaAReservaSeElaNaoEstiverConfirmada() {

    checkinService.confirmarReserva(reserva);

    when(reservaService.update(reserva, reserva.getId())).thenReturn(reserva);


    assertEquals(StatusReserva.ATIVA, reserva.getStatusReserva());

  }

  @Test
  void testarSeLancaExcecaoComReservaJaConfirmada() {
    reserva.setStatusReserva(StatusReserva.ATIVA);


    assertThrows(ReservaJaConfirmada.class, () -> {
      checkinService.confirmarReserva(reserva);
    });
  }

  @Test
  void testarSeLancaExcecaoDeCheckinAntesDaData() {
    assertThrows(CheckinAntesDaDataException.class, () -> {
      checkinService.validarDataReserva(reserva, LocalDate.of(2024,03,02));
    });
  }


}
