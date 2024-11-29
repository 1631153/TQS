package main.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.io.Serializable;

public class Mazo implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private final Random random;  // Generador de números aleatorios para el robo de cartas
    private final List<Carta> cartas;  // Lista de cartas en el mazo
    private Carta ultimaCartaJugada;  // Guarda la última carta jugada
    private String comodinColor; // Almacena el color después de un comodín

    private static final int TOTAL_CARTAS = 108;  // Total de cartas en el mazo

    /**
     * Constructor de la clase Mazo.
     * Inicializa el mazo con todas las cartas necesarias (cartas numéricas, especiales y comodines).
     */
    public Mazo() {
        this.random = new Random();
        this.cartas = new ArrayList<>(TOTAL_CARTAS);
        this.ultimaCartaJugada = null;
        inicializar();
    }

    /**
     * Método privado que inicializa el mazo con las cartas estándar del juego.
     * Se añaden cartas numéricas (0-9) para cada color, cartas especiales (skip, reverse, +2)
     * y comodines ("wild", "+4").
     */
    private void inicializar() {
        // Agregar cartas numéricas y especiales por cada color
        for (String color : new String[]{"r", "b", "g", "y"}) {
            // Agregar cartas numéricas
            cartas.add(new Carta(color, "0")); // Solo un "0" por color
            for (int i = 1; i <= 9; i++) {
                String valor = String.valueOf(i);
                cartas.add(new Carta(color, valor));
                cartas.add(new Carta(color, valor)); // Duplicado para el rango 1-9
            }
            // Agregar cartas especiales
            for (int i = 0; i < 2; i++) {
                cartas.add(new Carta(color, "skip"));
                cartas.add(new Carta(color, "reverse"));
                cartas.add(new Carta(color, "+2"));
            }
        }
        // Agregar comodines
        for (int i = 0; i < 4; i++) {
            cartas.add(new Carta(null, "wild"));
            cartas.add(new Carta(null, "+4"));
        }
    }

    /**
     * Roba una carta aleatoria del mazo, respetando las probabilidades originales del mazo.
     * 
     * @return La carta robada.
     */
    public Carta robarCarta() {
        // Se obtiene un índice aleatorio y se roba la carta correspondiente
        int cartaIndex = random.nextInt(cartas.size());
        Carta cartaRobada = cartas.get(cartaIndex);

        return cartaRobada;
    }

    /**
     * Obtiene la lista de cartas del mazo.
     * 
     * @return La lista de cartas en el mazo.
     */
    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);  // Devuelve una lista inmutable
    }

    /**
     * Obtiene la última carta que fue jugada.
     * 
     * @return La última carta jugada, o null si no ha habido cartas jugadas.
     */
    public Carta obtenerUltimaCartaJugada() {
        return ultimaCartaJugada;
    }

    /**
     * Obtiene el color de un comodín jugado, si corresponde.
     * 
     * @return El color del comodín, o null si no se ha jugado un comodín.
     */
    public String obtenerComodinColor() {
        return comodinColor;
    }

    /**
     * Actualiza la última carta jugada si es compatible con la carta actual, sin tener en cuenta los comodines.
     * 
     * Precondición: La carta no puede ser null.
     * 
     * @param carta La carta a intentar jugar.
     * @return true si la carta fue jugada correctamente, false si no es compatible.
     */
    public boolean actualizarUltimaCartaJugada(Carta carta) {
        // Precondición: La carta no puede ser null
        assert (carta != null) : "La carta no puede ser null";

        // Si la última carta jugada fue un comodín, verifica que la nueva carta coincida con el color del comodín
        if (comodinColor != null) {
            if (carta.getColor() != null && !carta.getColor().equals(comodinColor)) {
                return false;  // La carta no es compatible con el color del comodín
            }
        } else if (ultimaCartaJugada != null && !carta.esCompatible(ultimaCartaJugada)) {
            return false;  // La carta no es compatible con la última carta jugada
        }

        // Actualiza la última carta jugada y restablece comodinColor si se jugó una carta normal
        this.ultimaCartaJugada = carta;
        if (carta.getColor() != null) {
            comodinColor = null;
        }

        return true;
    }

    /**
     * Establece el color de un comodín jugado para condicionar la próxima carta jugada.
     * 
     * Precondición: El color elegido no puede ser null, y ademas debe de ser valido.
     * 
     * @param colorElegido El color elegido para el comodín.
     */
    public void establecerComodinColor(String colorElegido) {
        // Precondición: El color elegido no puede ser null.
        assert (colorElegido != null) : "El color elegido no puede ser null";
         // Precondición: El color elegido debe de ser valido.
        assert (colorElegido.equals("r") || colorElegido.equals("b") || 
                colorElegido.equals("g") || colorElegido.equals("y")) : "El color elegido debe de ser valido";

        this.comodinColor = colorElegido;
    }
}
