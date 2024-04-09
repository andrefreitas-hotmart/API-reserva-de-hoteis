package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {

  private Long id;
  private int numeroQuarto;
  private TipoQuarto tipoQuarto;
  private String descricaoQuarto;
  private Double precoNoite;
  private Boolean disponivel;

  public QuartoDTO(Quarto entity){
    this.id = entity.getId();
    this.numeroQuarto = entity.getNumeroQuarto();
    this.tipoQuarto = entity.getTipoQuarto();
    this.descricaoQuarto = entity.getDescricaoQuarto();
    this.precoNoite = entity.getPrecoNoite();
    this.disponivel = entity.getDisponivel();
  }

}
