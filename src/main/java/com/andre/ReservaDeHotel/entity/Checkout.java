package com.andre.ReservaDeHotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_checkout")
public class Checkout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "reserva_id",referencedColumnName = "id")
  private Reserva reserva;
  private Double multa;
  private Double precoTotal;
  private Long diasPermanencia;
}
