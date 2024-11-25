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
        //Precondicion o Invariant?
        assert (jugadores == null || jugadores.isEmpty()) : "La lista de jugadores debe estar vacía";
        assert (mazo == null) : "El mazo debe inicializarse como null";
        
        this.jugadores = new ArrayList<>();
        this.jugadorActual = 0;
        this.sentidoHorario = true;
        this.mazo = new Mazo();

        //PostCondicion o Invariant?
        assert (mazo != null) : "El mazo debe estar inicializado.";
    }

    public void setMazoMock(Mazo m) {
        //Precondicion
        assert (m != null) : "El mazo mock no puede ser null";

        this.mazo = m;
    }

    // Método para iniciar la partida
    public void iniciarPartida(int num_jugadores) {
        //Precondicion
        assert (num_jugadores >= 2 && num_jugadores <= 4) : "El numero de jugadores debe estar entre 2 y 4";
        assert (mazo != null) : "El mazo debe estar inicializado antes de iniciar la partida";

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

        //Postcondición
        assert (jugadores.size() == num_jugadores) : "El número de jugadores debe ser igual al número inicial";
        for (Jugador jugador : jugadores) {
            assert jugador.getMano().size() == 7 : "Cada jugador debe tener 7 cartas al iniciar la partida";
        }
    }

    // Sobrecarga que usa colorElegido como null (simulando un valor por defecto)
    public boolean jugarCarta(Carta carta) {
        //Precondicion
        assert (carta != null) : "La carta a jugar no puede ser null";
        assert jugadores.contains(getJugadorActual()) : "El jugador actual debe ser parte de la lista de jugadores";

        return jugarCarta(carta, null);  // Llama al método principal con colorElegido en null
    }

    // Método para que el jugador actual juegue una carta, con elección de color en caso de comodín
    public boolean jugarCarta(Carta carta, String colorElegido) {
        //Precondicion
        assert (carta != null) : "La carta a jugar no puede ser null";
        assert getJugadorActual().getMano().contains(carta) :  "La carta debe estar en la mano del jugador actual";
        assert jugadores.contains(getJugadorActual()) : "El jugador actual debe ser parte de la lista de jugadores";

        boolean res = getJugadorActual().jugarCarta(carta, mazo); //lo hago para aplicar una postcondición
        
        // Jugar la carta: remover de la mano del jugador y actualizar el mazo
        if (res) {
            // Si es una carta especial, aplicar su efecto
            if (carta.isValorEspecial(carta.getValor())) {
                aplicarCartaEspecial(carta, colorElegido);
            } else {
                cambiarTurno();
            }
        }

        //Postconidicon
        assert !(getJugadorActual().getMano().contains(carta)) : "La carta jugada no debe estar en la mano del jugador actual.";
        
        return res;
    }

    // Método para aplicar el efecto de una carta especial, incluyendo elección de color para comodines
    private void aplicarCartaEspecial(Carta carta, String colorElegido) {
         // Precondición
         if (carta.getValor().equals("+4") || carta.getValor().equals("wild")) {
            assert colorElegido != null && !colorElegido.isEmpty() : 
                "Debe elegirse un color válido al jugar un comodín.";
        }
        
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

        // Postcondición
        if (carta.getValor().equals("+4") || carta.getValor().equals("wild")) {
            assert mazo.obtenerComodinColor().equals(colorElegido) : 
                "El color del comodín debe coincidir con el elegido.";
        }
    }

    public void robarCartaJugadorActual() {
         // Precondición
         assert !mazo.getCartas().isEmpty() : "El mazo no puede estar vacío.";
        
         getJugadorActual().robarCarta(mazo);

         // Postcondición
        assert getJugadorActual().getMano().size() > 0 : "La mano del jugador debe contener al menos una carta más.";
    }

    // Método para cambiar el turno al siguiente jugador
    private void cambiarTurno() {
        // Precondición: Debe haber al menos dos jugadores en la partida.
        assert jugadores.size() >= 2 : "Debe haber al menos dos jugadores para cambiar el turno.";

        if (sentidoHorario) {
            jugadorActual = (jugadorActual + 1) % jugadores.size();
        } else {
            jugadorActual = (jugadorActual - 1 + jugadores.size()) % jugadores.size();
        }

        // Postcondición
        assert jugadorActual >= 0 && jugadorActual < jugadores.size() : 
            "El índice del jugador actual debe ser válido.";
    }

    public boolean getSentidoHorario() {
        return sentidoHorario;
    }

    // Método para verificar si la partida ha terminado
    public boolean esFinPartida() {
        // Invariante: Siempre debe haber jugadores en la partida.
        assert !jugadores.isEmpty() : "Debe haber jugadores en la partida.";

        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true; // La partida termina si un jugador se queda sin cartas
            }
        }
        return false;
    }

    // Getter para los jugadores de la partida
    public List<Jugador> getJugadores() {
        // Invariante: La lista de jugadores no debe ser nula.
        assert jugadores != null : "La lista de jugadores no puede ser nula.";

        return jugadores;
    }

    // Getter para el jugador actual
    public Jugador getJugadorActual() {
         // Precondición: El índice del jugador actual debe ser válido.
         assert jugadorActual >= 0 && jugadorActual < jugadores.size() : 
         "El índice del jugador actual debe ser válido.";

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
