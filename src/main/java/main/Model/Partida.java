package main.Model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Jugador> jugadores;     // Lista de jugadores en la partida
    private int jugadorActual;           // Índice del jugador con el turno actual
    private boolean sentidoHorario;      // Dirección del juego (true = horario, false = antihorario)
    private Mazo mazo;                   // Mazo de cartas

    /**
     * Constructor de la clase Partida.
     * Inicializa la partida con una lista vacía de jugadores, el primer jugador como el jugadorActual,
     * la dirección del turno (sentidoHorario) a horario y inicializa el mazo correctamente.
     */
    public Partida() {
        this.jugadores = new ArrayList<>();
        this.jugadorActual = 0;
        this.sentidoHorario = true;
        this.mazo = new Mazo();
    }

    /**
     * Establece un mazo mock para la partida.
     * 
     * Precondición: El mazo mock no puede ser null.
     * 
     * @param mazo El mazo mock que se quiere establecer.
     */
    public void setMazoMock(Mazo mazo) {
        // Precondición: Valida que el parámetro mazo no sea null, ya que no tiene sentido establecer un mazo null como mock.
        assert (mazo != null) : "El mazo mock no puede ser null";

        this.mazo = mazo;
    }

    /**
     * Inicia la partida con el número de jugadores especificado.
     * Crea los jugadores, les reparte cartas y establece las condiciones iniciales del juego.
     * 
     * Precondición: El número de jugadores debe estar entre 2 y 4.
     * 
     * @param num_jugadores El número de jugadores en la partida.
     */
    public void iniciarPartida(int num_jugadores) {
        // Precondición: Es una validación crítica para el correcto funcionamiento del juego.
        assert (num_jugadores >= 2 && num_jugadores <= 4) : "El número de jugadores debe estar entre 2 y 4";

        // Implementado para poder reutilizar la misma partida (borrando los jugadores anteriores antes de comenzar)
        if (!jugadores.isEmpty()) {
            jugadores.clear();
        }

        // Crear los jugadores y añadirlos a la lista
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

    /**
     * Permite que el jugador actual juegue una carta sin seleccionar un color para un comodín.
     * 
     * @param carta La carta a jugar.
     * @return true si la carta fue jugada correctamente, false si no fue posible.
     */
    public boolean jugarCarta(Carta carta) {
        return jugarCarta(carta, null);  // Llama al método principal con colorElegido en null
    }

    /**
     * Permite que el jugador actual juegue una carta, con la opción de elegir un color si la carta es un comodín.
     * 
     * Precondición: La carta no puede ser null.
     * 
     * @param carta La carta a jugar.
     * @param colorElegido El color elegido en caso de que la carta sea un comodín (puede ser null).
     * @return true si la carta fue jugada correctamente, false si no fue válida.
     */
    public boolean jugarCarta(Carta carta, String colorElegido) {
        // Precondición: La carta no puede ser null
        assert (carta != null) : "La carta a jugar no puede ser null";
        
        // Intentar jugar la carta
        if (getJugadorActual().jugarCarta(carta, mazo)) {
            // Si la carta es especial, aplicar su efecto
            if (carta.isValorEspecial(carta.getValor())) {
                aplicarCartaEspecial(carta, colorElegido);
            } else {
                cambiarTurno();
            }
            return true;
        }
        return false;
    }

    /**
     * Aplica el efecto de una carta especial, incluyendo la elección de color para los comodines.
     * 
     * Precondición: Si la carta es un comodín, el colorElegido no debe ser null.
     * 
     * @param carta La carta especial a aplicar.
     * @param colorElegido El color elegido en caso de que sea un comodín (null si no aplica).
     */
    private void aplicarCartaEspecial(Carta carta, String colorElegido) {
        // Precondición: Si la carta es comodín, el colorElegido debe ser válido
        if (carta.getValor().equals("+4") || carta.getValor().equals("wild")) {
            assert (colorElegido != null) : "Debe elegirse un color válido al jugar un comodín";
        }
        
        switch (carta.getValor()) {
            case "+4":
                mazo.establecerComodinColor(colorElegido);
                cambiarTurno();
                for (int i = 0; i < 4; i++) {
                    robarCartaJugadorActual();
                }
                break;
            case "wild":
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

    /**
     * Permite que el jugador actual robe una carta del mazo.
     */
    public void robarCartaJugadorActual() {      
        getJugadorActual().robarCarta(mazo);
    }

    /**
     * Cambia el turno al siguiente jugador, siguiendo la dirección del juego (sentido horario o antihorario).
     */
    private void cambiarTurno() {;
        if (sentidoHorario) {
            jugadorActual = (jugadorActual + 1) % jugadores.size();
        } else {
            jugadorActual = (jugadorActual - 1 + jugadores.size()) % jugadores.size();
        }
    }

    /**
     * Devuelve la dirección actual del turno (horario o antihorario).
     * 
     * @return true si el turno es horario, false si es antihorario.
     */
    public boolean getSentidoHorario() {
        return sentidoHorario;
    }

    /**
     * Verifica si la partida ha terminado. La partida termina si algún jugador se queda sin cartas.
     * 
     * Precondición: Debe haber al menos un jugador en la partida.
     * 
     * @return true si algún jugador se ha quedado sin cartas, false si la partida continúa.
     */
    public boolean esFinPartida() {
        // Precondición: Debe haber jugadores en la partida
        assert !(jugadores.isEmpty()) : "Debe haber jugadores en la partida";

        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true; // La partida termina si un jugador se queda sin cartas
            }
        }
        return false;
    }

    /**
     * Devuelve la lista de jugadores en la partida.
     * 
     * @return La lista de jugadores.
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Devuelve el jugador actual (quien tiene el turno).
     * 
     * @return El jugador actual.
     */
    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActual);
    }

    /**
     * Devuelve el número del jugador actual.
     * 
     * @return El número del jugador actual.
     */
    public int getNumeroJugadorActual() {
        return jugadorActual;
    }

    /**
     * Obtiene la última carta jugada del mazo.
     * 
     * @return La última carta jugada en el mazo.
     */
    public Carta obtenerUltimaCartaJugada() {
        return mazo.obtenerUltimaCartaJugada(); // Devuelve la última carta jugada
    }

    /**
     * Obtiene el color de un comodín jugado, si corresponde.
     * 
     * @return El color del comodín, o null si no se ha jugado un comodín.
     */
    public String obtenerComodinColor() {
        return mazo.obtenerComodinColor(); // Devuelve el color elegido al jugar un comodin
    }
}
