package main.View;

import main.Model.Carta;
import main.Model.Jugador;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class ConsolaView implements InterfazJuego {

    private Scanner scanner; // Mantiene el scanner local a la instancia

    // Constructor
    public ConsolaView() {
        this.scanner = new Scanner(System.in); // Usa el flujo estándar por defecto
    }

    // Constructor que permite configurar un flujo de entrada alternativo
    public ConsolaView(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void mostrarEstadoPartida() {
        System.out.println("Estado de la partida...");
    }

    @Override
    public void mostrarTurno(Jugador jugador) {
        System.out.println("Es el turno de " + jugador.getNombre());
    }

    @Override
    public void mostrarEstado(List<Carta> cartasMano, Carta ultimaCartaJugada) {
        System.out.println("Cartas en mano: " + cartasMano);
        System.out.println("Última carta jugada: " + ultimaCartaJugada);
    }

    @Override
    public void mostrarCartaJugada(Carta carta) {
        System.out.println("Se jugó la carta: " + carta.getColor() + " " + carta.getValor());
    }

    @Override
    public String solicitarColorComodin() {
        System.out.println("Elige un color: (r = rojo, b = azul, g = verde, y = amarillo)");
        return scanner.nextLine().trim(); // Lee la entrada del usuario
    }

    @Override
    public void mostrarGanador(Jugador jugador) {
        System.out.println("¡" + jugador.getNombre() + " ha ganado la partida!");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
