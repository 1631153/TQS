package main.Controller;

import main.Model.*;
import main.View.*;

public class JuegoController {

    private Partida partida; // Mantiene la lógica de la partida actual
    private InterfazJuego interfaz; // Interfaz para interactuar con el usuario

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
        
    }

    /**
     * Muestra el menú del modo Offline y maneja la selección de opciones.
     */
    private void manejarModoOffline() {

    }

    /**
     * Inicia una nueva partida offline.
     */
    private void iniciarNuevaPartidaOffline() {
        
    }

    /**
     * Guarda la partida actual en un archivo. (Proximamente)
     */
    private void guardarPartida() {
        
    }

    /**
     * Carga una partida guardada desde un archivo. (Proximamente)
     */
    private void cargarPartida() {
        
    }

    /**
     * Inicializa una nueva partida con el número de jugadores especificado.
     * 
     * @param numJugadores Número de jugadores en la partida.
     */
    private void iniciarPartida(int numJugadores) {
       
    }

    /**
     * Controla el flujo completo de la partida.
     */
    private void iniciarJuego() {
        
    }

    /**
     * Controla el flujo de un turno completo para el jugador actual.
     * Permite al usuario salir y guardar la partida en cualquier momento. (proximamente)
     */
    private boolean jugarTurno() {
        return false;
    }

    /**
     * Verifica si la partida ha terminado.
     * 
     * @return `true` si la partida ha terminado, `false` en caso contrario.
     */
    public boolean verificarFinDelJuego() {
        return false;
    }

    /**
     * Pausa el flujo hasta que el jugador presione Enter.
     */
    private void pausar() {
        
    }
}
