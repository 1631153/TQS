package test.Mock;

import java.util.ArrayList;
import java.util.List;

import main.Model.Carta;
import main.Model.Jugador;
import main.Model.Mazo;

public class JugadorMock extends Jugador {
    private List<Carta> cartasEnMano;
    private boolean decirUNO;
    private List<Carta> CartasPredeterminadas;

    public JugadorMock(String nom) {
        super(nom);
        this.cartasEnMano = new ArrayList<>();
        this.decirUNO = false;
        this.CartasPredeterminadas = new ArrayList<>();
    }

    @Override
    public void robarCarta(Mazo mazo) { //mazo aqui no hace nada
        Carta cartaRobada = CartasPredeterminadas.remove(0);
        cartasEnMano.add(cartaRobada);
        decirUNO = false;
    }

    @Override
    public boolean jugarCarta(Carta carta, Mazo mazo) {//mazo aqui no hace nada
        if (cartasEnMano.contains(carta)) {
            cartasEnMano.remove(carta);
            mazo.actualizarUltimaCartaJugada(carta);
            return true;
        }
        return false;
    }

    @Override
    public void decirUNO() {
        if (cartasEnMano.size() == 1) {
            decirUNO = true;
        } else {
            throw new IllegalStateException("El jugador solo puede decir 'UNO' cuando tiene una sola carta.");
        }
    }


    public List<Carta> getCartasEnMano() {
        return cartasEnMano;
    }

    public boolean haDichoUNO() {
        return decirUNO;
    }

    public void setCartasPredeterminadas(List<Carta> cartas) {
        this.CartasPredeterminadas = new ArrayList<>(cartas);
    }

}
