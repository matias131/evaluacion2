package com.evaluacion2.eva2.Modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class DetalleCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "mueble_id", nullable = false)
    private Mueble mueble;

    @ManyToMany
    @JoinTable(
            name = "detalle_variante",
            joinColumns = @JoinColumn(name = "detalle_id"),
            inverseJoinColumns = @JoinColumn(name = "variante_id")
    )
    private List<Variante> variantes;

    private int cantidad;
    private double precioUnitarioCalculado;
    private double subtotal;

    public DetalleCotizacion() {}

    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }
    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }
    public Mueble getMueble() { return mueble; }
    public void setMueble(Mueble mueble) { this.mueble = mueble; }
    public List<Variante> getVariantes() { return variantes; }
    public void setVariantes(List<Variante> variantes) { this.variantes = variantes; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitarioCalculado() { return precioUnitarioCalculado; }
    public void setPrecioUnitarioCalculado(double precioUnitarioCalculado) { this.precioUnitarioCalculado = precioUnitarioCalculado; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
