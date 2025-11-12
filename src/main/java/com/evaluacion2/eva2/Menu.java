package com.evaluacion2.eva2;

import com.evaluacion2.eva2.Modelo.*;
import com.evaluacion2.eva2.Servicio.CatalogoServicio;
import com.evaluacion2.eva2.Servicio.VentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    private CatalogoServicio catalogoService;

    @Autowired
    private VentaServicio ventaService;

    @Autowired
    private ConfigurableApplicationContext context;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();

            int opcion = -1;
            try {
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                }
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("La entrada es inválida.");
                if (scanner.hasNextLine()) scanner.next();
                continue;
            }

            try {
                switch (opcion) {
                    case 1:
                        crearMueble();
                        break;
                    case 2:
                        crearVariante();
                        break;
                    case 3:
                        listarMuebles();
                        break;
                    case 4:
                        desactivarMueble();
                        break;
                    case 5:
                        activarMueble();
                        break;
                    case 6:
                        crearCotizacion();
                        break;
                    case 7:
                        confirmarVenta();
                        break;
                    case 0:
                        continuar = false;
                        context.close();
                        continue;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }

                System.out.println("\n Presiona Enter para poder continuar");
                scanner.nextLine();

            } catch (Exception e) {
                System.err.println("\n ERROR: " + e.getMessage());
                System.out.println("\n Presiona Enter para poder continuar");
                scanner.nextLine();
            }
        }
        System.out.println("Saliendo del menú");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n-- MENÚ --");
        System.out.println("1. Crear Mueble");
        System.out.println("2. Crear Variante");
        System.out.println("3. Listar Muebles");
        System.out.println("4. Desactivar Mueble");
        System.out.println("5. Activar Mueble");
        System.out.println("6. Crear Cotización");
        System.out.println("7. Confirmar Venta");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private void crearMueble() {
        System.out.println("\n-- Crear Mueble --");
        Mueble m = new Mueble();

        System.out.print("Nombre: ");
        m.setNombreMueble(scanner.nextLine());
        System.out.print("Tipo: ");
        m.setTipo(scanner.nextLine());
        System.out.print("Material: ");
        m.setMaterial(scanner.nextLine());
        System.out.print("Precio Base: ");
        m.setPrecioBase(scanner.nextDouble());
        System.out.print("Stock inicial: ");
        m.setStock(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Seleccione el Tamaño:");
        System.out.println("1. GRANDE");
        System.out.println("2. MEDIANO");
        System.out.println("3. PEQUENO");
        System.out.print("Opción de Tamaño (1-3) solo ingresar número: ");
        int tamanoOpcion = scanner.nextInt();
        scanner.nextLine();

        switch (tamanoOpcion) {
            case 1:
                m.setTamano(Tamano.GRANDE);
                break;
            case 3:
                m.setTamano(Tamano.PEQUEÑO);
                break;
            case 2:
            default:
                m.setTamano(Tamano.MEDIANO);
                break;
        }
        m.setEstado(EstadoMueble.ACTIVO);

        Mueble muebleGuardado = catalogoService.saveMueble(m);

        System.out.println(" El mueble fue guardado con el ID: " + muebleGuardado.getIdMueble());
    }

    private void crearVariante() {
        System.out.println("\n-- Crear Variante --");
        Variante v = new Variante();

        System.out.print("Nombre de la variante: ");
        v.setNombre(scanner.nextLine());
        System.out.print("Costo Adicional: ");
        v.setCostoAdicional(scanner.nextDouble());
        scanner.nextLine();

        Variante varianteGuardada = catalogoService.saveVariante(v);
        System.out.println("La Variante guardada con ID: " + varianteGuardada.getIdVariante());
    }

    private void listarMuebles() {
        System.out.println("\n-- Listar Muebles --");
        List<Mueble> muebles = catalogoService.findAllMuebles();
        if (muebles.isEmpty()) {
            System.out.println("No hay muebles en el catálogo.");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-7s %-10s %-10s%n", "ID" , "Nombre" , "Precio" , "Stock" , "Estado" , "Tamaño");
        System.out.println("-------------------------------------------------");
        for (Mueble m : muebles) {
            System.out.printf("%d | %-15s | %-10.2f | %-5d | %-8s | %-10s\n",
                    m.getIdMueble(),
                    m.getNombreMueble(),
                    m.getPrecioBase(),
                    m.getStock(),
                    m.getEstado(),
                    m.getTamano());
        }
    }

    private void desactivarMueble() {
        System.out.println("\n-- Desactivar Mueble --");
        System.out.print("Ingrese el ID del mueble a DESACTIVAR: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Mueble mueble = catalogoService.desactivarMueble(id);
        System.out.println("El Mueble '" + mueble.getNombreMueble() + "' actualizado a estado: " + mueble.getEstado());
    }

    private void activarMueble() {
        System.out.println("\n-- Activar Mueble --");
        System.out.print("Ingrese el ID del mueble a ACTIVAR: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Mueble mueble = catalogoService.activarMueble(id);

        System.out.println("Mueble '" + mueble.getNombreMueble() + "' actualizado a estado: " + mueble.getEstado());
    }

    private void crearCotizacion() {
        System.out.println("\n-- Crear Cotización --");
        Cotizacion cotizacion = new Cotizacion();
        List<DetalleCotizacion> detalles = new ArrayList<>();

        boolean agregarMas = true;
        while (agregarMas) {
            DetalleCotizacion detalle = new DetalleCotizacion();

            System.out.print("ID del Mueble a cotizar: ");
            Long idMueble = scanner.nextLong();
            detalle.setMueble(new Mueble() {{ setIdMueble(idMueble); }});

            System.out.print("Cantidad: ");
            detalle.setCantidad(scanner.nextInt());
            scanner.nextLine();

            System.out.print("¿Añadir variantes? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                List<Variante> variantes = new ArrayList<>();
                boolean agregarMasVariantes = true;
                while(agregarMasVariantes) {
                    System.out.print("Ingrese ID de la Variante: ");
                    Long idVariante = scanner.nextLong();
                    scanner.nextLine();
                    variantes.add(new Variante() {{ setIdVariante(idVariante); }});

                    System.out.print("¿Quieres añadir otra variante a este mueble? (s/n): ");
                    if (!scanner.nextLine().equalsIgnoreCase("s")) {
                        agregarMasVariantes = false;
                    }
                }
                detalle.setVariantes(variantes);
            }
            detalles.add(detalle);
            System.out.print("¿Agregar otro ítem (otro mueble) a la cotización? (s/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("s")) {
                agregarMas = false;
            }
        }

        cotizacion.setDetalles(detalles);
        Cotizacion cotizacionGuardada = ventaService.crearCotizacion(cotizacion);

        System.out.println(" Cotización creada con el ID: " + cotizacionGuardada.getIdCotizacion());
        System.out.printf(" Total Calculado: %.2f\n", cotizacionGuardada.getTotalCotizacion());
    }

    private void confirmarVenta() {
        System.out.println("\n-- Confirmar Venta --");
        System.out.print("Ingrese el ID de la Cotización a confirmar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Cotizacion venta = ventaService.confirmarVenta(id);
        System.out.println(" Venta confirmada con ID: " + venta.getIdCotizacion());
        System.out.println(" Stock de ítems actualizado.");
    }
}