package com.evaluacion2.eva2.Servicio;

import com.evaluacion2.eva2.Modelo.Mueble;
import com.evaluacion2.eva2.Modelo.Variante;
import com.evaluacion2.eva2.Modelo.EstadoMueble;
import com.evaluacion2.eva2.Repositorio.MuebleRepositorio;
import com.evaluacion2.eva2.Repositorio.VarianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


//Servicio para gestionar el catálogo de muebles y variantes asegurando
// la integridad de los datos durante las operaciones sean consistentes
@Service
public class CatalogoServicio {

    @Autowired
    private MuebleRepositorio muebleRepositorio;

    @Autowired
    private VarianteRepositorio varianteRepositorio;

    @Transactional
    public Mueble saveMueble(Mueble mueble) {
        return muebleRepositorio.save(mueble);
    }

    public List<Mueble> findAllMuebles() {
        return muebleRepositorio.findAll();
    }

    public Mueble findMuebleById(Long id) {
        return muebleRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Mueble no encontrado con ID: " + id));
    }

    @Transactional
    public Mueble activarMueble(Long id) {
        Mueble mueble = findMuebleById(id);
        mueble.setEstado(EstadoMueble.ACTIVO);
        return muebleRepositorio.save(mueble);
    }

    @Transactional
    public Mueble desactivarMueble(Long id) {
        Mueble mueble = findMuebleById(id);
        mueble.setEstado(EstadoMueble.INACTIVO);
        return muebleRepositorio.save(mueble);
    }

    @Transactional
    public Variante saveVariante(Variante variante) {
        return varianteRepositorio.save(variante);
    }

    public List<Variante> findAllVariantes() {
        return varianteRepositorio.findAll();
    }

    public Variante findVarianteById(Long id) {
        return varianteRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada con ID: " + id));
    }
}