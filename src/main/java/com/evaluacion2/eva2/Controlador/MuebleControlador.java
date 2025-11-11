package com.evaluacion2.eva2.Controlador;

import com.evaluacion2.eva2.Servicio.CatalogoServicio;
import com.evaluacion2.eva2.Modelo.Mueble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/muebles")
public class MuebleControlador {

    @Autowired
    private CatalogoServicio catalogoServicio;

    @PostMapping
    public ResponseEntity<Mueble> crearMueble(@RequestBody Mueble mueble) {
        Mueble nuevoMueble = catalogoServicio.saveMueble(mueble);
        return new ResponseEntity<>(nuevoMueble, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Mueble>> getAllMuebles() {
        List<Mueble> muebles = catalogoServicio.findAllMuebles();
        return ResponseEntity.ok(muebles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mueble> getMuebleById(@PathVariable Long id) {
        Mueble mueble = catalogoServicio.findMuebleById(id);
        return ResponseEntity.ok(mueble);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mueble> updateMueble(@PathVariable Long id, @RequestBody Mueble muebleDetails) {
        muebleDetails.setIdMueble(id);
        Mueble updatedMueble = catalogoServicio.saveMueble(muebleDetails);
        return ResponseEntity.ok(updatedMueble);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Mueble> desactivarMueble(@PathVariable Long id) {
        Mueble deactivatedMueble = catalogoServicio.desactivarMueble(id);
        return ResponseEntity.ok(deactivatedMueble);
    }
}
