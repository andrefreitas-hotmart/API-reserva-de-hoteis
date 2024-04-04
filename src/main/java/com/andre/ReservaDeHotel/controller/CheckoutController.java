package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.CheckoutRequestDTO;
import com.andre.ReservaDeHotel.DTO.response.CheckoutResponseDTO;
import com.andre.ReservaDeHotel.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/checkout")
public class CheckoutController {

  @Autowired
  private CheckoutService checkoutService;

  @PostMapping(value = "/{reservaId}")
  public ResponseEntity<CheckoutResponseDTO> checkout(@PathVariable Long reservaId,
                                                      @RequestBody CheckoutRequestDTO checkoutRequestDTO) throws Exception {
    return ResponseEntity.ok(checkoutService.checkout(reservaId, checkoutRequestDTO));
  }


}
