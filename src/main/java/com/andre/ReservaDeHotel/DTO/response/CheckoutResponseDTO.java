package com.andre.ReservaDeHotel.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDTO {
  private Long reservaId;
  private Double multa;
  private Double precoTotal;
  private Long diasPermanencia;
}

