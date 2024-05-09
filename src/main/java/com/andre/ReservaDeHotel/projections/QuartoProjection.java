package com.andre.ReservaDeHotel.projections;

import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;

public interface QuartoProjection {
  Boolean getDisponivel();
  int getNumeroQuarto();
  Double getPrecoNoite();
  TipoQuarto getTipoQuarto();
  Long getId();
  String getDescricaoQuarto();
}
