package main.Controller;

import main.Model.*;
import main.View.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
                    mostrarMenuCargaPartida("saves");
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
                    iniciarPartida(numeroJugadores);

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
     * Inicializa una nueva partida con el número de jugadores especificado.
     * 
     * @param numJugadores Número de jugadores en la partida.
     */
    private void iniciarPartida(int numJugadores) {
        partida.iniciarPartida(numJugadores);
    }

    /**
     * Muestra un menú para cargar una partida desde una carpeta especificada.
     * Permite al usuario seleccionar una partida guardada o salir del menú.
     * 
     * @param fileName Nombre de la carpeta que contiene las partidas guardadas.
     */
    private void mostrarMenuCargaPartida(String fileName) {
        File carpetaSaves = new File(fileName);
        // Verificar si la carpeta existe
        if (!carpetaSaves.exists()) {
            interfaz.mostrarMensaje("La carpeta 'saves' no existe.");
            pausar();
            return;
        }

        // Leer los archivos en la carpeta 'saves'
        File[] archivos = carpetaSaves.listFiles((dir, name) -> name.endsWith(".dat"));
        List<String> nombresPartidasGuardadas = new ArrayList<>();
        for (File archivo : archivos) {
            // Extraer el nombre de la partida sin la extensión '.dat'
            nombresPartidasGuardadas.add(archivo.getName().replace(".dat", ""));
        }

        if (nombresPartidasGuardadas.isEmpty()) {
            interfaz.mostrarMensaje("No hay partidas guardadas.");
            pausar();
            return;
        }

        // Mostrar el menú de carga con las partidas disponibles
        boolean salir = false;
        while (!salir) {
            interfaz.clearScreen();
            interfaz.mostrarMensaje("=== Menú Carga Partida ===");
            interfaz.mostrarMensaje("Selecciona una partida para cargar:");
            for (int i = 0; i < nombresPartidasGuardadas.size(); i++) {
                interfaz.mostrarMensaje((i + 1) + ". " + nombresPartidasGuardadas.get(i));
            }
            interfaz.mostrarMensaje("S. Salir");
            interfaz.mostrarMensaje("Por favor, elige una opción:");

            String opcion = interfaz.solicitarAccion();
            interfaz.clearScreen();

            if ("S".equalsIgnoreCase(opcion)) {
                interfaz.mostrarMensaje("Saliendo del menú de carga...");
                pausar();
                salir = true;
            } else {
                try {
                    int seleccion = Integer.parseInt(opcion);
                    if (seleccion >= 1 && seleccion <= nombresPartidasGuardadas.size()) {
                        cargarPartida("saves", nombresPartidasGuardadas.get(seleccion - 1));
                        salir = true;
                    } else {
                        interfaz.mostrarMensaje("Selección inválida. Intenta de nuevo.");
                        pausar();
                    }
                } catch (NumberFormatException e) {
                    interfaz.mostrarMensaje("Opción inválida. Introduce un número válido o 'S' para salir.");
                    pausar();
                }
            }
        }
    }

    /**
     * Carga una partida guardada a partir del nombre de archivo proporcionado.
     * Si la partida no se encuentra o ocurre un error, muestra un mensaje correspondiente.
     * 
     * @param nombreArchivo Nombre de la partida a cargar (sin la extensión .dat).
     */
    private void cargarPartida(String fileName, String nombreArchivo) {
        File archivo = new File(fileName + "/" + nombreArchivo + ".dat");
        if (!archivo.exists()) {
            interfaz.mostrarMensaje("No se encontró la partida guardada.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            partida = (Partida) ois.readObject();
            interfaz.mostrarMensaje("Partida cargada exitosamente.");
            pausar();
            
            while (jugarTurno());

            interfaz.mostrarMensaje("¡Gracias por jugar!");
            pausar();
        } catch (IOException | ClassNotFoundException e) {
            interfaz.mostrarMensaje("Error al cargar la partida");
        }
    }

    /**
     * Muestra un menú para guardar la partida con un nombre personalizado.
     * Permite al usuario introducir un nombre para guardar la partida o cancelarla.
     * 
     * @param fileName Nombre de la carpeta donde se guardará la partida.
     */
    private void mostrarMenuGuardarPartida(String fileName) {
        boolean salir = false;
        while (!salir) {
            interfaz.mostrarMensaje("=== Menú Guardar Partida ===");
            interfaz.mostrarMensaje("Introduce el nombre para guardar la partida o \"\" para cancelar:");

            String nombrePartida = interfaz.solicitarAccion();

            if ("".equalsIgnoreCase(nombrePartida)) {
                interfaz.mostrarMensaje("Guardado cancelado.");
                salir = true;
            } else {
                guardarPartida(fileName, nombrePartida);
                salir = true;
            }
        }
    }

    /**
     * Guarda la partida actual con el nombre especificado en la carpeta dada.
     * 
     * @param nombrePartida Nombre que se le asignará a la partida guardada.
     * @param fileName Nombre de la carpeta donde se guardará el archivo de la partida.
     */
    private void guardarPartida(String fileName, String nombrePartida) {
        if (partida == null) {
            interfaz.mostrarMensaje("No hay ninguna partida en curso para guardar.");
            return;
        }

        File archivo = new File(fileName +"/" + nombrePartida + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(partida);
            interfaz.mostrarMensaje("Partida guardada exitosamente con el nombre: " + nombrePartida);
        } catch (IOException e) {
            interfaz.mostrarMensaje("Error al guardar la partida.");
        }
    }

    /**
     * Controla el flujo de un turno completo para el jugador actual.
     * Permite al usuario salir y guardar la partida en cualquier momento.
     * Además, verifica si el jugador dice "UNO" al quedar con una carta.
     */
    private boolean jugarTurno() {
        interfaz.clearScreen();

        Jugador jugadorActual = partida.getJugadorActual();

        interfaz.mostrarEstadoPartida(
            partida.getJugadores(), 
            partida.obtenerUltimaCartaJugada(), 
            partida.obtenerComodinColor(), 
            jugadorActual
        );

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
                mostrarMenuGuardarPartida("saves");
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
                    } else {
                        interfaz.mostrarMensaje("El jugador " + jugadorActual.getNombre() + " ha jugado su turno.");
                        interfaz.mostrarCarta(carta);
                        pausar();

                        // Verificar si el jugador tiene una sola carta
                        if (jugadorActual.getMano().size() == 1) {
                            verificarUno(5, 2);
                        }
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
     * Verifica que el jugador diga "UNO" dentro del tiempo límite.
     * Si no lo hace, roba cartas como penalización.
     *
     * @param jugadorActual El jugador que debe decir "UNO".
     * @param tiempoLimite Tiempo límite en segundos para que diga "UNO".
     * @param cartasPenalizacion Número de cartas a robar si no dice "UNO" a tiempo.
     * @return `true` si el jugador dijo "UNO" a tiempo, `false` si recibió la penalización.
     */
    private boolean verificarUno(int tiempoLimite, int cartasPenalizacion) {
        String jugadorActual = partida.getJugadorActual().getNombre();

        interfaz.mostrarMensaje(jugadorActual + ", ¡debes decir \"UNO\"! Tienes " + tiempoLimite + " segundos.");

        // Usar un ExecutorService para manejar el tiempo límite
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(interfaz::solicitarAccion);

        try {
            // Esperar respuesta dentro del tiempo límite
            String respuesta = future.get(tiempoLimite, TimeUnit.SECONDS);
            
            // Verificar si la respuesta es UNO
            if (respuesta.trim().equalsIgnoreCase("UNO")) {
                interfaz.mostrarMensaje("¡Correcto! Has dicho \"UNO\" a tiempo.");
                return true;
            } else {
                interfaz.mostrarMensaje("¡Respuesta incorrecta! No dijiste \"UNO\".");
            }
        } catch (TimeoutException e) {
            interfaz.mostrarMensaje("¡Se acabó el tiempo! No dijiste \"UNO\" a tiempo.");
        } catch (InterruptedException | ExecutionException e) {
            interfaz.mostrarMensaje("Error al procesar tu respuesta.");
        } finally {
            executor.shutdownNow(); // Liberar recursos
        }

        // Penalizar al jugador
        interfaz.mostrarMensaje(jugadorActual + " roba " + cartasPenalizacion + " cartas como penalización.");
        for (int i = 0; i < cartasPenalizacion; i++) {
            partida.robarCartaJugadorActual();; // Asumimos que esta función existe
        }
        return false;
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
