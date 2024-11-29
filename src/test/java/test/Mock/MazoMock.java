package test.Mock;

import main.Model.Carta;
import main.Model.Mazo;

public class MazoMock extends Mazo{
    private Carta cartaParaRobar;
    private boolean robar;

    public MazoMock() {
        super();
        this.cartaParaRobar = null;
        robar = true;
    }

    @Override
    public Carta robarCarta() {
        if (robar) {
            if (cartaParaRobar != null) {
                return cartaParaRobar;
            }
            return super.robarCarta();
        }
        return null;
    }

    public void definirCartaParaRobar(Carta carta) {
        this.cartaParaRobar = carta;
    }

    public void robar() {
        this.robar = !this.robar;
    }
}
