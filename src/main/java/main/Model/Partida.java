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

    // Método para iniciar la partida
    public void iniciarPartida() {
        for (int i = 0; i < 4; i++) {
            jugadores.add(new Jugador("Jugador" + i));
        }
        mazo.inicializar();
        
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 7; i++) {
                jugador.recibirCarta(mazo.robarCarta()); //implementar solucion para repartir carta
            }
        }
    }

    // Método para cambiar el turno al siguiente jugador
    public void cambiarTurno() {
        if (sentidoHorario == true) {
            jugadorActual = jugadorActual + 1;
        } else {
            jugadorActual = jugadorActual - 1;
        }
    }

    // Método para aplicar el efecto de una carta especial
    public void aplicarCartaEspecial(Carta carta) {
        String tipo = carta.getValor();
        switch(tipo) {
            case "skip":
                cambiarTurno();
                break;

            case "reverse":
                sentidoHorario = !sentidoHorario;
                break;

            case "+2":
                

            case "wild":
                //ahora mismo no tengo ni idea

            case "+4":
        }
        
        
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
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true;
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

    // Getter para el mazo
    public Mazo getMazo() {
        return mazo;
    }
}
