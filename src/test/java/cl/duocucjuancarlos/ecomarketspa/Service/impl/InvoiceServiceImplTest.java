package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Invoice;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    // Mocks: Versiones simuladas de las dependencias
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    // InjectsMocks: Crea una instancia real de InvoiceServiceImpl e inyecta los mocks de arriba
    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    // Objetos que usaremos en nuestros tests
    private User user;
    private Order order;
    private Invoice invoice;
    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() {
        // --- AQUÍ ESTÁ LA CLAVE ---
        // 1. Creamos las entidades simuladas con todos sus datos.
        user = new User();
        user.setId(1);
        user.setNombre("Juan Perez");

        order = new Order();
        order.setId(1);

        // 2. Creamos la entidad principal (Invoice) y le asignamos las dependencias.
        // Si no hacemos setUsuario() y setOrden(), el método toResponse fallará.
        invoice = new Invoice();
        invoice.setId(1);
        invoice.setUsuario(user); // Asignación CRÍTICA
        invoice.setOrden(order);  // Asignación CRÍTICA
        invoice.setTotalPedido(5000);
        invoice.setFechaEmision(new Date());

        // 3. Creamos el objeto de solicitud que simula venir del exterior.
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setUsuarioId(1);
        invoiceRequest.setOrdenId(1);
        invoiceRequest.setTotalPedido(5000);
    }

    @Test
    void createInvoice_success() {
        // Given: Definimos el comportamiento de los mocks
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(orderRepository.findById(1)).willReturn(Optional.of(order));
        given(invoiceRepository.save(any(Invoice.class))).willReturn(invoice);

        // When: Ejecutamos el método a probar
        InvoiceResponse response = invoiceService.createInvoice(invoiceRequest);

        // Then: Verificamos los resultados
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(5000, response.getTotalPedido());
        assertEquals("Juan Perez", response.getUsuarioNombre());
    }

    @Test
    void createInvoice_userNotFound_shouldThrowException() {
        // Given: Simulamos que el usuario no se encuentra
        given(userRepository.findById(99)).willReturn(Optional.empty());
        invoiceRequest.setUsuarioId(99);

        // When & Then: Verificamos que se lanza la excepción esperada
        assertThrows(RuntimeException.class, () -> invoiceService.createInvoice(invoiceRequest));
    }

    @Test
    void getInvoiceById_success() {
        // Given
        given(invoiceRepository.findById(1)).willReturn(Optional.of(invoice));

        // When
        InvoiceResponse response = invoiceService.getInvoiceById(1);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void getInvoiceById_notFound_shouldReturnNull() {
        // Given
        given(invoiceRepository.findById(99)).willReturn(Optional.empty());

        // When
        InvoiceResponse response = invoiceService.getInvoiceById(99);

        // Then: Nuestro método devuelve null si no encuentra nada.
        assertNull(response);
    }

    @Test
    void deleteInvoice_success() {
        // Given
        given(invoiceRepository.existsById(1)).willReturn(true);

        // When & Then
        assertDoesNotThrow(() -> invoiceService.deleteInvoice(1));
        verify(invoiceRepository).deleteById(1); // Verificamos que el método de borrado fue llamado.
    }

    @Test
    void deleteInvoice_notFound_shouldThrowException() {
        // Given
        given(invoiceRepository.existsById(99)).willReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> invoiceService.deleteInvoice(99));
    }
}