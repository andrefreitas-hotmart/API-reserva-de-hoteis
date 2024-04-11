package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.ReservaResponseDTO;
import com.andre.ReservaDeHotel.service.interfaces.IReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reserva")
@RequiredArgsConstructor
public class ReservaController {

  private final IReservaService reservaService;

  @PostMapping
  public ResponseEntity<ReservaResponseDTO> insert(@RequestBody ReservaRequestDTO dto) {
    ReservaResponseDTO dtoResponse = reservaService.insert(dto);
    return ResponseEntity.ok(dtoResponse);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ReservaResponseDTO> findById(@PathVariable Long id) {
    ReservaResponseDTO dto = reservaService.findById(id);
    return ResponseEntity.ok(dto);
  }

  @GetMapping
  public ResponseEntity<List<ReservaResponseDTO>> findAll(){
    return ResponseEntity.ok(reservaService.findAll());
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    reservaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ReservaRequestDTO> update(@RequestBody ReservaRequestDTO dto, @PathVariable Long id) {
    return ResponseEntity.ok(reservaService.update(dto, id));
  }


}
