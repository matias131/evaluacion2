package com.evaluacion2.eva2; // <-- Paquete Raíz

import com.evaluacion2.eva2.Modelo.*;
import com.evaluacion2.eva2.Repositorio.CotizacionRepositorio;
import com.evaluacion2.eva2.Servicio.CatalogoServicio;
import com.evaluacion2.eva2.Servicio.VentaServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServicioTest {

    @Mock
    private CotizacionRepositorio cotizacionRepository;

    @Mock
    private CatalogoServicio catalogoService;

    @Spy
    @InjectMocks
    private VentaServicio ventaService;

    @Test
    void testCrearCotizacion_CalculoDePrecioCorrecto() {
        Mueble mesa = new Mueble();
        mesa.setIdMueble(1L);
        mesa.setPrecioBase(100.0);

        Variante barniz_JSON = new Variante();
        barniz_JSON.setIdVariante(1L);

        Variante barniz_BD = new Variante();
        barniz_BD.setIdVariante(1L);
        barniz_BD.setCostoAdicional(20.0);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mesa);
        detalle.setCantidad(2);
        detalle.setVariantes(Arrays.asList(barniz_JSON));

        Cotizacion cotizacionEntrada = new Cotizacion();
        cotizacionEntrada.setDetalles(Arrays.asList(detalle));

        when(catalogoService.findMuebleById(1L)).thenReturn(mesa);
        when(catalogoService.findVarianteById(1L)).thenReturn(barniz_BD);

        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacionEntrada);

        Cotizacion resultado = ventaService.crearCotizacion(cotizacionEntrada);

        assertEquals(240.0, resultado.getTotalCotizacion(), 0.01, "El cálculo de precio con variantes debe ser correcto.");
    }

    @Test
    void testConfirmarVenta_StockSuficiente_VentaExitosa() {
        // ARRANGE
        Mueble silla = new Mueble();
        silla.setIdMueble(2L);
        silla.setStock(10);
        silla.setEstado(EstadoMueble.ACTIVO);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(silla);
        detalle.setCantidad(3);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(100L);
        cotizacion.setDetalles(Arrays.asList(detalle));

        when(cotizacionRepository.findById(100L)).thenReturn(Optional.of(cotizacion));

        when(catalogoService.findMuebleById(2L)).thenReturn(silla);

        when(catalogoService.saveMueble(any(Mueble.class))).thenReturn(silla);

        ventaService.confirmarVenta(100L);

        verify(catalogoService, times(1)).saveMueble(argThat(m -> m.getStock() == 7));
        verify(cotizacionRepository, times(1)).save(argThat(c -> c.isEsVenta()));
    }

    @Test
    void testConfirmarVenta_StockInsuficiente_LanzaExcepcion() {
        Mueble estante = new Mueble();
        estante.setIdMueble(3L);
        estante.setStock(1);
        estante.setEstado(EstadoMueble.ACTIVO);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(estante);
        detalle.setCantidad(5);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(200L);
        cotizacion.setDetalles(Arrays.asList(detalle));

        when(cotizacionRepository.findById(200L)).thenReturn(Optional.of(cotizacion));

        when(catalogoService.findMuebleById(3L)).thenReturn(estante);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.confirmarVenta(200L);
        });

        assertTrue(exception.getMessage().contains("stock insuficiente"),
                "El mensaje de error debe contener 'stock insuficiente'.");

        verify(catalogoService, never()).saveMueble(any(Mueble.class));
        verify(cotizacionRepository, never()).save(any(Cotizacion.class));
    }
}