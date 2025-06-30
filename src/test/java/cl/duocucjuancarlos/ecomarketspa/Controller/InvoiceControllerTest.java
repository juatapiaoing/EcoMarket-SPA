package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
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
class InvoiceControllerTest {
    @Mock
    private InvoiceService invoiceService;
    @InjectMocks
    private InvoiceController invoiceController;

    private InvoiceRequest invoiceRequest;
    private InvoiceResponse invoiceResponse;

    @BeforeEach
    void setUp() {
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setOrdenId(1);
        invoiceRequest.setUsuarioId(1);
        invoiceRequest.setTotalPedido(15000);

        invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(1);
        invoiceResponse.setTotalPedido(15000);
    }

    @Test
    void create_success() {
        given(invoiceService.createInvoice(any(InvoiceRequest.class))).willReturn(invoiceResponse);
        ResponseEntity<?> response = invoiceController.create(invoiceRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(invoiceResponse, response.getBody());
    }

    @Test
    void create_badRequest_whenTotalIsNull() {
        invoiceRequest.setTotalPedido(null);
        ResponseEntity<?> response = invoiceController.create(invoiceRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_success() {
        given(invoiceService.getInvoiceById(1)).willReturn(invoiceResponse);
        ResponseEntity<?> response = invoiceController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getById_notFound() {
        given(invoiceService.getInvoiceById(99)).willReturn(null);
        ResponseEntity<?> response = invoiceController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = invoiceController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(invoiceService.getAllInvoices()).willReturn(Collections.singletonList(invoiceResponse));
        ResponseEntity<List<InvoiceResponse>> response = invoiceController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void update_success() {
        given(invoiceService.updateInvoice(eq(1), any(InvoiceRequest.class))).willReturn(invoiceResponse);
        ResponseEntity<?> response = invoiceController.update(1, invoiceRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = invoiceController.update(0, invoiceRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_throwsException_whenNotFound() {
        given(invoiceService.updateInvoice(eq(99), any(InvoiceRequest.class))).willThrow(new RuntimeException("No encontrado"));
        assertThrows(RuntimeException.class, () -> invoiceController.update(99, invoiceRequest));
    }

    @Test
    void delete_success() {
        doNothing().when(invoiceService).deleteInvoice(1);
        ResponseEntity<?> response = invoiceController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(invoiceService).deleteInvoice(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = invoiceController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(invoiceService).deleteInvoice(99);
        assertThrows(RuntimeException.class, () -> invoiceController.delete(99));
    }
}