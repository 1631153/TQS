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

   // Métodos para manejar el estado del juego
    public void mostrarEstadoActual() {
        // Implementar lógica para mostrar el estado actual del juego
    }

    public boolean jugarCarta(Carta carta) {
        // Implementar lógica para jugar una carta
        return false;
    }

    public void robarCarta() {
        // Implementar lógica para robar una carta
    }

    public void decirUNO() {
        // Implementar lógica para decir "UNO"
    }

    public boolean verificarFinDelJuego() {
        // Implementar lógica para verificar si el juego ha terminado
        return false;
    }

    public void mostrarGanador() {
        // Implementar lógica para mostrar al ganador
    }
}
