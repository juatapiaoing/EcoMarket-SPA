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

                @GetMapping
                public ResponseEntity<List<InventoryResponse>> getAllInventory() {
                    return ResponseEntity.ok(inventoryService.getAllInventory());
                }

                @GetMapping("/{id}")
                public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable int id) {
                    InventoryResponse inv = inventoryService.getInventoryById(id);
                    if (inv == null) return ResponseEntity.notFound().build();
                    return ResponseEntity.ok(inv);
                }

                @PostMapping
                public ResponseEntity<InventoryResponse> addProduct(@RequestBody InventoryRequest inventoryRequest) {
                    InventoryResponse inv = inventoryService.addProduct(inventoryRequest);
                    if (inv == null) return ResponseEntity.badRequest().build();
                    return ResponseEntity.status(201).body(inv);
                }

                @PutMapping("/{id}")
                public ResponseEntity<InventoryResponse> updateProduct(@PathVariable int id, @RequestBody InventoryRequest inventoryRequest) {
                    InventoryResponse inv = inventoryService.updateProduct(id, inventoryRequest);
                    if (inv == null) return ResponseEntity.notFound().build();
                    return ResponseEntity.ok(inv);
                }

                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
                    InventoryResponse inv = inventoryService.deleteProduct(id);
                    if (inv == null) return ResponseEntity.notFound().build();
                    return ResponseEntity.noContent().build();
                }
//-----------------------------------------------------------------------------------------------

}//FIN CODIGO
