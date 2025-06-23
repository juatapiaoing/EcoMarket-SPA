package cl.duocucjuancarlos.ecomarketspa.Service.impl;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Movimiento;
import cl.duocucjuancarlos.ecomarketspa.Repository.MovimientoRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    private MovimientoResponse toResponse(Movimiento mov) {
        MovimientoResponse res = new MovimientoResponse();
        res.setId(mov.getId());
        res.setTipoMovimiento(mov.getTipoMovimiento());
        res.setCantidad(mov.getCantidad());
        res.setProveedor(mov.getProveedor());
        res.setUbicacion(mov.getUbicacion());
        return res;
    }

    @Override
    public MovimientoResponse createMovimiento(MovimientoRequest request) {
        Movimiento mov = new Movimiento();
        mov.setTipoMovimiento(request.getTipoMovimiento());
        mov.setCantidad(request.getCantidad());
        mov.setProveedor(request.getProveedor());
        mov.setUbicacion(request.getUbicacion());
        return toResponse(movimientoRepository.save(mov));
    }

    @Override
    public MovimientoResponse getMovimientoById(Integer id) {
        Movimiento mov = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        return toResponse(mov);
    }

    @Override
    public List<MovimientoResponse> getAllMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoResponse updateMovimiento(Integer id, MovimientoRequest request) {
        Movimiento mov = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        mov.setTipoMovimiento(request.getTipoMovimiento());
        mov.setCantidad(request.getCantidad());
        mov.setProveedor(request.getProveedor());
        mov.setUbicacion(request.getUbicacion());
        return toResponse(movimientoRepository.save(mov));
    }

    @Override
    public void deleteMovimiento(Integer id) {
        movimientoRepository.deleteById(id);
    }
}