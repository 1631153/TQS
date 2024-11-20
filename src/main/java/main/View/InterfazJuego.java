package main.View;

import main.Model.Carta;
import main.Model.Jugador;
import java.util.List;

public interface InterfazJuego {

    /**
     * Muestra el estado actual de la partida, incluyendo:
     * - Las cartas en juego
     * - Los jugadores y sus manos
     * - El turno actual
     */
    void mostrarEstadoPartida();

    /**
     * Indica de quién es el turno actual.
     * @param jugador El jugador cuyo turno es actualmente.
     */
    void mostrarTurno(Jugador jugador);

    /**
     * Muestra las cartas en la mano del jugador actual y la última carta jugada.
     * @param cartasMano Lista de cartas en la mano del jugador actual.
     * @param ultimaCartaJugada La última carta jugada en el mazo.
     */
    void mostrarEstado(List<Carta> cartasMano, Carta ultimaCartaJugada);

    /**
     * Muestra la carta que se acaba de jugar en el turno actual.
     * @param carta La carta jugada.
     */
    void mostrarCartaJugada(Carta carta);

    /**
     * Solicita al usuario elegir un color cuando se juega un comodín.
     * @return El color elegido por el usuario (ej., "r", "b", "g", "y").
     */
    String solicitarColorComodin();

    /**
     * Notifica al usuario que un jugador ha ganado la partida.
     * @param jugador El jugador ganador.
     */
    void mostrarGanador(Jugador jugador);

    /**
     * Muestra un mensaje genérico o de error en la interfaz.
     * @param mensaje El mensaje que se desea mostrar.
     */
    void mostrarMensaje(String mensaje);
}