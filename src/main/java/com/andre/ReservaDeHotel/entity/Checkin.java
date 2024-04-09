package com.andre.ReservaDeHotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_checkin")
public class Checkin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  @JoinColumn(name = "reserva_id", referencedColumnName = "id")
  private Reserva reserva;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "quarto_id",referencedColumnName = "id")
  private Quarto quarto;
}
