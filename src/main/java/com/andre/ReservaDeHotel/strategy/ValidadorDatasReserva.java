package com.andre.ReservaDeHotel.strategy;

import com.andre.ReservaDeHotel.DTO.ReservaRequestDTO;
import com.andre.ReservaDeHotel.strategy.interfaces.ValidacaoReservaStrategy;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@Service
public class ValidadorDatasReserva {

  public void dataFinalDepoisReserva(ValidacaoReservaStrategy strategy, ReservaRequestDTO dto, LocalDate data) {
    strategy.validaDataReserva(dto, data);
  }

  public void dataFinalDepoisDeHoje(ValidacaoReservaStrategy strategy, ReservaRequestDTO dto, LocalDate data) {
    strategy.validaDataReserva(dto, data);
  }

  public void diaDaReservaDepoisDeHoje (ValidacaoReservaStrategy strategy, ReservaRequestDTO dto, LocalDate data) {
    strategy.validaDataReserva(dto, data);
  }

}
