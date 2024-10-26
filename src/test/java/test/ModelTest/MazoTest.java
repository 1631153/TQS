package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

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
            assertTrue(carta.getColor().matches("[rbyg]"), "El color de la carta debería ser rojo, verde, azul o amarillo");
        }
        else{
            assertNull(carta.getColor(), "El color de la carta comodín debería ser null");
        }
    }

    @Test
    public void robarCarta_Valor(){
        Carta carta = mazo.robarCarta();
        assertTrue(carta.getValor().matches("[0-9]|skip|reverse|\\+2|wild|\\+4"), "El valor de la carta deberia ser un numero, especial o comodín")
    }

   




}