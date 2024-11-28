package main.Controller;

import main.Model.*;
import main.View.*;

import java.util.List;

public class JuegoController {
    private Partida partida;
    private InterfazJuego interfaz;

    public JuegoController() {
        this.partida = null;
        this.interfaz = null;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public void setInterfaz(InterfazJuego interfaz) {
        this.interfaz = interfaz;
    }

    /**
     * Muestra el menú principal al usuario y maneja la selección de opciones.
     */
    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            interfaz.clearScreen();
            interfaz.mostrarMensaje("=== Menú Principal ===");
            interfaz.mostrarMensaje("1. Modo Offline (local)");
            interfaz.mostrarMensaje("2. Modo Online (en desarrollo)");
            interfaz.mostrarMensaje("3. Salir");
            interfaz.mostrarMensaje("Selecciona una opción: ");

            String opcion = interfaz.solicitarAccion();
            interfaz.clearScreen();

            switch (opcion) {
                case "1": // Modo Offline
                    mostrarMenuOffline();
                    break;

                case "2": // Modo Online
                    interfaz.mostrarMensaje("El modo online todavía está en desarrollo.");
                    pausar();
                    break;

                case "3": // Salir
                    salir = true;
                    interfaz.mostrarMensaje("Gracias por jugar. ¡Hasta la próxima!");
                    pausar();
                    break;

                default:
                    interfaz.mostrarMensaje("Opción no válida. Por favor, selecciona una opción del menú.");
                    pausar();
            }
        }
    }

    /**
     * Muestra el menú del modo Offline y maneja la selección de opciones.
     */
    private void mostrarMenuOffline() {
        boolean regresar = false;
        while (!regresar) {
            interfaz.clearScreen();
            interfaz.mostrarMensaje("=== Menú Offline ===");
            interfaz.mostrarMensaje("1. Iniciar nueva partida");
            interfaz.mostrarMensaje("2. Cargar partida guardada");
            interfaz.mostrarMensaje("3. Regresar al menú principal");

            String opcion = interfaz.solicitarAccion();
            interfaz.clearScreen();

            switch (opcion) {
                case "1":
                    iniciarNuevaPartidaOffline();
                    break;
                case "2":
                    cargarPartida();
                    break;
                case "3":
                    regresar = true;
                    break;
                default:
                    interfaz.mostrarMensaje("Opción no válida. Por favor, selecciona una opción del menú.");
                    pausar();
            }
        }
    }

    /**
     * Inicia una nueva partida offline con el número de jugadores especificado y controla el flujo completo de la partida.
     */
    private void iniciarNuevaPartidaOffline() {
        interfaz.clearScreen();
        interfaz.mostrarMensaje("Introduce el número de jugadores (2-4), o (S) para salir: ");

        while (true) {
            
            String input = interfaz.solicitarAccion();
            if ("S".equalsIgnoreCase(input)) {
                return;
            }
            try {
                int numeroJugadores = Integer.parseInt(input);
                if (numeroJugadores >= 2 && numeroJugadores <= 4) {
                    partida = new Partida();
                    partida.iniciarPartida(numeroJugadores);

                    while (jugarTurno());

                    interfaz.mostrarMensaje("¡Gracias por jugar!");
                    pausar();

                    return;
                } else {
                    interfaz.clearScreen();
                    interfaz.mostrarMensaje("Por favor, introduce un número válido entre 2 y 4, o (S) para salir: ");
                }
            } catch (NumberFormatException e) {
                interfaz.clearScreen();
                interfaz.mostrarMensaje("Entrada no válida. Introduce un número entre 2 y 4, o (S) para salir: ");
            }
        }
    }

    /**
     * Guarda la partida actual en un archivo. (Proximamente)
     */
    private void guardarPartida() {
        interfaz.mostrarMensaje("Proximamente, todavia en desarollo");
        pausar();
    }

    /**
     * Carga una partida guardada desde un archivo. (Proximamente)
     */
    private void cargarPartida() {
        interfaz.mostrarMensaje("Proximamente, todavia en desarollo");
        pausar();
    }


    /**
     * Controla el flujo de un turno completo para el jugador actual.
     * Permite al usuario salir y guardar la partida en cualquier momento. (proximamente)
     */
    private boolean jugarTurno() {
        interfaz.clearScreen();

        Jugador jugadorActual = partida.getJugadorActual();

        interfaz.mostrarEstadoPartida(
            partida.getJugadores(), 
            partida.obtenerUltimaCartaJugada(), 
            partida.obtenerComodinColor(), 
            jugadorActual);

        // Solicitar acción del jugador (un número, '+', o salir)
        interfaz.mostrarMensaje("Escribe un número para jugar una carta, '+' para robar una carta, o 'S' para salir: ");
        String accion = interfaz.solicitarAccion();

        interfaz.clearScreen();

        if ("S".equalsIgnoreCase(accion)) {
            // Guardar y salir
            interfaz.mostrarMensaje("¿Deseas guardar la partida antes de salir? (S/N): ");
            String guardar = interfaz.solicitarAccion();
            
            interfaz.clearScreen();

            if ("S".equalsIgnoreCase(guardar)) {
                guardarPartida();
            }
            interfaz.mostrarMensaje("Saliendo de la partida...");
            pausar();
            return false;   // Regresar al menú Offline
        } else if ("+".equals(accion)) {
            partida.robarCartaJugadorActual();
            interfaz.mostrarMensaje("Has robado una carta.");
            pausar();
        } else {
            try {
                int indice = Integer.parseInt(accion) - 1;
                List<Carta> mano = jugadorActual.getMano();
                if (indice >= 0 && indice < mano.size()) {
                    Carta carta = mano.get(indice);
                    String colorComodin = null;
                    if (carta.getValor().equals("wild") || carta.getValor().equals("+4")) {
                        colorComodin = interfaz.solicitarColorComodin();
                    }
                    if (!partida.jugarCarta(carta, colorComodin)) {
                        interfaz.mostrarMensaje("Carta no válida.");
                        pausar();
                    }
                } else {
                    interfaz.mostrarMensaje("Número de carta no válido.");
                    pausar();
                }
            } catch (NumberFormatException e) {
                interfaz.mostrarMensaje("Acción no válida.");
                pausar();
            }
        }

        if (verificarFinDelJuego()) {
            return false;
        }
        return true;
    }

    /**
     * Verifica si la partida ha terminado.
     * 
     * @return `true` si la partida ha terminado, `false` en caso contrario.
     */
    public boolean verificarFinDelJuego() {
        if (partida.esFinPartida()) {
            interfaz.clearScreen();
            Jugador ganador = partida.getJugadores().stream()
                    .filter(j -> j.getMano().isEmpty())
                    .findFirst().orElse(null);
            interfaz.mostrarGanador(ganador);
            pausar(); // Pausar para mostrar el ganador
            return true;
        }
        return false;
    }

    /**
     * Pausa el flujo hasta que el jugador presione Enter.
     */
    private void pausar() {
        // Muestra el mensaje solicitando que presione Enter para continuar
        interfaz.mostrarMensaje("Presiona Enter para continuar...");
        
        // Espera hasta que el usuario presione Enter
        interfaz.solicitarAccion();  // Aquí espera que el usuario presione Enter
        interfaz.clearScreen();
    }
}
