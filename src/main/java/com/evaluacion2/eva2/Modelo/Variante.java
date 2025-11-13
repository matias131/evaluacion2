package com.evaluacion2.eva2.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Variante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVariante;
    private String nombre;
    private String descripcion;
    private double costoAdicional;

    public Variante() {}

    public Long getIdVariante() { return idVariante; }
    public void setIdVariante(Long idVariante) { this.idVariante = idVariante; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getCostoAdicional() { return costoAdicional; }
    public void setCostoAdicional(double costoAdicional) { this.costoAdicional = costoAdicional; }
}
