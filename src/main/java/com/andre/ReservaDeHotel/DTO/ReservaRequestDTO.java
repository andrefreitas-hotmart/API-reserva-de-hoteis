package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.Reserva;
import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequestDTO {


  private Long id;
  private Long userId;
  private LocalDate diaDaReserva;
  private LocalDate dataFinalReserva;

  private TipoQuarto tipoQuartoEscolhido;
  private Double precoMaximoQuarto;

  public ReservaRequestDTO(Reserva entity){
    this.id = entity.getId();
    this.userId = entity.getUser().getId();
    this.diaDaReserva = entity.getDiaDaReserva();
    this.dataFinalReserva = entity.getDataFinalReserva();
    this.tipoQuartoEscolhido = entity.getTipoQuartoEscolhido();
    this.precoMaximoQuarto = entity.getPrecoMaximoQuarto();
  }

}
