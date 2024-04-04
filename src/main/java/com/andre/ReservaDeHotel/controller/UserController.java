package com.andre.ReservaDeHotel.controller;

import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  private UserService userService;


  @PostMapping
  public ResponseEntity<UserDTO> insert(@RequestBody UserDTO dto){
    dto = userService.insert(dto);
    return ResponseEntity.ok(dto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id){
    return ResponseEntity.ok(userService.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll(){
    List<UserDTO> users = userService.findAll();
    return ResponseEntity.ok(users);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto, @PathVariable Long id){
    return ResponseEntity.ok(userService.update(dto, id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
