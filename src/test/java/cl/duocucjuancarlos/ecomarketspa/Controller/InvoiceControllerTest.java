package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    InvoiceResponse testInvoice;

    @BeforeEach
    void setUp() {
        testInvoice = new InvoiceResponse(
                1,
                "1-9",
                "Juan Tapia",
                Arrays.asList("Shampoo", "Acondicionador"),
                Arrays.asList(1000, 2000),
                3000
        );
    }

    @Test
    void getAllInvoices_returnsList() throws Exception {
        when(invoiceService.getAllInvoices()).thenReturn(List.of(testInvoice));
        mockMvc.perform(get("/api/v1/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan Tapia"))
                .andExpect(jsonPath("$[0].products[0]").value("Shampoo"));
    }

    @Test
    void getInvoiceById_found() throws Exception {
        when(invoiceService.getInvoiceById(1)).thenReturn(testInvoice);
        mockMvc.perform(get("/api/v1/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Tapia"))
                .andExpect(jsonPath("$.products[1]").value("Acondicionador"));
    }

    @Test
    void getInvoiceById_notFound() throws Exception {
        when(invoiceService.getInvoiceById(99)).thenReturn(null);
        mockMvc.perform(get("/api/v1/invoices/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addInvoice_success() throws Exception {
        InvoiceRequest req = new InvoiceRequest();
        req.setOrderId(1);
        when(invoiceService.addInvoice(any(InvoiceRequest.class))).thenReturn(testInvoice);

        mockMvc.perform(post("/api/v1/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Juan Tapia"))
                .andExpect(jsonPath("$.total").value(3000));
    }

    @Test
    void addInvoice_badRequest() throws Exception {
        when(invoiceService.addInvoice(any(InvoiceRequest.class))).thenReturn(null);
        mockMvc.perform(post("/api/v1/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new InvoiceRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteInvoice_success() throws Exception {
        when(invoiceService.deleteInvoice(1)).thenReturn(testInvoice);
        mockMvc.perform(delete("/api/v1/invoices/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInvoice_notFound() throws Exception {
        when(invoiceService.deleteInvoice(99)).thenReturn(null);
        mockMvc.perform(delete("/api/v1/invoices/99"))
                .andExpect(status().isNotFound());
    }
}