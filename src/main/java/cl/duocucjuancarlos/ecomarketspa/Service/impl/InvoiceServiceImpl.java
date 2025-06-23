package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Invoice;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private InvoiceResponse toResponse(Invoice invoice) {
        InvoiceResponse res = new InvoiceResponse();
        res.setId(invoice.getId());
        res.setUsuarioId(invoice.getUsuario().getId());
        res.setUsuarioNombre(invoice.getUsuario().getNombre());
        res.setOrdenId(invoice.getOrden().getId());
        res.setFechaEmision(invoice.getFechaEmision());
        res.setTotalPedido(invoice.getTotalPedido());
        return res;
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Order orden = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        invoice.setUsuario(usuario);
        invoice.setOrden(orden);
        invoice.setFechaEmision(new Date());
        invoice.setTotalPedido(request.getTotalPedido());
        return toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse getInvoiceById(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return toResponse(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponse updateInvoice(Integer id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Order orden = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        invoice.setUsuario(usuario);
        invoice.setOrden(orden);
        invoice.setTotalPedido(request.getTotalPedido());
        // No actualizamos la fechaEmision aquí
        return toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}