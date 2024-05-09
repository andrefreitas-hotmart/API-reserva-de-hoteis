package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.CheckoutRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.CheckoutResponseDTO;
import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.entity.Checkout;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.repository.CheckoutRepository;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.CheckoutDateException;
import com.andre.ReservaDeHotel.service.interfaces.ICheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.andre.ReservaDeHotel.entity.Checkout.copyDtoToEntity;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements ICheckoutService {

  private ReservaRepository reservaRepository;
  private QuartoRepository quartoRepository;
  private ReservaServiceImpl reservaService;
  private CheckoutRepository checkoutRepository;

  public CheckoutResponseDTO checkout (Long reservaId, CheckoutRequestDTO checkoutRequestDTO) {
    //
    Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);

    reservaService.checarReservaNaoExiste(reservaOptional);

    Reserva reserva = reservaOptional.get();

    reserva.getQuarto().setDisponivel(true); // Salvando 2 entidades ao mesmo tempo

    reservaService.checarReservaConfirmada(reserva);

    reserva.setStatusReserva(StatusReserva.ENCERRADA);

    reservaRepository.save(reserva);
    //

    Quarto quarto = quartoRepository.getReferenceById(reserva.getQuarto().getId());

    ReservaResponseDTO reservaDTO = new ReservaResponseDTO(reserva);
    QuartoDTO quartoDTO = new QuartoDTO(quarto);

    LocalDate dataDoCheckout = checkoutRequestDTO.getDataCheckout();

    validaDataCheckout(dataDoCheckout, reservaDTO);

    long diasDePermanencia = calculaDiasPermanencia(reservaDTO, dataDoCheckout);

    validaDiasDePermanencia(diasDePermanencia);

    double multa = 0;

    if (checarAtraso(reservaDTO, dataDoCheckout)) {
      long diasDeAtraso = calcularDiasDeAtraso(reservaDTO, dataDoCheckout);
      multa = calculaMulta(diasDeAtraso, quartoDTO);
    }

    double precototal = calculaPrecoTotal(diasDePermanencia, multa, quartoDTO);

    CheckoutResponseDTO checkoutResponseDTO = buildCheckoutDTO(multa, diasDePermanencia, reservaDTO, precototal);

    Checkout checkout = new Checkout();

    copyDtoToEntity(checkout,checkoutResponseDTO, reserva);
    checkoutRepository.save(checkout);

    return checkoutResponseDTO;

  }



  private void validaDiasDePermanencia(long diasDePermanencia) {
    if (diasDePermanencia == 0) {
      throw new CheckoutDateException("Você nao pode fazer checkout no mesmo dia que fez a reserva");
    }
  }

  private void validaDataCheckout(LocalDate dataDoCheckout, ReservaResponseDTO reservaDTO) {
    if (dataDoCheckout.isBefore(reservaDTO.getDiaDaReserva())) {
      throw new CheckoutDateException("Voce deve fazer o checkout apenas depois do dia da reserva");
    }
  }

  private CheckoutResponseDTO buildCheckoutDTO(double multa, long diasDePermanencia, ReservaResponseDTO reservaDTO, double precototal) {
    CheckoutResponseDTO checkoutDTO = new CheckoutResponseDTO();

    checkoutDTO.setMulta(multa);
    checkoutDTO.setDiasPermanencia(diasDePermanencia);
    checkoutDTO.setReservaId(reservaDTO.getId());
    checkoutDTO.setPrecoTotal(precototal);

    return checkoutDTO;
  }

  public long calculaDiasPermanencia(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDiaDaReserva().until(diaDoCheckout, ChronoUnit.DAYS);
  }

  public long calcularDiasDeAtraso(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDataFinalReserva().until(diaDoCheckout, ChronoUnit.DAYS);
  }

  public boolean checarAtraso(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDataFinalReserva().isBefore(diaDoCheckout);
  }

  public double calculaMulta(double diasDeAtraso, QuartoDTO quartoDTO) {
    return (quartoDTO.getPrecoNoite() * 1.3) * diasDeAtraso;
  }

  public double calculaPrecoTotal(long diasDePermanencia, double multa, QuartoDTO quartoDTO) {
    return (quartoDTO.getPrecoNoite() * diasDePermanencia) + multa;
  }

}
