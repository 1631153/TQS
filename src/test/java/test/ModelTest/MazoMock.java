package test.ModelTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.Model.Carta;
import main.Model.Mazo;

public class MazoMock extends Mazo{
    private Carta ultimaCarta;

    public MazoMock() {
        super();
        this.ultimaCarta = null;
    }

    @Override
    public Carta robarCarta() {
        if (ultimaCarta != null) {
            return ultimaCarta;
        }
        return super.robarCarta();
    }

    public void definirCartaParaRobar(Carta carta) {
        this.ultimaCarta = carta;
    }
}
