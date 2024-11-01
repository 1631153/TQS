package main.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mazo {
    private final Random random;
    private final List<Carta> cartas;
    private Carta ultimaCartaJugada;

    private static final int TOTAL_CARTAS = 108;

    public Mazo() {
        this.random = new Random();
        this.cartas = new ArrayList<>(TOTAL_CARTAS);
        this.ultimaCartaJugada = null;
        inicializar();
    }

    public void inicializar() {
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

    // Devuelve una carta aleatoria respetando las probabilidades iniciales
    public Carta robarCarta() {
        int cartaIndex = random.nextInt(cartas.size());
        return cartas.get(cartaIndex);
    }

    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    public Carta obtenerUltimaCartaJugada() {
        return ultimaCartaJugada; // Devuelve la última carta jugada
    }

    // Actualiza la última carta jugada si es compatible con la actual
    public boolean actualizarUltimaCartaJugada(Carta carta) {
        // Verifica si la nueva carta es compatible con la última carta jugada
        if (ultimaCartaJugada == null || carta.esCompatible(ultimaCartaJugada)) {
            this.ultimaCartaJugada = carta;
            return true;
        }
        return false; // No se actualiza si no es compatible
    }
}
