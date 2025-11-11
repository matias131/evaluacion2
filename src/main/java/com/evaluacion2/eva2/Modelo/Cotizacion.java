package com.evaluacion2.eva2.Modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCotizacion;

    private LocalDateTime fechaCotizacion = LocalDateTime.now();

    private boolean esVenta = false;

    private double totalCotizacion;


    @JsonManagedReference
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCotizacion> detalles;

    public Cotizacion() {}

    public Long getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(Long idCotizacion) { this.idCotizacion = idCotizacion; }
    public LocalDateTime getFechaCotizacion() { return fechaCotizacion; }
    public void setFechaCotizacion(LocalDateTime fechaCotizacion) { this.fechaCotizacion = fechaCotizacion; }
    public boolean isEsVenta() { return esVenta; }
    public void setEsVenta(boolean esVenta) { this.esVenta = esVenta; }
    public double getTotalCotizacion() { return totalCotizacion; }
    public void setTotalCotizacion(double totalCotizacion) { this.totalCotizacion = totalCotizacion; }

    public List<DetalleCotizacion> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles = detalles;
        if (detalles != null) {
            detalles.forEach(d -> d.setCotizacion(this));
        }
    }
}