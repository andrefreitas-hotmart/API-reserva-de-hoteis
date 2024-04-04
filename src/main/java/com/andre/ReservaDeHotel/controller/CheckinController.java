package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.CheckinDTO;
import com.andre.ReservaDeHotel.entity.Checkin;
import com.andre.ReservaDeHotel.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/checkin")
public class CheckinController {

  @Autowired
  private CheckinService checkinService;

  @PostMapping(value = "/{id}")
  public ResponseEntity<CheckinDTO> checkin(@PathVariable Long id) {

    return ResponseEntity.ok(checkinService.checkin(id));

  }


}
