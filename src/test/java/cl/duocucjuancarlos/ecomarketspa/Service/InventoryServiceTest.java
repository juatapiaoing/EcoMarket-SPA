package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Model.Inventory;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    Inventory testInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testInventory = new Inventory(1, "Shampoo", "bio", 10, 1000);
    }

    @Test
    void getAllInventory_returnsList() {
        when(inventoryRepository.findAll()).thenReturn(List.of(testInventory));
        List<InventoryResponse> result = inventoryService.getAllInventory();
        assertEquals(1, result.size());
        assertEquals("Shampoo", result.get(0).getName());
    }

    @Test
    void getAllInventory_emptyList() {
        when(inventoryRepository.findAll()).thenReturn(Collections.emptyList());
        List<InventoryResponse> result = inventoryService.getAllInventory();
        assertTrue(result.isEmpty());
    }

    @Test
    void getInventoryById_found() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(testInventory));
        InventoryResponse result = inventoryService.getInventoryById(1);
        assertNotNull(result);
        assertEquals("Shampoo", result.getName());
    }

    @Test
    void getInventoryById_notFound() {
        when(inventoryRepository.findById(99)).thenReturn(Optional.empty());
        InventoryResponse result = inventoryService.getInventoryById(99);
        assertNull(result);
    }

    @Test
    void addProduct_success() {
        InventoryRequest req = new InventoryRequest("Jabón", "natural", 20, 1500);
        Inventory expected = new Inventory(2, req.getName(), req.getDescription(), req.getQuantity(), req.getPrice());
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(expected);

        InventoryResponse result = inventoryService.addProduct(req);

        assertNotNull(result);
        assertEquals("Jabón", result.getName());
        assertEquals(20, result.getQuantity());

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(captor.capture());
        Inventory saved = captor.getValue();
        assertEquals("Jabón", saved.getName());
        assertEquals(1500, saved.getPrice());
    }

    @Test
    void addProduct_nullRequest() {
        InventoryResponse result = inventoryService.addProduct(null);
        assertNull(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void updateProduct_success() {
        InventoryRequest req = new InventoryRequest("Acondicionador", "vegano", 5, 2500);
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(testInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(i -> i.getArgument(0));

        InventoryResponse result = inventoryService.updateProduct(1, req);

        assertNotNull(result);
        assertEquals("Acondicionador", result.getName());
        assertEquals(2500, result.getPrice());

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(captor.capture());
        Inventory updated = captor.getValue();
        assertEquals("Acondicionador", updated.getName());
        assertEquals("vegano", updated.getDescription());
    }

    @Test
    void updateProduct_notFound() {
        InventoryRequest req = new InventoryRequest("Acondicionador", "vegano", 5, 2500);
        when(inventoryRepository.findById(55)).thenReturn(Optional.empty());
        InventoryResponse result = inventoryService.updateProduct(55, req);
        assertNull(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void updateProduct_nullRequest() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(testInventory));
        InventoryResponse result = inventoryService.updateProduct(1, null);
        assertNull(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void deleteProduct_success() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(testInventory));
        InventoryResponse result = inventoryService.deleteProduct(1);
        assertNotNull(result);
        verify(inventoryRepository).delete(testInventory);
    }

    @Test
    void deleteProduct_notFound() {
        when(inventoryRepository.findById(77)).thenReturn(Optional.empty());
        InventoryResponse result = inventoryService.deleteProduct(77);
        assertNull(result);
        verify(inventoryRepository, never()).delete(any());
    }
}