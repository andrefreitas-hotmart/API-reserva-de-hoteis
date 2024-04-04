package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {


  private Long id;
  private Long userId;
  private LocalDate diaDaReserva;
  private LocalDate dataFinalReserva;
  private Long quartoId;
  private StatusReserva statusReserva;
  private int precoInicialReserva;

  public ReservaDTO(Reserva entity){
    this.id = entity.getId();
    this.userId = entity.getUser().getId();
    this.diaDaReserva = entity.getDiaDaReserva();
    this.dataFinalReserva = entity.getDataFinalReserva();
    this.quartoId = entity.getQuarto().getId();
    this.statusReserva = entity.getStatusReserva();
    this.precoInicialReserva = entity.getPrecoInicialReserva();
  }

}
