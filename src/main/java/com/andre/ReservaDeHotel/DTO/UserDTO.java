package com.andre.ReservaDeHotel.DTO;

import com.andre.ReservaDeHotel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String name;
  private String email;
  private String password;

  public UserDTO(User entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.password = entity.getPassword();
  }

}
