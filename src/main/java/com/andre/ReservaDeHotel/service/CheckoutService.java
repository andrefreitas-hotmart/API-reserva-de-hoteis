package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.CheckoutRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.CheckoutResponseDTO;
import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.entity.Checkout;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.repository.CheckoutRepository;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.ReservaRepository;
import com.andre.ReservaDeHotel.service.exceptions.CheckoutDateException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoEstaConfirmadaException;
import com.andre.ReservaDeHotel.service.exceptions.ReservaNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class CheckoutService {

  @Autowired
  private ReservaRepository reservaRepository;
  @Autowired
  private QuartoRepository quartoRepository;
  @Autowired
  private QuartoService quartoService;
  @Autowired
  private ReservaService reservaService;
  @Autowired
  private CheckoutRepository checkoutRepository;

  public CheckoutResponseDTO checkout (Long reservaId, CheckoutRequestDTO checkoutRequestDTO) throws Exception{

    Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
    
    if(reservaOptional.isEmpty()){
      throw new ReservaNaoExisteException("Reserva nao existe");
    }

    Reserva reserva = reservaOptional.get();

    if (!reserva.getStatusReserva().equals(StatusReserva.ATIVA)) {
      throw new ReservaNaoEstaConfirmadaException("A reserva nao esta confirmada");
    }

    reserva.setStatusReserva(StatusReserva.ENCERRADA);

    reservaRepository.save(reserva);

    Quarto quarto = quartoRepository.getReferenceById(reserva.getQuarto().getId());

    ReservaDTO reservaDTO = new ReservaDTO(reserva);
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

    copyDtoToEntityAndSave(checkout,checkoutResponseDTO, reserva);

    return checkoutResponseDTO;

  }

  private void copyDtoToEntityAndSave(Checkout checkout, CheckoutResponseDTO dto, Reserva reserva) {
    checkout.setReserva(reserva);
    checkout.setMulta(dto.getMulta());
    checkout.setDiasPermanencia(dto.getDiasPermanencia());
    checkout.setPrecoTotal(dto.getPrecoTotal());
    checkoutRepository.save(checkout);
  }

  private static void validaDiasDePermanencia(long diasDePermanencia) {
    if (diasDePermanencia == 0) {
      throw new CheckoutDateException("Você nao pode fazer checkout no mesmo dia que fez a reserva");
    }
  }

  private static void validaDataCheckout(LocalDate dataDoCheckout, ReservaDTO reservaDTO) {
    if(dataDoCheckout.isBefore(reservaDTO.getDiaDaReserva())) {
      throw new CheckoutDateException("Voce deve fazer o checkout apenas depois do dia da reserva");
    }
  }

  private static CheckoutResponseDTO buildCheckoutDTO( double multa, long diasDePermanencia, ReservaDTO reservaDTO, double precototal) {
    CheckoutResponseDTO checkoutDTO = new CheckoutResponseDTO();

    checkoutDTO.setMulta(multa);
    checkoutDTO.setDiasPermanencia(diasDePermanencia);
    checkoutDTO.setReservaId(reservaDTO.getId());
    checkoutDTO.setPrecoTotal(precototal);

    return checkoutDTO;
  }

  public long calculaDiasPermanencia(ReservaDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDiaDaReserva().until(diaDoCheckout, ChronoUnit.DAYS);
  }

  public long calcularDiasDeAtraso(ReservaDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDataFinalReserva().until(diaDoCheckout, ChronoUnit.DAYS);
  }

  public boolean checarAtraso(ReservaDTO reservaDTO, LocalDate diaDoCheckout) {
    return reservaDTO.getDataFinalReserva().isBefore(diaDoCheckout);
  }

  public double calculaMulta(double diasDeAtraso, QuartoDTO quartoDTO) {
    return (quartoDTO.getPrecoNoite() * 1.3) * diasDeAtraso;
  }

  public double calculaPrecoTotal(long diasDePermanencia, double multa, QuartoDTO quartoDTO) {
    return (quartoDTO.getPrecoNoite() * diasDePermanencia) + multa;
  }

}
