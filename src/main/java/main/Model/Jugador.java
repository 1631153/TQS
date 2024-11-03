package main.Model;

import java.util.List;

public class Jugador {
    private String nombre;           // Nombre del jugador
    private List<Carta> mano;        // Cartas en la mano del jugador
    private boolean haDichoUNO;      // Indica si el jugador ha dicho "UNO"

    // Constructor
    public Jugador(String nombre) {
        // Inicialización (sin código interno)
    }

    // Método para que el jugador robe una carta
    public void robarCarta(Mazo mazo) {
        // Robar carta (sin código interno)
    }

    public void recibirCarta(Carta carta) {
        mano.add(carta);
    }

    // Método para que el jugador juegue una carta
    public void jugarCarta(Carta carta, Mazo mazo) {
        // Jugar carta (sin código interno)
    }

    // Método para que el jugador diga "UNO"
    public void decirUNO() {
        // Marcar que ha dicho UNO (sin código interno)
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
