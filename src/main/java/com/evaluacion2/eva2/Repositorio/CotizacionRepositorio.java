package com.evaluacion2.eva2.Repositorio;

import com.evaluacion2.eva2.Modelo.Cotizacion;

import org.springframework.data.jpa.repository.JpaRepository;

//maneja la persistencia de las cotizaciones, lo que muestra el detalle
//con mueble, variante
public interface CotizacionRepositorio extends JpaRepository<Cotizacion, Long> {
}