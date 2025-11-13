package com.evaluacion2.eva2.Controlador;

import com.evaluacion2.eva2.Modelo.Cotizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.evaluacion2.eva2.Servicio.VentaServicio;


//Controlador para manejar las cotizaciones y ventas desde su inicio hasta el final
@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionesControlador {

    @Autowired
    private VentaServicio ventaServicio;

    //Crea una nueva cotización
    @PostMapping
    public ResponseEntity<?> crearCotizacion(@RequestBody Cotizacion cotizacion) {
        try {
            Cotizacion nuevaCotizacion = ventaServicio.crearCotizacion(cotizacion);
            return new ResponseEntity<>(nuevaCotizacion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cotizacion>> getAllCotizaciones() {
        return ResponseEntity.ok(ventaServicio.findAll());
    }

    //Confirma la venta
    @PutMapping("/{id}/confirmarVenta")
    public ResponseEntity<?> confirmarVenta(@PathVariable Long id) {
        try {
            Cotizacion ventaConfirmada = ventaServicio.confirmarVenta(id);
            return ResponseEntity.ok(ventaConfirmada);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("stock insuficiente")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}