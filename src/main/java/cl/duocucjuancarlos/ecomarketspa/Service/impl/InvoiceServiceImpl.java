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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

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
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUsuarioId()));
        Order orden = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + request.getOrdenId()));

        Invoice invoice = new Invoice();
        invoice.setUsuario(usuario);
        invoice.setOrden(orden);
        invoice.setFechaEmision(new Date());
        invoice.setTotalPedido(request.getTotalPedido());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return toResponse(savedInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(Integer id) {
        return invoiceRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceResponse updateInvoice(Integer id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));

        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUsuarioId()));
        Order orden = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + request.getOrdenId()));

        invoice.setUsuario(usuario);
        invoice.setOrden(orden);
        invoice.setTotalPedido(request.getTotalPedido());

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return toResponse(updatedInvoice);
    }

    @Override
    @Transactional
    public void deleteInvoice(Integer id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Factura no encontrada con ID: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}