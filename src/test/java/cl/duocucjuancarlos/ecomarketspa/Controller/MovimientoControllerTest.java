package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {

    @Mock
    private MovimientoService movimientoService;
    @InjectMocks
    private MovimientoController movimientoController;

    private MovimientoRequest movimientoRequest;
    private MovimientoResponse movimientoResponse;

    @BeforeEach
    void setUp() {
        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setTipoMovimiento("ENTRADA");
        movimientoRequest.setCantidad(50);

        movimientoResponse = new MovimientoResponse();
        movimientoResponse.setId(1);
        movimientoResponse.setTipoMovimiento("ENTRADA");
    }

    @Test
    void create_success() {
        given(movimientoService.createMovimiento(any(MovimientoRequest.class))).willReturn(movimientoResponse);
        ResponseEntity<?> response = movimientoController.create(movimientoRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void create_badRequest_whenTypeIsEmpty() {
        movimientoRequest.setTipoMovimiento("");
        ResponseEntity<?> response = movimientoController.create(movimientoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_success() {
        given(movimientoService.getMovimientoById(1)).willReturn(movimientoResponse);
        ResponseEntity<?> response = movimientoController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getById_notFound() {
        given(movimientoService.getMovimientoById(99)).willReturn(null);
        ResponseEntity<?> response = movimientoController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = movimientoController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(movimientoService.getAllMovimientos()).willReturn(Collections.singletonList(movimientoResponse));
        ResponseEntity<List<MovimientoResponse>> response = movimientoController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void update_success() {
        given(movimientoService.updateMovimiento(eq(1), any(MovimientoRequest.class))).willReturn(movimientoResponse);
        ResponseEntity<?> response = movimientoController.update(1, movimientoRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = movimientoController.update(0, movimientoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_throwsException_whenNotFound() {
        given(movimientoService.updateMovimiento(eq(99), any(MovimientoRequest.class))).willThrow(new RuntimeException("No encontrado"));
        assertThrows(RuntimeException.class, () -> movimientoController.update(99, movimientoRequest));
    }

    @Test
    void delete_success() {
        doNothing().when(movimientoService).deleteMovimiento(1);
        ResponseEntity<?> response = movimientoController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(movimientoService).deleteMovimiento(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = movimientoController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(movimientoService).deleteMovimiento(99);
        assertThrows(RuntimeException.class, () -> movimientoController.delete(99));
    }
}