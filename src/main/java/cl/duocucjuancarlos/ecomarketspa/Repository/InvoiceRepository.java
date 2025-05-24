package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceRepository {
    private List<InvoiceResponse> invoiceResponse;

    public InvoiceRepository() {
        invoiceResponse = new ArrayList<>();
    }

    public List<InvoiceResponse> getAllInvoiceResponse() {
        return invoiceResponse;
    }




}
