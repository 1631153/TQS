package test.Mock;

import java.util.ArrayList;
import java.util.List;

import main.Model.Carta;
import main.Model.Jugador;
import main.Model.Mazo;

public class JugadorMock extends Jugador {
    private List<Carta> cartasEnMano;
    private Carta cartaParaRobar;

    public JugadorMock(String nom) {
        super(nom);
        this.cartasEnMano = new ArrayList<>();
        this.cartaParaRobar = null;
    }

    @Override
    public void robarCarta(Mazo mazo) { //Si hay cartaParaRobar entonces mazo aqui no hace nada
        if (cartaParaRobar != null) {
            cartasEnMano.add(cartaParaRobar);
        }
        else {
            cartasEnMano.add(mazo.robarCarta());
        }
    }

    @Override
    public boolean jugarCarta(Carta carta, Mazo mazo) { // mazo aqui no hace nada
        if (cartasEnMano.contains(carta) && mazo.actualizarUltimaCartaJugada(carta)) {
            cartasEnMano.remove(carta);
            return true;
        }
        return false;
    }

    @Override
    public List<Carta> getMano() {
        return cartasEnMano;
    }

    public void setCartaParaRobar(Carta carta) {
        this.cartaParaRobar = carta;
    }

}
