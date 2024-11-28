package main.Model;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

import java.io.Serializable;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private String nombre;           // Nombre del jugador
    private List<Carta> mano;        // Cartas en la mano del jugador
    private boolean haDichoUNO;      // Indica si el jugador ha dicho "UNO"

    // Constructor
    public Jugador(String nom) {
        //precondicion
        assert (nom != null) : "El nombre no puede ser null";

        this.nombre = nom;
        this.mano = new ArrayList<>();
        this.haDichoUNO = false;

        //postcondicion
        assert (this.nombre.equals(nom)) : "El nombre no se inicializó correctamente";
        assert (this.mano.isEmpty()) : "La mano debe estar vacía al principio";
        assert !(this.haDichoUNO) : "El jugador no debe decir UNO al principio";
    }

    // Método para que el jugador robe una carta del mazo
    public void robarCarta(Mazo mazo) {
        // Precondición
        assert (mazo != null) : "El mazo no puede ser null";

        Carta cartaRobada = mazo.robarCarta();
        this.mano.add(cartaRobada);
        haDichoUNO = false;

        // Postcondición
        assert (this.mano.contains(cartaRobada)) : "La carta robada debe añadirse a la mano";
        assert (!this.haDichoUNO) : "Decir 'UNO' debe restablecerse al robar una carta";
    }

    // Método para que el jugador juegue una carta
    public boolean jugarCarta(Carta carta, Mazo mazo) {
        // Precondición
        assert (carta != null) : "La carta no puede ser null";
        assert (mazo != null) : "El mazo no puede ser null";
        assert (this.mano.contains(carta)) : "La carta debe estar en la mano del jugador";

        int FrecuanciaAntes = Collections.frequency(this.mano, carta);
        boolean jugada = false;
        if (mazo.actualizarUltimaCartaJugada(carta)) {
            int cantidadAntes = mano.size();
            this.mano.remove(carta);
            int cantidadDespues = mano.size();
            assert (cantidadDespues == cantidadAntes - 1) : "Solo una carta deberia eliminarse";
            jugada = true;
        }
        int FrecuanciaDespues = Collections.frequency(this.mano, carta);
        // Postcondición
        if (jugada) {
            assert (!this.mano.contains(carta) || (FrecuanciaDespues < FrecuanciaAntes)) : "La carta jugada no debe estar en la mano";
            assert (mazo.obtenerUltimaCartaJugada().equals(carta)) : "La carta jugada debe ser la última en el mazo";
        } else {
            assert (this.mano.contains(carta)) : "La mano debe permanecer sin cambios si no se jugó la carta";
        }

        return jugada;  // Coloca la carta en el mazo
    }

    // Método para que el jugador diga "UNO"
    public void decirUNO() {
        // Precondición
        assert (this.mano.size() == 1) : "El jugador solo puede decir 'UNO' si tiene una sola carta";

        if (this.mano.size() == 1) {
            this.haDichoUNO = true;
        } else {
            throw new IllegalStateException("El jugador solo puede decir 'UNO' cuando tiene una sola carta.");
        }

        // Postcondición
        assert (this.haDichoUNO) : "El jugador debe haber dicho 'UNO'";
    }

    // Getter para la nombre del jugador
    public String getNombre() {
        // Postcondición
        assert (this.nombre != null) : "El nombre no puede ser null";

        return nombre;
    }

    // Getter para la mano del jugador
    public List<Carta> getMano() {
        // Postcondición
        assert (this.mano != null) : "La mano no puede ser null";

        return Collections.unmodifiableList(mano);
    }

    // Método para verificar si el jugador ha dicho UNO
    public boolean haDichoUNO() {
        // Postcondición
        assert (this.haDichoUNO == (this.mano.size() == 1)) : "El estado de 'UNO' debe ser consistente con el tamaño de la mano";

        return haDichoUNO;
    }
}
