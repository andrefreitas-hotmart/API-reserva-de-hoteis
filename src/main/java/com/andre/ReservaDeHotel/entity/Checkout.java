package com.andre.ReservaDeHotel.entity;

import com.andre.ReservaDeHotel.DTO.response.CheckoutResponseDTO;
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

  public static void copyDtoToEntity(Checkout checkout, CheckoutResponseDTO dto, Reserva reserva) {
    checkout.setReserva(reserva);
    checkout.setMulta(dto.getMulta());
    checkout.setDiasPermanencia(dto.getDiasPermanencia());
    checkout.setPrecoTotal(dto.getPrecoTotal());
  }

}
