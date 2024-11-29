package main.View;

import main.Model.Carta;
import main.Model.Jugador;
import java.util.List;

public interface InterfazJuego {

    /**
     * Muestra el estado actual de la partida, incluyendo:
     * - Las cartas en juego
     * - Los jugadores y sus manos (sin revelar el contenido de las manos de otros jugadores).
     * - El turno actual.
     * 
     * @param jugadores        Lista de jugadores en la partida.
     * @param ultimaCarta      La última carta jugada.
     * @param colorComodin     El color activo si se jugó un comodín.
     * @param jugadorActual    El jugador que está en turno.
     */
    public void mostrarEstadoPartida(
        List<Jugador> jugadores, 
        Carta ultimaCarta, 
        String colorComodin, 
        Jugador jugadorActual);

    /**
     * Muestra las cartas en la mano del jugador actual y la última carta jugada.
     * @param cartasMano Lista de cartas en la mano del jugador actual.
     * @param color Color de la última carta jugada en el mazo.
     * @param valor Valor de la última carta jugada en el mazo.
     */
    public void mostrarEstado(List<Carta> cartasMano, String color, String valor);

    /**
     * Notifica al usuario que un jugador ha ganado la partida.
     * @param jugador El jugador ganador.
     */
    public void mostrarGanador(Jugador jugador);

    /**
     * Muestra un mensaje genérico o de error en la interfaz.
     * @param mensaje El mensaje que se desea mostrar.
     */
    public void mostrarMensaje(String mensaje);

    /**
     * Solicita al usuario elegir una acción en su turno, como:
     * - '1', '2', '3', ... para jugar una carta.
     * - '+' para robar una carta.
     * - "S" para salir
     * @return La acción seleccionada por el usuario.
     */
    public String solicitarAccion();

    /**
     * Solicita al usuario elegir un color cuando se juega un comodín.
     * Los colores disponibles son:
     * - "r" para rojo.
     * - "b" para azul.
     * - "g" para verde.
     * - "y" para amarillo.
     * @return El color elegido por el usuario (ej., "r", "b", "g", "y").
     */
    public String solicitarColorComodin();

    /**
     * Limpia la pantalla de la consola para ofrecer una interfaz más limpia entre turnos o antes de mostrar nuevos datos.
     * Este método es útil para borrar la información anterior y dar espacio para el siguiente conjunto de mensajes,
     * mejorando la experiencia de usuario en la aplicación.
     */
    public void clearScreen();

    /**
     * Muestra una representación visual o textual de una carta específica.
     * Este método se utiliza para resaltar o detallar una carta en particular en la interfaz de usuario.
     *
     * @param carta La carta que se desea mostrar. Contiene información como color y valor.
     */
    public void mostrarCarta(Carta carta);
}