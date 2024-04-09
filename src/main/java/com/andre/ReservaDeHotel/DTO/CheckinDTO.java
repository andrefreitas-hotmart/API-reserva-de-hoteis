package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.Checkin;
import com.andre.ReservaDeHotel.entity.Quarto;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinDTO {
  private Long id;
  private Long reservaId;
  private Quarto quarto;

  public CheckinDTO(Checkin entity) {
    this.id = entity.getId();
    this.reservaId = entity.getReserva().getId();
    this.quarto = entity.getQuarto();
  }
}
