package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaController {

  @Autowired
  private ReservaService reservaService;

  @PostMapping
  public ResponseEntity<ReservaDTO> insert(@RequestBody ReservaDTO dto) {
    try {
      dto = reservaService.insert(dto);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return ResponseEntity.ok(dto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ReservaDTO> findById(@PathVariable Long id) {
    ReservaDTO dto = reservaService.findById(id);
    return ResponseEntity.ok(dto);
  }

  @GetMapping
  public ResponseEntity<List<ReservaDTO>> findAll(){
    return ResponseEntity.ok(reservaService.findAll());
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    reservaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ReservaDTO> update(@RequestBody ReservaDTO dto, @PathVariable Long id) {
    return ResponseEntity.ok(reservaService.update(dto, id));
  }


}
