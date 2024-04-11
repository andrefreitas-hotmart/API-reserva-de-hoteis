package com.andre.ReservaDeHotel.service.interfaces;

import com.andre.ReservaDeHotel.DTO.UserDTO;

import java.util.List;

public interface IUserService {
  UserDTO insert(UserDTO dto);
  UserDTO findById(Long id);
  List<UserDTO> findAll();
  UserDTO update(UserDTO dto, Long id);
  void delete(Long id);
}
