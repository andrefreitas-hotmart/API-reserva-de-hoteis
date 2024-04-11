package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import com.andre.ReservaDeHotel.service.interfaces.IQuartoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.andre.ReservaDeHotel.entity.Quarto.copyDtoToEntity;

@Service
@AllArgsConstructor
public class QuartoServiceImpl implements IQuartoService {

  private QuartoRepository quartoRepository;

  public QuartoDTO insert(QuartoDTO dto) {
    Quarto quarto = new Quarto();

    quarto.setDisponivel(true);

    copyDtoToEntity(dto, quarto);
    quartoRepository.save(quarto);

    return new QuartoDTO(quarto);
  }

  public QuartoDTO findById(Long id) {
    Quarto quarto = quartoRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Quarto nao encontrado")
    );

    return new QuartoDTO(quarto);

  }

  public List<QuartoDTO> findAll() {
    List<Quarto> quartos = quartoRepository.findAll();
    return quartos.stream().map(QuartoDTO::new).toList();
  }

  @Transactional
  public QuartoDTO update(QuartoDTO dto, Long id) {
    Quarto quarto = quartoRepository.getReferenceById(id);

    copyDtoToEntity(dto, quarto);
    quarto = quartoRepository.save(quarto);

    return new QuartoDTO(quarto);
  }

  public void delete(Long id) {
    quartoRepository.deleteById(id);
  }

}
