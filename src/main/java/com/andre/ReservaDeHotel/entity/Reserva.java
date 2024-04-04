package com.andre.ReservaDeHotel.entity;

import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_reserva")
public class Reserva {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  private LocalDate diaDaReserva;
  private LocalDate dataFinalReserva;
  private StatusReserva statusReserva;
  private int precoInicialReserva;

  @ManyToOne
  @JoinColumn(name = "quarto_id")
  private Quarto quarto;


}
