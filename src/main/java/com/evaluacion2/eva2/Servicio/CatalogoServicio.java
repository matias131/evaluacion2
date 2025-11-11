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

@Service
public class CatalogoServicio {

    @Autowired
    private MuebleRepositorio muebleRepository;

    @Autowired
    private VarianteRepositorio varianteRepository;

    @Transactional
    public Mueble saveMueble(Mueble mueble) {
        return muebleRepository.save(mueble);
    }

    public List<Mueble> findAllMuebles() {
        return muebleRepository.findAll();
    }

    public Mueble findMuebleById(Long id) {
        return muebleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mueble no encontrado con ID: " + id));
    }

    @Transactional
    public Mueble activarMueble(Long id) {
        Mueble mueble = findMuebleById(id);
        mueble.setEstado(EstadoMueble.ACTIVO);
        return muebleRepository.save(mueble);
    }

    @Transactional
    public Mueble desactivarMueble(Long id) {
        Mueble mueble = findMuebleById(id);
        mueble.setEstado(EstadoMueble.INACTIVO);
        return muebleRepository.save(mueble);
    }

    @Transactional
    public Variante saveVariante(Variante variante) {
        return varianteRepository.save(variante);
    }

    public List<Variante> findAllVariantes() {
        return varianteRepository.findAll();
    }

    public Variante findVarianteById(Long id) {
        return varianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada con ID: " + id));
    }
}