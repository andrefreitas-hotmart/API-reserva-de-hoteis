package com.andre.ReservaDeHotel.repository;

import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import com.andre.ReservaDeHotel.projections.QuartoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {

  @Query(nativeQuery = true,
      value = "SELECT * FROM TB_QUARTO WHERE PRECO_NOITE <= ?1 AND TIPO_QUARTO = ?2")

  Optional<Quarto> findQuartoByDadosDaReserva(Double precoMaximoQuarto, TipoQuarto tipoQuarto);

  @Query(nativeQuery = true,
        value = "SELECT COUNT (*)" +
            " FROM TB_QUARTO as q" +
            " WHERE q.ID NOT IN (" +
            " SELECT r.QUARTO_ID" +
            " FROM TB_RESERVA as r" +
            " WHERE r.PRECO_MAXIMO_QUARTO <= q.PRECO_NOITE" +
            " AND r.TIPO_QUARTO_ESCOLHIDO = q.TIPO_QUARTO" +
            " AND (:dataInicio <= r.DATA_FINAL_RESERVA AND :dataFim >= r.DIA_DA_RESERVA)" +
            ")"
  )
  Long existeQuartoDisponivel(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("dataInicio") LocalDate dataInicio,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("dataFim") LocalDate dataFim);



}
