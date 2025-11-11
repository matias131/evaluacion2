package com.evaluacion2.eva2; // <-- Paquete Raíz

import com.evaluacion2.eva2.Modelo.EstadoMueble;
import com.evaluacion2.eva2.Modelo.Mueble;
import com.evaluacion2.eva2.Repositorio.MuebleRepositorio;
import com.evaluacion2.eva2.Repositorio.VarianteRepositorio;
import com.evaluacion2.eva2.Servicio.CatalogoServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogoServicioTest {

    @Mock
    private MuebleRepositorio muebleRepository;

    @InjectMocks
    private CatalogoServicio catalogoService;

    @Test
    void testDesactivarMueble_CambiarEstadoAInactivo() {
        Long id = 1L;
        Mueble mueble = new Mueble();
        mueble.setIdMueble(id);
        mueble.setEstado(EstadoMueble.ACTIVO);

        when(muebleRepository.findById(id)).thenReturn(Optional.of(mueble));
        when(muebleRepository.save(any(Mueble.class))).thenReturn(mueble);

        Mueble resultado = catalogoService.desactivarMueble(id);

        assertEquals(EstadoMueble.INACTIVO, resultado.getEstado(), "El estado debe cambiar a INACTIVO.");
        verify(muebleRepository, times(1)).save(mueble);
    }

    @Test
    void testFindMuebleById_NotFound_LanzaExcepcion() {
        Long idInexistente = 99L;
        when(muebleRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            catalogoService.findMuebleById(idInexistente);
        }, "Debe lanzar una excepción si el mueble no existe.");
    }
}