package com.evaluacion2.eva2.Repositorio;

import com.evaluacion2.eva2.Modelo.Mueble;
import com.evaluacion2.eva2.Modelo.EstadoMueble;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//se encarga de manejar las operaciones de base de datos relacionadas con los muebles
public interface MuebleRepositorio extends JpaRepository<Mueble, Long> {
    List<Mueble> findByEstado(EstadoMueble estado);
}