package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.CheckoutRequestDTO;
import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoEstaConfirmadaException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class CheckoutServiceTest {
  @Mock
  private ReservaRepository reservaRepository;

  @InjectMocks
  private CheckoutService checkoutService;

  private static ReservaDTO reserva;
  private UserDTO user;
  private QuartoDTO quarto;

  @BeforeEach
  public void init(){
    quarto = new QuartoDTO(1L, 203, TipoQuarto.duplo,
        "Quarto mais top que existe", 50.0);

    LocalDate dataFinalReserva = LocalDate.of(2024, 3, 30);
    LocalDate diaDaReserva = LocalDate.of(2024, 3, 1);

    reserva = new ReservaDTO(1L, 1L, diaDaReserva, dataFinalReserva, 1L, StatusReserva.AGUARDANDO_CHECKIN,100);
    user = new UserDTO(1L, "Tester", "tester@gmail.com", "123456");

  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testarOCalculoDosDiasDePermanencia() {

    LocalDate diaDoCheckout = LocalDate.of(2024, 3, 5);
    Long diasDePermanencia = checkoutService.calculaDiasPermanencia(reserva, diaDoCheckout);

    assertEquals(4 ,diasDePermanencia);

  }

  @Test
  void testarChecarAtraso() {

    LocalDate diaDoCheckoutComAtraso= LocalDate.of(2024, 3, 31);
    LocalDate diaDoCheckoutSemAtraso = LocalDate.of(2024,3,30);

    assertTrue(checkoutService.checarAtraso(reserva,diaDoCheckoutComAtraso));
    assertFalse(checkoutService.checarAtraso(reserva,diaDoCheckoutSemAtraso));

  }

  @Test
  void testarOCalculoDosDiasDeAtraso() {
    LocalDate diaDoCheckoutComAtraso= LocalDate.of(2024, 3, 31);
    LocalDate diaDoCheckoutSemAtraso = LocalDate.of(2024,3,30);

   assertEquals(1, checkoutService.calcularDiasDeAtraso(reserva,diaDoCheckoutComAtraso));
   assertEquals(0, checkoutService.calcularDiasDeAtraso(reserva,diaDoCheckoutSemAtraso));


  }

  @Test
  void testarOCalculoDeMulta() {

    double multa = checkoutService.calculaMulta(2, quarto);

    assertEquals(130, multa);

  }

  @Test
  void calcularPrecoTotalPermanencia() {
    double precoTotal = checkoutService.calculaPrecoTotal(5, 0, quarto);
    double precoTotalComMulta= checkoutService.calculaPrecoTotal(5, 130, quarto);

    assertEquals(250,precoTotal);
    assertEquals(380, precoTotalComMulta);

  }

  @Test
  void testarSeAReservaNaoEstiverConfirmadaOCheckoutNaoFunciona() {

    CheckoutRequestDTO checkoutRequestDTO = new CheckoutRequestDTO(LocalDate.of(2024, 3, 30));

    when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaBuildNaoConfirmada()));

    assertThrows(ReservaNaoEstaConfirmadaException.class, () -> {
      checkoutService.checkout(1L, checkoutRequestDTO);
    });
  }

  @Test
  void testarSeReservaNaoExiste() {

    CheckoutRequestDTO checkoutRequestDTO = new CheckoutRequestDTO(LocalDate.of(2024, 3, 30));

    assertThrows(ReservaNaoExisteException.class, () -> {
      checkoutService.checkout(1L, checkoutRequestDTO);
    });
  }

  public static Reserva reservaBuildNaoConfirmada() {
    return new Reserva(reserva.getId(), new User(), reserva.getDiaDaReserva(),
            reserva.getDataFinalReserva(), StatusReserva.AGUARDANDO_CHECKIN, 100, new Quarto());
  }


}
