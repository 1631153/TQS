package main.Controller;

import main.Model.*;
import main.View.*;

public class JuegoController {
    
    // Atributos
    private Partida partida;          // Instancia de la lógica de la partida
    private InterfazJuego interfaz;  // Interfaz para comunicar el estado del juego al usuario

    // Constructor
    public JuegoController(Partida partida, InterfazJuego interfaz) {
        this.partida = partida;
        this.interfaz = interfaz;
    }

    // Métodos

    /**
     * Inicia el juego configurando la vista inicial y los parámetros básicos.
     */
    public void iniciarJuego() {
        // Configuración inicial del juego
        // Lógica para iniciar la partida y conectar con la vista
    }

    /**
     * Ejecuta el turno de un jugador.
     * @param jugador El jugador que realiza el turno.
     * @param carta La carta que desea jugar.
     */
    public void jugarTurno(Jugador jugador, Carta carta) {
        // Lógica para gestionar el turno de un jugador
        // Validar y aplicar las reglas correspondientes
    }
}
