package main.View;

import main.Model.Carta;
import main.Model.Jugador;

public class ConsolaView implements InterfazJuego {
    @Override
    public void mostrarEstadoPartida() {
        // Lógica para mostrar el estado del juego en la consola.
    }

    @Override
    public void mostrarTurno(Jugador jugador) {
        // Lógica para mostrar el turno actual en consola.
    }

    @Override
    public void mostrarCartaJugada(Carta carta) {
        // Lógica para mostrar la carta jugada.
    }

    @Override
    public void mostrarGanador(Jugador jugador) {
        // Mensaje de victoria en consola.
    }

    @Override
    public String solicitarColorComodin() {
        // Solicitar entrada del usuario para elegir un color.
        return null; // Implementar entrada de consola.
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        // Mostrar un mensaje simple en consola.
    }
}