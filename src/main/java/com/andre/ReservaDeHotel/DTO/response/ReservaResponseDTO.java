package com.andre.ReservaDeHotel.DTO.response;

import com.andre.ReservaDeHotel.entity.Quarto;
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
public class ReservaResponseDTO {
  private Long id;
  private Long userId;
  private LocalDate diaDaReserva;
  private LocalDate dataFinalReserva;
  private StatusReserva statusReserva;
  private int precoInicialReserva;
  private Quarto quarto;
  private TipoQuarto tipoQuartoEscolhido;
  private Double precoMaximoQuarto;

  public ReservaResponseDTO(Reserva entity){
    this.id = entity.getId();
    this.userId = entity.getUser().getId();
    this.diaDaReserva = entity.getDiaDaReserva();
    this.dataFinalReserva = entity.getDataFinalReserva();
    this.statusReserva = entity.getStatusReserva();
    this.precoInicialReserva = entity.getPrecoInicialReserva();
    this.tipoQuartoEscolhido = entity.getTipoQuartoEscolhido();
    this.precoMaximoQuarto = entity.getPrecoMaximoQuarto();
    this.quarto = entity.getQuarto();
  }

}
