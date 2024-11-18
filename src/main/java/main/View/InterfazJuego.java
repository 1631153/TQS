package main.View;

import main.Model.Carta;
import main.Model.Jugador;

public interface InterfazJuego {
    
    /**
     * Muestra el estado actual del juego, incluyendo las cartas en juego,
     * los jugadores y sus manos, y el turno actual.
     */
    void mostrarEstadoPartida();

    /**
     * Indica de quién es el turno actual.
     * @param jugador El jugador cuyo turno es actualmente.
     */
    void mostrarTurno(Jugador jugador);

    /**
     * Muestra la carta que se acaba de jugar en el turno actual.
     * @param carta La carta jugada.
     */
    void mostrarCartaJugada(Carta carta);

    /**
     * Notifica al usuario que un jugador ha ganado la partida.
     * @param jugador El jugador ganador.
     */
    void mostrarGanador(Jugador jugador);

    /**
     * Solicita al usuario elegir un color cuando se juega un comodín.
     * @return El color elegido por el usuario (ej., "r", "b", "g", "y").
     */
    String solicitarColorComodin();

    /**
     * Muestra una notificación o mensaje relevante en la interfaz.
     * @param mensaje El mensaje que se desea mostrar.
     */
    void mostrarMensaje(String mensaje);
}