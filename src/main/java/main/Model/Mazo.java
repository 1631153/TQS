package main.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mazo{
    private static final String[] colores = {"r", "b", "g", "y"};
    private static final String[] valor_especial = {"skip", "reverse", "+2"};

    private final Random mazo;
    private List<Carta> cartas;

    //constructor
    public Mazo() {
        mazo = new Random();
        inicializar();
    } 

    private void inicializar(){
        cartas = new ArrayList<>();

        //La logica a continuación funciona tal que así:
        //Al robar una carta de la lista, la probabilidad de tener un tipo especifico 
        //de carta se ajusta automaticamente segun su frecuencia en el mazo.
        //ALTERNATIVA: Considerar una tabla de probabilidades.

        for (String color: colores) {
            cartas.add(new Carta(color, "0"));
            for (int i = 1; i <= 9; i++) {
                Carta cartaNumerica = new Carta(color, String.valueOf(i));
                cartas.add(cartaNumerica);
                cartas.add(cartaNumerica);
            }
        }

        for (String color: colores) {
            for (String valor: valor_especial) {
                Carta cartaNumerica = new Carta(color, valor);
                cartas.add(cartaNumerica);
                cartas.add(cartaNumerica);
            }
        }

        for (int i = 0; i < 4; i++) {
            cartas.add(new Carta(null, "wild"));
            cartas.add(new Carta(null, "+4"));
        }
    
    } 

    
    //En teoria esto coge una carta al azar de una lista que respta la probabilidad de cada tipo de carta
    public Carta robarCarta() {
        int Carta_random = mazo.nextInt(cartas.size());
        return cartas.get(Carta_random);
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}