package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Movimiento;
import cl.duocucjuancarlos.ecomarketspa.Repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceImplTest {
    @Mock
    private MovimientoRepository movimientoRepository;
    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Movimiento movimiento;
    private MovimientoRequest movimientoRequest;

    @BeforeEach
    void setUp() {
        movimiento = new Movimiento();
        movimiento.setId(1);
        movimiento.setTipoMovimiento("ENTRADA");

        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setTipoMovimiento("ENTRADA");
    }

    @Test
    void createMovimiento() {
        given(movimientoRepository.save(any(Movimiento.class))).willReturn(movimiento);
        MovimientoResponse response = movimientoService.createMovimiento(movimientoRequest);
        assertNotNull(response);
        assertEquals("ENTRADA", response.getTipoMovimiento());
    }

    @Test
    void getMovimientoById_success() {
        given(movimientoRepository.findById(1)).willReturn(Optional.of(movimiento));
        MovimientoResponse response = movimientoService.getMovimientoById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
    }
}