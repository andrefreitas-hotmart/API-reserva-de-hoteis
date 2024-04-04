package com.andre.ReservaDeHotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "/hello")
  public String rotaTeste(){
    return "Hello World!";
  }

}
