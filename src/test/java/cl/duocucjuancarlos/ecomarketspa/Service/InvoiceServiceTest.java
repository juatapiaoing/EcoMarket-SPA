package cl.duocucjuancarlos.ecomarketspa.Service;

import org.junit.jupiter.api.Test;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Invoice;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Model.Inventory;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllInvoices_returnsList() {
        Invoice inv = new Invoice(1, "1-9", "Juan Tapia", List.of("prod1"), List.of(100), 100);
        when(invoiceRepository.findAll()).thenReturn(List.of(inv));
        List<InvoiceResponse> resp = invoiceService.getAllInvoices();
        assertEquals(1, resp.size());
        assertEquals("Juan Tapia", resp.get(0).getName());
    }

    @Test
    void getInvoiceById_found() {
        Invoice inv = new Invoice(1, "1-9", "Juan Tapia", List.of("prod1"), List.of(100), 100);
        when(invoiceRepository.findById(1)).thenReturn(Optional.of(inv));
        InvoiceResponse resp = invoiceService.getInvoiceById(1);
        assertNotNull(resp);
        assertEquals("Juan Tapia", resp.getName());
    }

    @Test
    void getInvoiceById_notFound() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());
        InvoiceResponse resp = invoiceService.getInvoiceById(1);
        assertNull(resp);
    }

    @Test
    void addInvoice_success() {
        // Setup mocks
        InvoiceRequest req = new InvoiceRequest();
        req.setOrderId(5);

        User user = new User(1, "1-9", "Juan", "Tapia", "mail@mail.com", "1234");
        Order order = new Order(5, user, Arrays.asList(10, 20));
        Inventory prod1 = new Inventory(10, "Shampoo", "desc", 10, 1000);
        Inventory prod2 = new Inventory(20, "Acondicionador", "desc", 5, 2000);

        when(orderRepository.findById(5)).thenReturn(Optional.of(order));
        when(inventoryRepository.findAllById(Arrays.asList(10, 20))).thenReturn(Arrays.asList(prod1, prod2));

        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(i -> {
            Invoice inv = i.getArgument(0);
            inv.setId(44);
            return inv;
        });

        InvoiceResponse resp = invoiceService.addInvoice(req);

        assertNotNull(resp);
        assertEquals("Juan Tapia", resp.getName());
        assertEquals(List.of("Shampoo", "Acondicionador"), resp.getProducts());
        assertEquals(List.of(1000, 2000), resp.getPrices());
        assertEquals(3000, resp.getTotal());
        assertEquals(44, resp.getId());

        verify(invoiceRepository).save(invoiceCaptor.capture());
        Invoice saved = invoiceCaptor.getValue();
        assertEquals("1-9", saved.getRun());
        assertEquals(3000, saved.getTotal());
    }

    @Test
    void addInvoice_nullRequest() {
        assertNull(invoiceService.addInvoice(null));
    }

    @Test
    void addInvoice_nullOrderId() {
        InvoiceRequest req = new InvoiceRequest();
        req.setOrderId(null);
        assertNull(invoiceService.addInvoice(req));
    }

    @Test
    void addInvoice_orderNotFound() {
        InvoiceRequest req = new InvoiceRequest();
        req.setOrderId(5);
        when(orderRepository.findById(5)).thenReturn(Optional.empty());
        assertNull(invoiceService.addInvoice(req));
    }

    @Test
    void deleteInvoice_found() {
        Invoice inv = new Invoice(1, "1-9", "Juan Tapia", List.of("prod1"), List.of(100), 100);
        when(invoiceRepository.findById(1)).thenReturn(Optional.of(inv));
        InvoiceResponse resp = invoiceService.deleteInvoice(1);
        assertNotNull(resp);
        verify(invoiceRepository).delete(inv);
    }

    @Test
    void deleteInvoice_notFound() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(invoiceService.deleteInvoice(1));
        verify(invoiceRepository, never()).delete(any());
    }
}