package com.evaluacion2.eva2.Controlador;

import com.evaluacion2.eva2.Modelo.Variante;
import com.evaluacion2.eva2.Servicio.CatalogoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//controla variantes
@RestController
@RequestMapping("/api/variantes")
public class VarianteControlador {

    @Autowired
    private CatalogoServicio catalogoServicio;

    @PostMapping
    public ResponseEntity<Variante> crearVariante(@RequestBody Variante variante) {
        Variante nuevaVariante = catalogoServicio.saveVariante(variante);
        return new ResponseEntity<>(nuevaVariante, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Variante>> getAllVariantes() {
        List<Variante> variantes = catalogoServicio.findAllVariantes();
        return ResponseEntity.ok(variantes);
    }
}