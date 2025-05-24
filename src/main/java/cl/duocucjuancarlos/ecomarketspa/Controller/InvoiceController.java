package cl.duocucjuancarlos.ecomarketspa.Controller;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        if (service.getAllInvoices() == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(service.getAllInvoices());
    }

    @PostMapping("/newInvoice")
    public ResponseEntity<InvoiceResponse> newInvoice(@RequestBody InvoiceRequest request) {
        InvoiceRequest found = service.addInvoice(request);
        if (found == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }


}
