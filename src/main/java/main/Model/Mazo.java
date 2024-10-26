package main.Model;

import java.util.Random;

public class Mazo{
    private static final String[] color = {"r", "b", "g", "y"};
    private static final String[] valor_numerico = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] valor_especial = {"skip", "reverse", "+2"};
    private static final String[] valor_comodin = {"wild", "+4"};

    private final Random mazo;

    private void inicializar(){} //Aquí se aplicarán las probabilidades de la carta

    //constructor
    public Mazo() {
        mazo = new Random();
    } 

    public Carta robarCarta() {}






}