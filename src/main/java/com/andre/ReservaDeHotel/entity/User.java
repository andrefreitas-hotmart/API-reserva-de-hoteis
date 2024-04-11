package com.andre.ReservaDeHotel.entity;

import com.andre.ReservaDeHotel.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  private String email;
  private String password;

  @OneToMany(mappedBy = "user")
  private List<Reserva> reservas;

  public static void copyDtoToEntity(UserDTO dto, User entity){
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
  }


}
