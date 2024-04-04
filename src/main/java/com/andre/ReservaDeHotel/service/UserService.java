package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public UserDTO insert(UserDTO dto){
    User user = new User();

    copyDtoToEntity(dto, user);
    userRepository.save(user);

    return new UserDTO(user);
  }

  public UserDTO findById(Long id){
    User user = userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Usuario nao encontrado")
    );

    return new UserDTO(user);

  }

  public List<UserDTO> findAll(){
    List<User> users = userRepository.findAll();
    return users.stream().map(UserDTO::new).toList();
  }

  @Transactional
  public UserDTO update(UserDTO dto, Long id){
     User user = userRepository.getReferenceById(id);

     copyDtoToEntity(dto, user);
     user = userRepository.save(user);

     return new UserDTO(user);
  }

  public void delete(Long id){
    userRepository.deleteById(id);
  }

  private void copyDtoToEntity(UserDTO dto, User entity){
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
  }



}
