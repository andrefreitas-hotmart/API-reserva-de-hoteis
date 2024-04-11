package com.andre.ReservaDeHotel.entity;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_quarto")
public class Quarto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private int numeroQuarto;
  private TipoQuarto tipoQuarto;
  private String descricaoQuarto;
  private Double precoNoite;
  private Boolean disponivel;

  public static void copyDtoToEntity(QuartoDTO dto, Quarto entity){
    entity.setDescricaoQuarto(dto.getDescricaoQuarto());
    entity.setNumeroQuarto(dto.getNumeroQuarto());
    entity.setPrecoNoite(dto.getPrecoNoite());
    entity.setTipoQuarto(dto.getTipoQuarto());
  }

}
