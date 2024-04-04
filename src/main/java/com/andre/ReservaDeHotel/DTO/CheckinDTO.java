package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.Checkin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinDTO {
  private Long id;
  private Long reservaId;

  public CheckinDTO(Checkin entity) {
    this.id = entity.getId();
    this.reservaId = entity.getReserva().getId();
  }
}
