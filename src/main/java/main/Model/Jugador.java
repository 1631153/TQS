package main.Model;

import java.util.List;
import java.util.ArrayList;

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

    // Método para que el jugador robe una carta
    public void robarCarta(Mazo mazo) {
        Carta cartaRobada = mazo.robarCarta();
        if (cartaRobada != null) {
            recibirCarta(cartaRobada);
        }
    }

    public void recibirCarta(Carta carta) {
        mano.add(carta);
        haDichoUNO = false;
    }

    // Método para que el jugador juegue una carta
    public void jugarCarta(Carta carta, Mazo mazo) {
        if(mano.contains(carta)) {
            mazo.ultimaCarta(carta);
            mano.remove(carta);
        }
    }

    // Método para que el jugador diga "UNO"
    public void decirUNO() {
        if (mano.size() == 1) {
            haDichoUNO = true;
        }
    }

    // Getter para la nombre del jugador
    public String getNombre() {
        return nombre;
    }

    // Getter para la mano del jugador
    public List<Carta> getMano() {
        return mano;
    }

    // Método para verificar si el jugador ha dicho UNO
    public boolean haDichoUNO() {
        return haDichoUNO;
    }
}
