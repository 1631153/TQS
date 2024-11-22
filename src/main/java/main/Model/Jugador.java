package main.Model;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

public class Jugador {
    private String nombre;           // Nombre del jugador
    private List<Carta> mano;        // Cartas en la mano del jugador
    private boolean haDichoUNO;      // Indica si el jugador ha dicho "UNO"

    // Constructor
    public Jugador(String nom) {
        this.nombre = nom;
        this.mano = new ArrayList<>();
        this.haDichoUNO = false;
    }

    // Método para que el jugador robe una carta del mazo
    public void robarCarta(Mazo mazo) {
        this.mano.add(mazo.robarCarta());
        haDichoUNO = false;
    }

    // Método para que el jugador juegue una carta
    public boolean jugarCarta(Carta carta, Mazo mazo) {
        if (this.mano.contains(carta) && mazo.actualizarUltimaCartaJugada(carta)) {
            this.mano.remove(carta);
            return true;
        }
        return false;  // Coloca la carta en el mazo
    }

    // Método para que el jugador diga "UNO"
    public void decirUNO() {
        if (this.mano.size() == 1) {
            this.haDichoUNO = true;
        } else {
            throw new IllegalStateException("El jugador solo puede decir 'UNO' cuando tiene una sola carta.");
        }
    }

    // Getter para la nombre del jugador
    public String getNombre() {
        return nombre;
    }

    // Getter para la mano del jugador
    public List<Carta> getMano() {
        return Collections.unmodifiableList(mano);
    }

    // Método para verificar si el jugador ha dicho UNO
    public boolean haDichoUNO() {
        return haDichoUNO;
    }
}
