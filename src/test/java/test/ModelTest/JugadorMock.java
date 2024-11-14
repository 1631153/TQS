package test.ModelTest;

import java.util.ArrayList;
import java.util.List;

import main.Model.Carta;
import main.Model.Jugador;
import main.Model.Mazo;

public class JugadorMock extends Jugador {
    private List<Carta> cartasEnMano;
    private boolean decirUNO;
    private String nombre;

    public JugadorMock(String nom) {
        super(nom);
        this.cartasEnMano = new ArrayList<>();
        this.decirUNO = false;
    }

    @Override
    public void robarCarta(Mazo mazo) {
        Carta cartaRobada = mazo.robarCarta();
        if (cartaRobada != null) {
            cartasEnMano.add(cartaRobada);
            decirUNO = false;
        } else {
            throw new IllegalStateException("El mazo siempre deberia devolver una carta.");
        }
    }

    @Override
    public boolean jugarCarta(Carta carta, Mazo mazo) {
        if (cartasEnMano.contains(carta) && mazo.actualizarUltimaCartaJugada(carta)) {
            cartasEnMano.remove(carta);
            return true;
        }
        return false;  // Coloca la carta en el mazo
    }

    @Override
    public void decirUNO() {
        if (cartasEnMano.size() == 1) {
            decirUNO = true;
        } else {
            throw new IllegalStateException("El jugador solo puede decir 'UNO' cuando tiene una sola carta.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Carta> getMano() {
        return cartasEnMano;
    }

    public boolean haDichoUNO() {
        return decirUNO;
    }

}
