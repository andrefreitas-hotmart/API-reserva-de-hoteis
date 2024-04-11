package com.andre.ReservaDeHotel.service;

import com.andre.ReservaDeHotel.DTO.QuartoDTO;
import com.andre.ReservaDeHotel.entity.Quarto;
import com.andre.ReservaDeHotel.entity.enums.TipoQuarto;
import com.andre.ReservaDeHotel.repository.QuartoRepository;
import com.andre.ReservaDeHotel.service.exceptions.QuartoJaExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuartoServiceTest {

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @InjectMocks
  private QuartoServiceImpl quartoService;
  @Mock
  private QuartoRepository quartoRepository;

  private QuartoDTO quarto;
  private Quarto q;

  @BeforeEach
  void init() {
    quarto = new QuartoDTO(1L, 203, TipoQuarto.duplo,
        "Quarto mais top que existe", 50.0);
    q = new Quarto(1L, 203, TipoQuarto.duplo,
        "Quarto mais top que existe", 50.0);
  }

  @Test
  void testarSeQuartoJaExiste() {

    List<Quarto> quartos = new ArrayList<>();
    quartos.add(q);

    when(quartoRepository.findAll()).thenReturn(quartos);

    assertThrows(QuartoJaExisteException.class, () -> {
      quartoService.validarSeQuartoExiste(quarto);
    });

  }

}
