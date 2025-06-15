package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InventoryService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    InventoryResponse testInventory;

    @BeforeEach
    void setUp() {
        testInventory = new InventoryResponse(1, "Shampoo", "bio", 10, 1000);
    }

    @Test
    void getAllInventory_returnsList() throws Exception {
        when(inventoryService.getAllInventory()).thenReturn(List.of(testInventory));
        mockMvc.perform(get("/api/v1/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Shampoo"));
    }

    @Test
    void getInventoryById_found() throws Exception {
        when(inventoryService.getInventoryById(1)).thenReturn(testInventory);
        mockMvc.perform(get("/api/v1/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shampoo"));
    }

    @Test
    void getInventoryById_notFound() throws Exception {
        when(inventoryService.getInventoryById(99)).thenReturn(null);
        mockMvc.perform(get("/api/v1/inventory/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addProduct_success() throws Exception {
        InventoryRequest req = new InventoryRequest("Jabón", "natural", 20, 1500);
        InventoryResponse saved = new InventoryResponse(2, "Jabón", "natural", 20, 1500);
        when(inventoryService.addProduct(any(InventoryRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jabón"));
    }

    @Test
    void addProduct_badRequest() throws Exception {
        when(inventoryService.addProduct(any(InventoryRequest.class))).thenReturn(null);
        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new InventoryRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_success() throws Exception {
        InventoryRequest req = new InventoryRequest("Acondicionador", "vegano", 5, 2500);
        InventoryResponse updated = new InventoryResponse(1, "Acondicionador", "vegano", 5, 2500);
        when(inventoryService.updateProduct(eq(1), any(InventoryRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Acondicionador"));
    }

    @Test
    void updateProduct_notFound() throws Exception {
        when(inventoryService.updateProduct(eq(99), any(InventoryRequest.class))).thenReturn(null);
        mockMvc.perform(put("/api/v1/inventory/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new InventoryRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_success() throws Exception {
        when(inventoryService.deleteProduct(1)).thenReturn(testInventory);
        mockMvc.perform(delete("/api/v1/inventory/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_notFound() throws Exception {
        when(inventoryService.deleteProduct(99)).thenReturn(null);
        mockMvc.perform(delete("/api/v1/inventory/99"))
                .andExpect(status().isNotFound());
    }
}