package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Movimiento;
import cl.duocucjuancarlos.ecomarketspa.Repository.MovimientoRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.MovimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

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
    @Transactional
    public MovimientoResponse createMovimiento(MovimientoRequest request) {
        Movimiento mov = new Movimiento();
        mov.setTipoMovimiento(request.getTipoMovimiento());
        mov.setCantidad(request.getCantidad());
        mov.setProveedor(request.getProveedor());
        mov.setUbicacion(request.getUbicacion());

        Movimiento savedMovimiento = movimientoRepository.save(mov);
        return toResponse(savedMovimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoResponse getMovimientoById(Integer id) {
        return movimientoRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponse> getAllMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoResponse updateMovimiento(Integer id, MovimientoRequest request) {
        Movimiento mov = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));

        mov.setTipoMovimiento(request.getTipoMovimiento());
        mov.setCantidad(request.getCantidad());
        mov.setProveedor(request.getProveedor());
        mov.setUbicacion(request.getUbicacion());

        Movimiento updatedMovimiento = movimientoRepository.save(mov);
        return toResponse(updatedMovimiento);
    }

    @Override
    @Transactional
    public void deleteMovimiento(Integer id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Movimiento no encontrado con ID: " + id);
        }
        movimientoRepository.deleteById(id);
    }
}