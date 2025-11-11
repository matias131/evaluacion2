package com.evaluacion2.eva2.Servicio;

import com.evaluacion2.eva2.Modelo.*;
import com.evaluacion2.eva2.Repositorio.CotizacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaServicio {

    @Autowired
    private CotizacionRepositorio cotizacionRepository;

    @Autowired
    private CatalogoServicio catalogoService;
    public List<Cotizacion> findAll() {
        return cotizacionRepository.findAll();
    }

    @Transactional
    public Cotizacion crearCotizacion(Cotizacion cotizacion) {
        double total = 0.0;

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {

            Mueble mueble = catalogoService.findMuebleById(detalle.getMueble().getIdMueble());
            detalle.setMueble(mueble);

            List<Variante> variantesPersistidas = new ArrayList<>();
            if (detalle.getVariantes() != null && !detalle.getVariantes().isEmpty()) {
                for (Variante v : detalle.getVariantes()) {
                    Variante variantePersistida = catalogoService.findVarianteById(v.getIdVariante());
                    variantesPersistidas.add(variantePersistida);
                }
            }
            detalle.setVariantes(variantesPersistidas);

            double precioCalculado = calcularPrecioTotalDetalle(
                    detalle.getMueble(),
                    detalle.getVariantes(),
                    detalle.getCantidad()
            );

            detalle.setPrecioUnitarioCalculado(precioCalculado / detalle.getCantidad());
            detalle.setSubtotal(precioCalculado);
            total += precioCalculado;
        }

        cotizacion.setTotalCotizacion(total);
        return cotizacionRepository.save(cotizacion);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Cotizacion confirmarVenta(Long cotizacionId) {
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        if (cotizacion.isEsVenta()) {
            throw new RuntimeException("Esta cotización ya fue confirmada como venta.");
        }

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            Mueble mueble = catalogoService.findMuebleById(detalle.getMueble().getIdMueble());
            if (mueble.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("stock insuficiente para el mueble: " + mueble.getNombreMueble());
            }

            mueble.setStock(mueble.getStock() - detalle.getCantidad());
            catalogoService.saveMueble(mueble);
        }

        cotizacion.setEsVenta(true);
        return cotizacionRepository.save(cotizacion);
    }


    private double calcularPrecioTotalDetalle(Mueble mueble, List<Variante> variantes, int cantidad) {
        double precioUnitario = mueble.getPrecioBase();

        if (variantes != null) {
            double costoTotalVariantes = variantes.stream()
                    .mapToDouble(Variante::getCostoAdicional)
                    .sum();
            precioUnitario += costoTotalVariantes;
        }

        return precioUnitario * cantidad;
    }
}