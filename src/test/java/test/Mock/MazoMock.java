package test.Mock;

import main.Model.Carta;
import main.Model.Mazo;

public class MazoMock extends Mazo{
    private Carta cartaParaRobar;

    public MazoMock() {
        super();
        this.cartaParaRobar = null;
    }

    @Override
    public Carta robarCarta() {
        // Si hay una carta para robar definida, devuélve esa carta
        if (cartaParaRobar != null) {
            return cartaParaRobar;
        }
        // Si no hay carta definida, llama al comportamiento original
        return super.robarCarta();
    }

    public void definirCartaParaRobar(Carta carta) {
        this.cartaParaRobar = carta;
    }
}
