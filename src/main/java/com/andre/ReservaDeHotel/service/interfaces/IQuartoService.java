package com.andre.ReservaDeHotel.service.interfaces;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;

import java.util.List;

public interface IQuartoService {
  QuartoDTO insert(QuartoDTO dto);
  QuartoDTO findById(Long id);
  List<QuartoDTO> findAll();
  QuartoDTO update(QuartoDTO dto, Long id);
  void delete(Long id);
}
