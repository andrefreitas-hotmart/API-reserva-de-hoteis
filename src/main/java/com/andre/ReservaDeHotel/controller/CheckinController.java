package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.CheckinDTO;
import com.andre.ReservaDeHotel.service.interfaces.ICheckinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/checkin")
@RequiredArgsConstructor
public class CheckinController {

  private final ICheckinService checkinService;

  @PostMapping(value = "/{id}")
  public ResponseEntity<CheckinDTO> checkin(@PathVariable Long id) {

    return ResponseEntity.ok(checkinService.checkin(id));

  }
}
