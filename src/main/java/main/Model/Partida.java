package main.Model;

import java.util.List;

public class Partida {
    private List<Jugador> jugadores;     // Lista de jugadores en la partida
    private int jugadorActual;           // Índice del jugador con el turno actual
    private boolean sentidoHorario;      // Dirección del juego (true = horario, false = antihorario)
    private Mazo mazo;                   // Mazo de cartas
    private boolean enCurso;             // Indica si la partida está activa

    // Constructor
    public Partida() {
        // Inicialización (sin código interno)
    }

    // Método para iniciar la partida
    public void iniciarPartida() {
        // Inicialización de la partida (sin código interno)
    }

    // Método para cambiar el turno al siguiente jugador
    public void cambiarTurno() {
        // Cambio de turno (sin código interno)
    }

    // Método para aplicar el efecto de una carta especial
    public void aplicarCartaEspecial(Carta carta) {
        // Aplicar efecto especial (sin código interno)
    }

    public boolean getSentidoHorario() {
        return sentidoHorario;
    }

    public void setSentidoHorario(boolean s) {
        this.sentidoHorario = s;
    }

    // Método para verificar si la partida ha terminado
    public boolean esFinPartida() {
        return false;  // Placeholder
    }

    // Getter para los jugadores de la partida
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    // Getter para el jugador actual
    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActual);
    }

    // Getter para el numero del jugador actual
    public int getNumeroJugadorActual() {
        return jugadorActual;
    }

    // Getter para el mazo
    public Mazo getMazo() {
        return mazo;
    }
}
