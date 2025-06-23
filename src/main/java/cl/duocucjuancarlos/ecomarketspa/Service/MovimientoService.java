package cl.duocucjuancarlos.ecomarketspa.Service;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;

import java.util.List;

public interface MovimientoService {
    MovimientoResponse createMovimiento(MovimientoRequest request);
    MovimientoResponse getMovimientoById(Integer id);
    List<MovimientoResponse> getAllMovimientos();
    MovimientoResponse updateMovimiento(Integer id, MovimientoRequest request);
    void deleteMovimiento(Integer id);
}