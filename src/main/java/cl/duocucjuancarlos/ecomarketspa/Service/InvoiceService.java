package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;

import java.util.List;


public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);
    InvoiceResponse getInvoiceById(Integer id);
    List<InvoiceResponse> getAllInvoices();
    InvoiceResponse updateInvoice(Integer id, InvoiceRequest request);
    void deleteInvoice(Integer id);
}