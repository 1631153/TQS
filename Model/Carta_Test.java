//Test para poner las reglas, supongo.
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//Red, Green, Blue, Yellow, Null (primero color y luego numero - EN INGLES)

class Carta_Test {

    @Test
        void ValidarCarta() {
            Carta cartaActual = new Carta(Color.RED, "5");
            Carta cartaMazo = new Carta(Color.RED, "8");
            assertTrue(CartaActual.ValidarCarta(cartaMazo));
            
            Carta cartaMal = new Carta(Color.GREEN, "2");
            assertFalse(cartaMal.ValidarCarta(cartaMazo));
        }
}