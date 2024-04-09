package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/quarto")
public class QuartoController {

  @Autowired
  private QuartoService quartoService;


  @PostMapping
  public ResponseEntity<QuartoDTO> insert(@RequestBody QuartoDTO dto){
    dto = quartoService.insert(dto);
    return ResponseEntity.ok(dto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<QuartoDTO> findById(@PathVariable Long id){
    return ResponseEntity.ok(quartoService.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<QuartoDTO>> findAll(){
    List<QuartoDTO> users = quartoService.findAll();
    return ResponseEntity.ok(users);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<QuartoDTO> update(@RequestBody QuartoDTO dto, @PathVariable Long id){
    return ResponseEntity.ok(quartoService.update(dto, id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    quartoService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
