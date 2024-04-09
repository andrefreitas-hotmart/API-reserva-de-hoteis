package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {

  @Query(nativeQuery = true,value = "SELECT * FROM TB_QUARTO WHERE PRECO_NOITE <= ?1 AND TIPO_QUARTO = ?2")

  Optional<Quarto> findQuartoByDadosDaReserva(Double precoMaximoQuarto, TipoQuarto tipoQuarto);

}
