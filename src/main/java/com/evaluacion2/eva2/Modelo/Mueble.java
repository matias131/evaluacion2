package com.evaluacion2.eva2.Modelo;

import jakarta.persistence.*;


@Entity
public class Mueble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMueble;

    private String nombreMueble;
    private String tipo;
    private double precioBase;
    private int stock;

    @Enumerated(EnumType.STRING)
    private EstadoMueble estado;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    private String material;

    public Mueble() {}

    public Long getIdMueble() { return idMueble; }
    public void setIdMueble(Long idMueble) { this.idMueble = idMueble; }
    public String getNombreMueble() { return nombreMueble; }
    public void setNombreMueble(String nombreMueble) { this.nombreMueble = nombreMueble; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public EstadoMueble getEstado() { return estado; }
    public void setEstado(EstadoMueble estado) { this.estado = estado; }
    public Tamano getTamano() { return tamano; }
    public void setTamano(Tamano tamano) { this.tamano = tamano; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
}