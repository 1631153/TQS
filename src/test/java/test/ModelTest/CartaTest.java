package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Carta;

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

    // 1. Test para comprobar que las cartas numéricas tienen un valor válido (0-9)
    @Test
    public void testCartaNumeroEnRango() {
        for (int i = 0; i <= 9; i++) {
            Carta carta = new Carta("r", String.valueOf(i));
            assertEquals(String.valueOf(i), carta.getValor(), "El valor de la carta debería ser " + i);
        }
    }

    // 4. Test para verificar que los valores numéricos fuera de rango lanzan excepciones y que un numero válido no pueden ser multicolor
    @Test
    public void testCartaNumeroInvalidos() {
        String[] valoresInvalidos = {"-20", "-1", "10", "20", null};
        for (String valor : valoresInvalidos) {
            assertThrows(AssertionError.class, () -> {
                new Carta("r", valor);
            }, "Debería lanzarse IllegalArgumentException para valores fuera del rango: " + valor);
        }

        // Verificar que no se puede crear una carta numérica multicolor
        assertThrows(AssertionError.class, () -> {
            new Carta(null, "5");
        }, "No se debería permitir crear una carta numérica con color null (AllColors)");
    }

    // 2. Test para verificar que los colores son válidos
    @Test
    public void testCartaColoresValidos() {
        String[] coloresValidos = {"r", "b", "g", "y"};
        
        // Probar cartas numéricas con colores válidos
        for (String color : coloresValidos) {
            Carta carta = new Carta(color, "5");  // Carta numérica
            assertEquals(color, carta.getColor(), "El color de la carta debería ser " + color);
        }
    }

    // 5. Test para verificar que los colores inválidos lanzan excepciones y que no puede haber un comodin con un color
    @Test
    public void testCartaColorInvalido() {
        // Colores inválidos (partición inválida)
        String[] coloresInvalidos = {"z", "x", "purple", "", "1"};
        for (String color : coloresInvalidos) {
            assertThrows(AssertionError.class, () -> {
                new Carta(color, "5");
            }, "Debería lanzarse IllegalArgumentException para colores no válidos: " + color);
        }

        // Probar que los comodines no pueden tener color
        assertThrows(AssertionError.class, () -> {
            new Carta("r", "wild");  // No debería permitir un comodín con color
        }, "Un comodín no debería tener color");

        assertThrows(AssertionError.class, () -> {
            new Carta("g", "+4");  // No debería permitir un comodín +4 con color
        }, "Un comodín +4 no debería tener color");
    }

    // 3. Test para verificar que las cartas de acción tienen valores válidos
    @Test
    public void testCartaAccionEspecialValida() {
        // Valores permitidos para cartas especiales (sin incluir comodines)
        String[] accionesEspeciales = {"skip", "reverse", "+2"};
        
        // Probar cartas de acción con color
        for (String accion : accionesEspeciales) {
            Carta carta = new Carta("b", accion);  // Cualquier color para acciones especiales
            assertEquals(accion, carta.getValor(), "El valor de la carta especial debería ser " + accion);
        }

        // Probar comodín (sin color)
        Carta comodin = new Carta(null, "wild");  // Un comodín sin color
        assertEquals("wild", comodin.getValor(), "El comodín debería tener valor 'wild' sin color");
        assertNull(comodin.getColor(), "El comodín debería no tener color (null)");

        Carta comodinMasCuatro = new Carta(null, "+4");  // Un comodín +4 sin color
        assertEquals("+4", comodinMasCuatro.getValor(), "El comodín +4 debería tener valor '+4' sin color");
        assertNull(comodinMasCuatro.getColor(), "El comodín +4 debería no tener color (null)");
    }

    // 6. Test para verificar que las cartas de acción inválidas lanzan excepciones
    @Test
    public void testCartaAccionEspecialInvalida() {
        // Valores permitidos para cartas especiales (sin incluir comodines)
        String[] accionesEspeciales = {"skip", "reverse", "+2"};
        
        // Probar cartas de acción sin color
        for (String accion : accionesEspeciales) {
            // Intentar crear una carta especial sin color debe lanzar una excepción
            assertThrows(AssertionError.class, () -> {
                new Carta(null, accion);  // No debe permitir una carta especial sin color
            }, "Las cartas especiales deben tener un color válido");
        }
    }

    // 7. Test para verificar que dos cartas del mismo color son compatibles, en los dos sentidos
    @Test
    public void testCartaCompatibleMismoColor() {
        Carta cartaRoja7 = new Carta("r", "7");
        Carta cartaRoja8 = new Carta("r", "8");
        
        assertTrue(cartaRoja7.esCompatible(cartaRoja8), "Dos cartas del mismo color deberían ser compatibles");
        assertTrue(cartaRoja8.esCompatible(cartaRoja7), "Dos cartas del mismo color deberían ser compatibles");
    }

    // 8. Test para verificar que dos cartas con el mismo valor son compatibles, en los dos sentidos
    @Test
    public void testCartaCompatibleMismoValor() {
        Carta cartaRoja5 = new Carta("r", "5");
        Carta cartaAzul5 = new Carta("b", "5");
        
        assertTrue(cartaRoja5.esCompatible(cartaAzul5), "Dos cartas con el mismo valor deberían ser compatibles");
        assertTrue(cartaAzul5.esCompatible(cartaRoja5), "Dos cartas con el mismo valor deberían ser compatibles");
    }

    // 9. Test para verificar que dos cartas especiales iguales de diferente color son compatibles, en los dos sentidos
    @Test
    public void testCompatibilidadCartasEspecialesDiferenteColor() {
        Carta cartaRojaSkip = new Carta("r", "skip"); // Carta roja de acción especial "skip"
        Carta cartaVerdeSkip = new Carta("g", "skip"); // Carta verde de acción especial "skip"
        
        assertTrue(cartaRojaSkip.esCompatible(cartaVerdeSkip), 
                "Dos cartas especiales iguales pero de diferente color deberían ser compatibles");
        assertTrue(cartaVerdeSkip.esCompatible(cartaRojaSkip), 
                "Dos cartas especiales iguales pero de diferente color deberían ser compatibles");
    }

    // 10. Test para verificar que un comodín es compatible con cualquier otra carta, en los dos sentidos
    @Test
    public void testCartaComodinSiempreCompatible() {
        Carta cartaAmarilla2 = new Carta("y", "2");
        Carta cartaComodinMas4 = new Carta(null, "+4");

        assertTrue(cartaComodin.esCompatible(cartaAmarilla2), "El comodín debería ser compatible con la carta amarilla con valor 2");
        assertTrue(cartaAmarilla2.esCompatible(cartaComodin), "La carta amarilla con valor 2 debería ser compatible con el comodín");

        assertTrue(cartaComodin.esCompatible(cartaRojaNumero), "El comodín debería ser compatible con la carta roja con valor 5");
        assertTrue(cartaRojaNumero.esCompatible(cartaComodin), "La carta roja con valor 5 debería ser compatible con el comodín");

        assertTrue(cartaComodin.esCompatible(cartaAzulAccion), "El comodín debería ser compatible con la carta azul de acción especial 'skip'");
        assertTrue(cartaAzulAccion.esCompatible(cartaComodin), "La carta azul de acción especial 'skip' debería ser compatible con el comodín");

        assertTrue(cartaComodin.esCompatible(cartaComodinMas4), "El comodín wild debería ser compatible con el comodín más 4");
        assertTrue(cartaComodinMas4.esCompatible(cartaComodin), "El comodín más 4 debería ser compatible con comodín wild");
    }

    // 11. Test para verificar que dos cartas con diferentes colores y valores son incompatibles, en los dos sentidos
    @Test
    public void testCartaIncompatible() {
        Carta cartaRoja9 = new Carta("r", "9");
        Carta cartaVerde3 = new Carta("g", "3");
        
        assertFalse(cartaRoja9.esCompatible(cartaVerde3), "Cartas con diferentes colores y valores deberían ser incompatibles");
    }

    // 12. Test para verificar que dos cartas especiales diferentes del mismo color son compatibles, en los dos sentidos
    @Test
    public void testCompatibilidadCartasEspecialesMismoColor() {
        Carta cartaRojaSkip = new Carta("r", "+2"); // Carta roja de acción especial "skip"
        Carta cartaVerdeSkip = new Carta("r", "skip"); // Carta verde de acción especial "skip"
        
        assertTrue(cartaRojaSkip.esCompatible(cartaVerdeSkip), 
                "Dos cartas especiales diferentes pero del mismo color deberían ser compatibles");
    }

    // 12. Test para verificar que dos cartas especiales diferentes de diferente color son incompatibles, en los dos sentidos
    @Test
    public void testCompatibilidadCartasDiferentesEspecialesColor() {
        Carta cartaRojaSkip = new Carta("r", "+2"); // Carta roja de acción especial "skip"
        Carta cartaVerdeSkip = new Carta("b", "skip"); // Carta verde de acción especial "skip"
        
        assertFalse(cartaRojaSkip.esCompatible(cartaVerdeSkip), 
                "Dos cartas especiales diferentes y de diferente color deberían ser incompatibles");
    }

    // 13. Test para verificar que no se puede hacer una comparacion si la otra carta es null
    @Test
    public void testCompatibilidadOtraCartaEsNull() {
        Carta cartaRojaSkip = new Carta("r", "+2");
        
        assertThrows(AssertionError.class, () -> {
            cartaRojaSkip.esCompatible(null); 
        }, "Las otra carta no puede ser null");
    }
}
