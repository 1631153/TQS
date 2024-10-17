import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartaTest {

    private Carta cartaRojaNumero;
    private Carta cartaAzulAccion;
    private Carta cartaComodin;
    
    @BeforeEach
    public void setUp() {
        // Crear cartas de ejemplo
        cartaRojaNumero = new Carta("r", "5"); // Carta roja con valor 5
        cartaAzulAccion = new Carta("b", "skip"); // Carta azul de acción especial "skip"
        cartaComodin = new Carta(null, "+4"); // Comodín multicolor +4
    }

    @Test
    public void testCrearCartaNumero() {
        assertEquals("r", cartaRojaNumero.getColor(), "El color de la carta debería ser rojo (r)");
        assertEquals("5", cartaRojaNumero.getValor(), "El valor de la carta debería ser 5");
    }

    @Test
    public void testCrearCartaAccionEspecial() {
        assertEquals("b", cartaAzulAccion.getColor(), "El color de la carta debería ser azul (b)");
        assertEquals("skip", cartaAzulAccion.getValor(), "El valor de la carta debería ser skip (acción especial)");
    }

    @Test
    public void testCrearCartaComodin() {
        assertNull(cartaComodin.getColor(), "El color de la carta comodín debería ser null");
        assertEquals("+4", cartaComodin.getValor(), "El valor de la carta debería ser +4");
    }

    @Test
    public void testCartaInvalida() {
        // Cartas inválidas deben lanzar excepciones (podemos manejar esto en el constructor)
        assertThrows(IllegalArgumentException.class, () -> {
            new Carta("r", "15"); // Valor fuera del rango permitido
        }, "Debería lanzarse IllegalArgumentException para valores de carta no válidos");
    }

    @Test
    public void testCartaCompatibleMismoColor() {
        Carta cartaRoja7 = new Carta("r", "7");
        Carta cartaRoja8 = new Carta("r", "8");
        
        assertTrue(cartaRoja7.esCompatible(cartaRoja8), "Dos cartas del mismo color deberían ser compatibles");
    }

    @Test
    public void testCartaCompatibleMismoValor() {
        Carta cartaRoja5 = new Carta("r", "5");
        Carta cartaAzul5 = new Carta("b", "5");
        
        assertTrue(cartaRoja5.esCompatible(cartaAzul5), "Dos cartas con el mismo valor deberían ser compatibles");
    }

    @Test
    public void testCartaComodinSiempreCompatible() {
        Carta cartaAmarilla2 = new Carta("y", "2");
        
        assertTrue(cartaComodin.esCompatible(cartaAmarilla2), "El comodín debería ser compatible con cualquier carta");
    }

    @Test
    public void testCartaIncompatible() {
        Carta cartaRoja9 = new Carta("r", "9");
        Carta cartaVerde3 = new Carta("g", "3");
        
        assertFalse(cartaRoja9.esCompatible(cartaVerde3), "Cartas con diferentes colores y valores deberían ser incompatibles");
    }
}
