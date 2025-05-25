package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
            //CONTROLA EL INVENTARIO PUDIENDO :ELIMINAR AÑADIR VER MODIFICAR
@RestController
@RequestMapping("/api/v1/inventory")

public class InventoryController {//INICIO CODIGO
    @Autowired
    private InventoryService inventoryService;
//-----------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getInventory() {
        return ResponseEntity.ok(inventoryService.getInventory());
    }
//-----------------------------------------------------------------------------------------------

    @GetMapping("/{elementNumber}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable int elementNumber) {
        InventoryResponse found = inventoryService.getInventoryById(elementNumber);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }
//-----------------------------------------------------------------------------------------------
    @PostMapping("/add")
     public ResponseEntity<InventoryRequest> addInventory(@RequestBody InventoryRequest inventoryRequest) {
        InventoryRequest found = inventoryService.addInventory(inventoryRequest);
        if (found != null) {
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.notFound().build();
    }
//-----------------------------------------------------------------------------------------------

    @PutMapping("/{elementNumber}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable int elementNumber, @RequestBody InventoryRequest inventoryRequest) {
        InventoryResponse found = inventoryService.updateInventory(elementNumber, inventoryRequest);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }
//-----------------------------------------------------------------------------------------------

    @DeleteMapping("/{elementNumber}")
    public ResponseEntity<InventoryResponse> deleteInventory(@PathVariable int elementNumber) {
        InventoryResponse found = inventoryService.deleteInventory(elementNumber);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }
//-----------------------------------------------------------------------------------------------

}//FIN CODIGO
