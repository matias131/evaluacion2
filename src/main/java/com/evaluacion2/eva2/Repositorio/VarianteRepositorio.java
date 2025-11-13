package com.evaluacion2.eva2.Repositorio;

import com.evaluacion2.eva2.Modelo.Variante;

import org.springframework.data.jpa.repository.JpaRepository;

//maneja las operaciones CRUD para las variantes de muebles, lo que
//permite modificar el precio del mueble
public interface VarianteRepositorio extends JpaRepository<Variante, Long> {
}
