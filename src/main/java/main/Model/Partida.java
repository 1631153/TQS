package main.Model;

import java.util.List;
import java.util.ArrayList;

public class Partida {
    private List<Jugador> jugadores;     // Lista de jugadores en la partida
    private int jugadorActual;           // Índice del jugador con el turno actual
    private boolean sentidoHorario;      // Dirección del juego (true = horario, false = antihorario)
    private Mazo mazo;                   // Mazo de cartas

    // Constructor
    public Partida() {
        this.jugadores = new ArrayList<>();
        this.jugadorActual = 0;
        this.sentidoHorario = true;
        this.mazo = new Mazo();
    }

    public void setMazoMock(Mazo m) {
        this.mazo = m;
    }

    // Método para iniciar la partida
    public void iniciarPartida(int num_jugadores) {

        if (num_jugadores < 2) {
            throw new IllegalStateException("Siempre tiene que haber un minimo de 2 jugadores en partida.");
        }
        if (num_jugadores > 4) {
            throw new IllegalStateException("Siempre tiene que haber un maximo de 4 jugadores en partida.");
        }

        // Crear 4 jugadores y añadirlos a la lista
        for (int i = 1; i <= num_jugadores; i++) {
            jugadores.add(new Jugador("Jugador" + i));
        }
        
        // Repartir cartas iniciales a cada jugador
        for (Jugador jugador : jugadores) {
            for (int j = 0; j < 7; j++) {
                jugador.robarCarta(mazo);
            }
        }
    }

    // Sobrecarga que usa colorElegido como null (simulando un valor por defecto)
    public boolean jugarCarta(Carta carta) {
        return jugarCarta(carta, null);  // Llama al método principal con colorElegido en null
    }

    // Método para que el jugador actual juegue una carta, con elección de color en caso de comodín
    public boolean jugarCarta(Carta carta, String colorElegido) {
        Jugador jugadorActual = getJugadorActual();
        
        // Jugar la carta: remover de la mano del jugador y actualizar el mazo
        if (jugadorActual.jugarCarta(carta, mazo)) {
            // Si es una carta especial, aplicar su efecto
            if (carta.isValorEspecial(carta.getValor())) {
                aplicarCartaEspecial(carta, colorElegido);
            } else {
                cambiarTurno();
            }
            return true;  // La carta fue jugada exitosamente
        }
        
        return false;  // La carta no fue jugada (incompatible)
    }

    // Método para aplicar el efecto de una carta especial, incluyendo elección de color para comodines
    private void aplicarCartaEspecial(Carta carta, String colorElegido) {
        switch (carta.getValor()) {
            case "+4":
                // Configurar el color deseado y hacer que el siguiente jugador robe 4 cartas
                mazo.establecerComodinColor(colorElegido);
                cambiarTurno();
                for (int i = 0; i < 4; i++) {
                    robarCartaJugadorActual();
                }
                break;
            case "wild":
                // Configurar el color deseado para el comodín
                mazo.establecerComodinColor(colorElegido);
                cambiarTurno();
                break;
            case "skip":
                cambiarTurno();
                cambiarTurno();
                break;
            case "reverse":
                sentidoHorario = !sentidoHorario;
                cambiarTurno();
                break;
            case "+2":
                cambiarTurno();
                for (int i = 0; i < 2; i++) {
                    robarCartaJugadorActual();
                }
                break;
        }
    }

    public void robarCartaJugadorActual() {
        getJugadorActual().robarCarta(mazo);
    }

    // Método para cambiar el turno al siguiente jugador
    private void cambiarTurno() {
        if (sentidoHorario) {
            jugadorActual = (jugadorActual + 1) % jugadores.size();
        } else {
            jugadorActual = (jugadorActual - 1 + jugadores.size()) % jugadores.size();
        }
    }

    public boolean getSentidoHorario() {
        return sentidoHorario;
    }

    // Método para verificar si la partida ha terminado
    public boolean esFinPartida() {
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true; // La partida termina si un jugador se queda sin cartas
            }
        }
        return false;
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

    public Carta obtenerUltimaCartaJugada() {
        return mazo.obtenerUltimaCartaJugada(); // Devuelve la última carta jugada
    }

    public String obtenerComodinColor() {
        return mazo.obtenerComodinColor(); // Devuelve el color elegido al jugar un comodin
    }
}
