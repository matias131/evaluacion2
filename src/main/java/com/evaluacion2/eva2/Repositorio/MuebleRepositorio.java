package com.evaluacion2.eva2.Repositorio;

import com.evaluacion2.eva2.Modelo.Mueble;
import com.evaluacion2.eva2.Modelo.EstadoMueble;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MuebleRepositorio extends JpaRepository<Mueble, Long> {
    List<Mueble> findByEstado(EstadoMueble estado);
}