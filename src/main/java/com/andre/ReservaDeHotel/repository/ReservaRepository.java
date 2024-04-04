package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.DTO.ReservaDTO;
import com.andre.ReservaDeHotel.entity.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "DELETE FROM TB_RESERVA WHERE DIA_DA_RESERVA < ?1" +
                                      " AND STATUS_RESERVA = 0")
  void deletarReservasExpiradas(LocalDate date);

  @Query(nativeQuery = true, value = "SELECT * FROM TB_RESERVA WHERE QUARTO_ID = ?1")
  List<Reserva> findReservasByQuartoId(Long id);

}
