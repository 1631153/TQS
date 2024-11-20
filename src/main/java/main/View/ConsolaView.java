package main.View;

import main.Model.Carta;
import main.Model.Jugador;
import java.util.List;

public class ConsolaView implements InterfazJuego {

    // Constructor
    public ConsolaView() {
        // Inicialización de la vista si es necesario
    }

    @Override
    public void mostrarEstadoPartida() {
        // Lógica para mostrar el estado completo de la partida (cartas en juego, jugadores, etc.)
    }

    @Override
    public void mostrarTurno(Jugador jugador) {
        // Lógica para mostrar el turno del jugador actual
        // Ejemplo: "Es el turno de Jugador 1"
    }

    @Override
    public void mostrarEstado(List<Carta> cartasMano, Carta ultimaCartaJugada) {
        // Mostrar las cartas del jugador actual y la última carta jugada
        // Ejemplo:
        // "Cartas en mano: [rojo 5, azul 3, verde +2]"
        // "Última carta jugada: rojo 5"
    }

    @Override
    public void mostrarCartaJugada(Carta carta) {
        // Lógica para mostrar la carta que se acaba de jugar
        // Ejemplo: "Jugador 1 ha jugado la carta: rojo 5"
    }

    @Override
    public String solicitarColorComodin() {
        // Lógica para solicitar al usuario elegir un color cuando se juega un comodín
        // Ejemplo: Mostrar las opciones y pedir una entrada del usuario
        return "b"; // Retorna el color elegido por el usuario
    }

    @Override
    public void mostrarGanador(Jugador jugador) {
        // Lógica para mostrar el ganador del juego
        // Ejemplo: "¡Jugador 1 ha ganado la partida!"
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        // Lógica para mostrar un mensaje general o error
        System.out.println(mensaje);  // Muestra el mensaje en la consola
    }
}