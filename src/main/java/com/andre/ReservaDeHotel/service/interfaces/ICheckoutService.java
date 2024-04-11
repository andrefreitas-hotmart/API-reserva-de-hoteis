package com.andre.ReservaDeHotel.service.interfaces;

import com.andre.ReservaDeHotel.DTO.CheckoutRequestDTO;
import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.response.CheckoutResponseDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;

import java.time.LocalDate;

public interface ICheckoutService {
  CheckoutResponseDTO checkout (Long reservaId, CheckoutRequestDTO checkoutRequestDTO);
  private void validaDiasDePermanencia(long diasDePermanencia){}
  private void validaDataCheckout(LocalDate dataDoCheckout, ReservaResponseDTO reservaDTO){}

  private CheckoutResponseDTO buildCheckoutDTO(double multa, long diasDePermanencia, ReservaResponseDTO reservaDTO, double precototal) {
    return null;
  }

  long calculaDiasPermanencia(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout);
  long calcularDiasDeAtraso(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout);
  boolean checarAtraso(ReservaResponseDTO reservaDTO, LocalDate diaDoCheckout);
  double calculaMulta(double diasDeAtraso, QuartoDTO quartoDTO);
  double calculaPrecoTotal(long diasDePermanencia, double multa, QuartoDTO quartoDTO);
}
