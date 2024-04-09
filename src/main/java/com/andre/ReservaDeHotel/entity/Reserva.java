package com.andre.ReservaDeHotel.entity;

import com.andre.ReservaDeHotel.entity.enums.StatusReserva;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "quarto_id",referencedColumnName = "id")
  private Quarto quarto;

  private TipoQuarto tipoQuartoEscolhido;
  private Double precoMaximoQuarto;


  // Na hora de fazer o checkin eu seto o quarto na reserva?
}
