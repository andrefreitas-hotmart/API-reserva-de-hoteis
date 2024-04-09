package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.QuartoJaExisteException;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartoService {

  @Autowired
  QuartoRepository quartoRepository;

  public QuartoDTO insert(QuartoDTO dto){
    Quarto quarto = new Quarto();

    validarSeQuartoExiste(dto);

    quarto.setDisponivel(true);

    copyDtoToEntity(dto, quarto);
    quartoRepository.save(quarto);

    return new QuartoDTO(quarto);
  }

  public void validarSeQuartoExiste(QuartoDTO dto) {
    List<Quarto> quartos = quartoRepository.findAll();

    for (Quarto q: quartos) {
      if (q.getNumeroQuarto() == dto.getNumeroQuarto()) {
        throw new QuartoJaExisteException("Esse numero de quarto ja existe");
      }
    }
  }

  public QuartoDTO findById(Long id){
    Quarto quarto = quartoRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Quarto nao encontrado")
    );

    return new QuartoDTO(quarto);

  }

  public List<QuartoDTO> findAll(){
    List<Quarto> quartos = quartoRepository.findAll();
    return quartos.stream().map(QuartoDTO::new).toList();
  }

  @Transactional
  public QuartoDTO update(QuartoDTO dto, Long id){
    Quarto quarto = quartoRepository.getReferenceById(id);

    copyDtoToEntity(dto, quarto);
    quarto = quartoRepository.save(quarto);

    return new QuartoDTO(quarto);
  }

  public void delete(Long id){
    quartoRepository.deleteById(id);
  }

  private void copyDtoToEntity(QuartoDTO dto, Quarto entity){
    entity.setDescricaoQuarto(dto.getDescricaoQuarto());
    entity.setNumeroQuarto(dto.getNumeroQuarto());
    entity.setPrecoNoite(dto.getPrecoNoite());
    entity.setTipoQuarto(dto.getTipoQuarto());

  }



}
