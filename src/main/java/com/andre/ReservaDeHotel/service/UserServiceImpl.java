package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.UserDTO;
import com.andre.ReservaDeHotel.entity.User;
import com.andre.ReservaDeHotel.repository.UserRepository;
import com.andre.ReservaDeHotel.service.exceptions.ResourceNotFoundException;
import com.andre.ReservaDeHotel.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.andre.ReservaDeHotel.entity.User.copyDtoToEntity;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

  private UserRepository userRepository;

  public UserDTO insert(UserDTO dto) {
    User user = new User();

    copyDtoToEntity(dto, user);
    userRepository.save(user);

    return new UserDTO(user);
  }

  public UserDTO findById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Usuario nao encontrado")
    );

    return new UserDTO(user);

  }

  public List<UserDTO> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream().map(UserDTO::new).toList();
  }

  @Transactional
  public UserDTO update(UserDTO dto, Long id) {
     User user = userRepository.getReferenceById(id);

     copyDtoToEntity(dto, user);
     user = userRepository.save(user);

     return new UserDTO(user);
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }

}
