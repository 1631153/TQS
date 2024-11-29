package main.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private String nombre;           // Nombre del jugador
    private List<Carta> mano;        // Cartas en la mano del jugador

    /**
     * Constructor de la clase Jugador.
     * 
     * Este constructor inicializa un nuevo jugador con un nombre especificado
     * y una mano vacía de cartas. El nombre es obligatorio y debe ser válido.
     * 
     * @param nombre El nombre del jugador. No puede ser null.
     */
    public Jugador(String nombre) {
        // Precondición: Garantiza que el constructor no reciba un valor null como nombre. 
        // Es importante validar esta precondición, ya que un nombre null podría causar errores en otras operaciones.
        assert (nombre != null) : "El nombre no puede ser null";

        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    /**
     * Permite al jugador robar una carta del mazo.
     * 
     * Precondición: El mazo no puede ser null.
     * 
     * Precondición: La carta robada puede ser null.
     * 
     * @param mazo El mazo del cual el jugador robará una carta.
     */
    public void robarCarta(Mazo mazo) {
        // Precondición: Garantiza que el mazo pasado como argumento no sea null. 
        // Es una precondición importante para evitar errores al intentar interactuar con un objeto inexistente.
        assert (mazo != null) : "El mazo no puede ser null";

        // Robar la carta del mazo
        Carta cartaRobada = mazo.robarCarta();

        // Precondición: Garantiza que la carta robada del mazo pasado como argumento no sea null. 
        // Es una precondición importante para evitar errores al intentar interactuar con un objeto inexistente.
        assert (cartaRobada != null) : "El mazo no puede ser null";

        this.mano.add(cartaRobada);
    }

    /**
     * Permite al jugador jugar una carta desde su mano al mazo.
     * 
     * Precondición: La carta no puede ser null, el mazo no puede ser null, y la carta debe estar en la mano del jugador.
     * 
     * @param carta La carta que el jugador desea jugar.
     * @param mazo El mazo al que el jugador jugará la carta.
     * @return true si la carta fue jugada exitosamente, false si no fue compatible.
     */
    public boolean jugarCarta(Carta carta, Mazo mazo) {
        // Precondición: Garantizan que los argumentos son válidos y que el jugador tiene la carta que quiere jugar. 
        // Sin estas validaciones, podrían ocurrir errores al intentar jugar una carta no válida.
        assert (carta != null) : "La carta no puede ser null";
        assert (mazo != null) : "El mazo no puede ser null";
        assert (this.mano.contains(carta)) : "La carta debe estar en la mano del jugador";

        // Si la carta es compatible, actualizar la última carta jugada y quitarla de la mano
        if (mazo.actualizarUltimaCartaJugada(carta)) {
            this.mano.remove(carta);
            return true;
        }

        return false;  // Retorna si la carta fue jugada exitosamente
    }

    // Getter para el nombre del jugador

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    // Getter para la mano del jugador

    /**
     * Obtiene la mano del jugador.
     * 
     * @return Una lista inmutable de las cartas en la mano del jugador.
     */
    public List<Carta> getMano() {
        return Collections.unmodifiableList(mano);  // Retorna una lista inmutable
    }
}
