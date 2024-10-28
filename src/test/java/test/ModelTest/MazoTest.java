package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Carta;
import main.Model.Mazo;

public class MazoTest {

    private Mazo mazo;

    @BeforeEach
    public void setUp() {
        mazo = new Mazo();
    }

    @Test
    public void robarCarta_NONull() {
        Carta carta = mazo.robarCarta();
        assertNotNull(carta, "La carta robada no debería ser null");
    }

    @Test
    public void robarCarta_Color() {
        Carta carta = mazo.robarCarta();

        if (!carta.getValor().equals("wild") && !carta.getValor().equals("+4")) {
            assertNotNull(carta.getColor(), "El color de la carta no debe ser null para cartas que no son comodín");
            assertTrue(carta.getColor().matches("[rgby]"), "El color de la carta debería ser rojo, verde, azul o amarillo");
        }
        else{
            assertNull(carta.getColor(), "El color de la carta comodín debería ser null");
        }
    }

    @Test
    public void robarCarta_Valor(){
        Carta carta = mazo.robarCarta();
        assertTrue(carta.getValor().matches("[0-9]|skip|reverse|\\+2|wild|\\+4"), "El valor de la carta deberia ser un numero, especial o comodín");
        //quitarle algun valor del matches hace que falle algunas veces al ser aleatorio
    }

    @Test
    public void probabilidades() {
        Map<String, Integer> frecuencia = new HashMap<>();
       
        //se añade cada carta, sus probabilidades vienen a partir de las veces repetidas
        frecuencia.put("0", 4);
        for (int i = 1; i <= 9; i++) {
            frecuencia.put(String.valueOf(i), 8);
        }
        frecuencia.put("skip", 8);
        frecuencia.put("reverse", 8);
        frecuencia.put("+2", 8);
        frecuencia.put("wild", 4);
        frecuencia.put("+4", 4);

        List<Carta> cartas = mazo.getCartas();
        Map<String, Integer> frecuenciaCartas = new HashMap<>();

        for (Carta carta : cartas) {
            String valor = carta.getValor();
            frecuenciaCartas.put(valor, frecuenciaCartas.getOrDefault(valor, 0) + 1);
        }

        //Comprobar que la frecuencia de cada tipo es la esperada
        for (String entry : frecuencia.keySet()) {
            int cantidadEsperada = frecuencia.get(entry);
            int cantidadObtenida = frecuenciaCartas.getOrDefault(entry, 0);

            assertEquals(cantidadEsperada, cantidadObtenida, "La cantidad de cartas para " + entry + " debería ser " + cantidadEsperada + " pero es " + cantidadObtenida);
        }

    }

}