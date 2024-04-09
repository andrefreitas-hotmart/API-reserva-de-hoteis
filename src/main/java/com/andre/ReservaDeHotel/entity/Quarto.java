package com.andre.ReservaDeHotel.entity;

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
  private int numeroQuarto;
  private TipoQuarto tipoQuarto;
  private String descricaoQuarto;
  private Double precoNoite;
  private Boolean disponivel;

}
