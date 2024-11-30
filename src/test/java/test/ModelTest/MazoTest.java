package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    /**
     * 1. Test para verificar la correcta inicialización del mazo.
     * Este test asegura que el mazo se inicializa correctamente con exactamente 108 cartas
     * y que la frecuencia de las cartas en el mazo corresponde con la cantidad esperada para cada valor.
     */
    @Test
    public void testInicializacionCorrectaDelMazo() {
        List<Carta> cartas = mazo.getCartas();
        assertEquals(108, cartas.size(), "El mazo debería contener exactamente 108 cartas");

        // Frecuencia esperada
        Map<String, Integer> frecuenciaEsperada = new HashMap<>();
        frecuenciaEsperada.put("0", 4);
        for (int i = 1; i <= 9; i++) {
            frecuenciaEsperada.put(String.valueOf(i), 8);
        }
        frecuenciaEsperada.put("skip", 8);
        frecuenciaEsperada.put("reverse", 8);
        frecuenciaEsperada.put("+2", 8);
        frecuenciaEsperada.put("wild", 4);
        frecuenciaEsperada.put("+4", 4);

        // Comprobar frecuencia actual en el mazo
        Map<String, Long> frecuenciaActual = cartas.stream()
            .collect(Collectors.groupingBy(Carta::getValor, Collectors.counting()));

        for (String valor : frecuenciaEsperada.keySet()) {
            assertEquals(frecuenciaEsperada.get(valor).intValue(), frecuenciaActual.getOrDefault(valor, 0L).intValue(),
                "La cantidad de cartas para " + valor + " debería ser " + frecuenciaEsperada.get(valor));
        }
    }

    /**
     * 2. Test para asegurar que al robar una carta, esta no sea null.
     * Verifica que el método `robarCarta` siempre devuelva una carta válida, no nula.
     */
    @Test
    public void testRobarCartaNoDebeSerNull() {
        Carta carta = mazo.robarCarta();
        assertNotNull(carta, "La carta robada no debería ser null");
    }

    /**
     * 3. Test para verificar que al robar una carta, esta tenga un color correcto.
     * Asegura que las cartas que no sean comodines tengan un color no nulo y válido,
     * y que los comodines tengan color nulo.
     */
    @Test
    public void testRobarCartaDebeTenerColorCorrecto() {
        Carta carta = mazo.robarCarta();

        if (!carta.getValor().equals("wild") && !carta.getValor().equals("+4")) {
            assertNotNull(carta.getColor(), "El color de la carta no debe ser null para cartas que no son comodín");
            assertTrue(carta.getColor().matches("[rgby]"), "El color de la carta debería ser rojo, verde, azul o amarillo");
        } else {
            assertNull(carta.getColor(), "El color de la carta comodín debería ser null");
        }
    }

    /**
     * 4. Test para verificar que al robar una carta, esta tenga un valor correcto.
     * Comprueba que la carta robada tenga un valor válido, que puede ser un número del 0 al 9,
     * o valores especiales como "skip", "reverse", "+2", "wild" o "+4".
     */
    @Test
    public void testRobarCartaDebeTenerValorCorrecto(){
        Carta carta = mazo.robarCarta();
        assertTrue(carta.getValor().matches("[0-9]|skip|reverse|\\+2|wild|\\+4"), "El valor de la carta deberia ser un numero, especial o comodín");
        //quitarle algun valor del matches hace que falle algunas veces al ser aleatorio
    }

    /**
     * 5. Test para verificar la probabilidad de las cartas al robar múltiples cartas.
     * Realiza 10,000 extracciones para comprobar que las frecuencias de las cartas robadas
     * se aproximan a las probabilidades esperadas basadas en la distribución inicial del mazo.
     */
    @Test
    public void testProbabilidadDeCartas() {
        int numExtracciones = 10000;
        Map<String, Integer> frecuenciaExtracciones = new HashMap<>();

        for (int i = 0; i < numExtracciones; i++) {
            Carta carta = mazo.robarCarta();
            frecuenciaExtracciones.put(carta.getValor(), frecuenciaExtracciones.getOrDefault(carta.getValor(), 0) + 1);
        }

        // Frecuencias aproximadas
        double tolerancia = 0.1; // Tolerancia de 10%
        Map<String, Double> probabilidadesEsperadas = Map.of(
            "0", 4 / 108.0,
            "wild", 4 / 108.0,
            "+4", 4 / 108.0,
            "skip", 8 / 108.0,
            "reverse", 8 / 108.0,
            "+2", 8 / 108.0
        );

        for (String valor : probabilidadesEsperadas.keySet()) {
            double frecuenciaObtenida = frecuenciaExtracciones.getOrDefault(valor, 0) / (double) numExtracciones;
            double frecuenciaEsperada = probabilidadesEsperadas.get(valor);

            assertTrue(
                Math.abs(frecuenciaEsperada - frecuenciaObtenida) < tolerancia,
                "La frecuencia de la carta " + valor + " está fuera del rango esperado"
            );
        }
    }

    /**
     * 6. Test para verificar la variedad de cartas robadas.
     * Asegura que al robar 1,000 cartas, haya una variedad significativa de cartas robadas,
     * verificando que se obtienen diferentes tipos de cartas.
     */
    @Test
    public void testVariedadDeCartasRobadas() {
        int numExtracciones = 1000;
        List<Carta> cartasRobadas = IntStream.range(0, numExtracciones)
            .mapToObj(i -> mazo.robarCarta())
            .collect(Collectors.toList());

        // Comprobación de que hay al menos varias cartas diferentes
        long tiposDiferentes = cartasRobadas.stream()
            .map(Carta::getValor)
            .distinct()
            .count();
        
        assertTrue(tiposDiferentes > 10, "Debe haber al menos 10 tipos de cartas distintas en las cartas robadas");
    }

    /**
     * 7. Test para verificar que la primera carta jugada actualiza correctamente la última carta jugada en el mazo.
     * Asegura que la primera carta que se juega se actualiza correctamente como la última carta jugada en el mazo.
     */
    @Test
    public void testActualizarUltimaCartaJugada_PrimeraCarta() {
        Carta cartaInicial = new Carta("r", "5");
        
        // Como es la primera carta, debería actualizarse sin restricciones
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaInicial);
        
        assertTrue(resultado, "La primera carta jugada debe actualizarse sin restricciones.");
        assertEquals(cartaInicial, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería ser la primera carta.");
    }

    /**
     * 8. Test para verificar que se puede jugar una carta compatible, y que esta actualiza correctamente la última carta jugada.
     * Este test asegura que cuando se juega una carta compatible (por color o valor), la última carta jugada se actualiza correctamente.
     */
    @Test
    public void testActualizarUltimaCartaJugada_CartaCompatible() {
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        
        // Nueva carta compatible por color
        Carta cartaCompatible = new Carta("r", "3");
        
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaCompatible);
        
        assertTrue(resultado, "La carta compatible debería permitir actualizar la última carta jugada.");
        assertEquals(cartaCompatible, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse a la carta compatible.");
    }

    /**
     * 9. Test para verificar que una carta incompatible no puede actualizar la última carta jugada.
     * Asegura que cuando una carta no es compatible (por color o valor), no se permite que se actualice la última carta jugada.
     */
    @Test
    public void testActualizarUltimaCartaJugada_CartaIncompatible() {
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        
        // Nueva carta incompatible por color y valor
        Carta cartaIncompatible = new Carta("b", "9");
        
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaIncompatible);
        
        assertFalse(resultado, "La carta incompatible no debería permitir actualizar la última carta jugada.");
        assertEquals(cartaInicial, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería seguir siendo la carta inicial.");
    }

    /**
     * 10. Test para verificar que un comodín puede actualizar la última carta jugada sin restricciones.
     * Asegura que los comodines, que siempre son compatibles con cualquier carta, actualicen correctamente la última carta jugada.
     */
    @Test
    public void testActualizarUltimaCartaJugada_ComodinCompatible() {
        Carta cartaInicial = new Carta("g", "2");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        
        // Nueva carta es un comodín, siempre compatible
        Carta cartaComodin = new Carta(null, "wild");
        
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaComodin);
        mazo.establecerComodinColor("g");
        
        assertTrue(resultado, "Un comodín debería permitir actualizar la última carta jugada sin restricciones.");
        assertEquals(cartaComodin, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse al comodín.");
        assertEquals("g", mazo.obtenerComodinColor(), "El comodin debe haber establecido el color en amarillo.");

        // Nueva carta es un comodín, siempre compatible
        Carta cartaComodinNew = new Carta(null, "+4");

        resultado = mazo.actualizarUltimaCartaJugada(cartaComodinNew);
        mazo.establecerComodinColor("b");
        
        assertTrue(resultado, "Un comodín debería permitir actualizar la última carta jugada sin restricciones.");
        assertEquals(cartaComodinNew, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse al comodín.");
        assertEquals("b", mazo.obtenerComodinColor(), "El comodin debe haber establecido el color en azul.");
    }
  
    /**
    * 11. Test para verificar que un comodín con un color condicionado no permita jugar una carta de color incompatible.
    * Este test asegura que cuando un comodín condiciona el color, solo se puedan jugar cartas que coincidan con dicho color.
    */
    @Test
    public void testActualizarUltimaCartaJugada_ComodinIncompatible() {
        Carta cartaComodin = new Carta(null, "wild");

        mazo.actualizarUltimaCartaJugada(cartaComodin);
        mazo.establecerComodinColor("b");
        
        // Nueva carta no tiene el color fijado por el comodín
        Carta cartaNueva = new Carta("g", "2");
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaNueva);

        assertFalse(resultado, "Un comodín debería fijar un color diferente a esta carta.");
        assertNotEquals(cartaNueva, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería seguirsiendo el comodín.");
    }

    /**
     * 12. Test para verificar que al jugar un comodín, se establezca correctamente el color condicionado para la próxima carta.
     * Asegura que cuando se juega un comodín, se establece correctamente el color para la siguiente carta.
     */
    @Test
    public void testEstablecerComodinColorYCondicionarProximaCarta() {
        Carta cartaComodin = new Carta(null, "wild");
        
        // Jugar un comodín y establecer color rojo
        mazo.actualizarUltimaCartaJugada(cartaComodin);
        mazo.establecerComodinColor("r");
        
        // La próxima carta debe coincidir con el color rojo
        Carta cartaRoja = new Carta("r", "7");
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaRoja);
        
        assertTrue(resultado, "La carta roja debería ser válida tras jugar un comodín con color condicionado.");
        assertEquals(cartaRoja, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería ser la carta roja jugada.");
    }

    /**
     * 13. Test para verificar que un color condicionado por un comodín no permita jugar una carta de color incorrecto.
     * Asegura que el color establecido por un comodín sea respetado, y que una carta con un color incorrecto sea rechazada.
     */
    @Test
    public void testCondicionDeColorComodinFallaConColorIncorrecto() {
        Carta cartaComodin = new Carta(null, "wild");
        
        // Jugar un comodín y establecer el color azul
        mazo.actualizarUltimaCartaJugada(cartaComodin);
        mazo.establecerComodinColor("b");
        
        // Intentar jugar una carta verde, lo cual debería fallar
        Carta cartaVerde = new Carta("g", "3");
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaVerde);
        
        assertFalse(resultado, "Una carta verde no debería ser válida tras jugar un comodín con color condicionado a azul.");
        assertEquals(cartaComodin, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería seguir siendo el comodín.");
    }

    /**
     * 14. Test para verificar que el color condicionado por un comodín se restablece después de jugar una carta normal.
     * Este test asegura que una vez jugada una carta normal (sin comodín), se restablece el color condicionado por el comodín.
     */
    @Test
    public void testRestablecerColorComodinConCartaNormal() {
        Carta cartaComodin = new Carta(null, "wild");
        
        // Jugar un comodín y establecer el color amarillo
        mazo.actualizarUltimaCartaJugada(cartaComodin);
        mazo.establecerComodinColor("y");
        
        // Jugar una carta amarilla que es compatible
        Carta cartaAmarilla = new Carta("y", "5");
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaAmarilla);
        
        // Verificar que el color condicionado se restablece después de una carta normal
        assertTrue(resultado, "La carta amarilla debería ser válida tras jugar un comodín con color condicionado.");
        assertEquals(cartaAmarilla, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería ser la carta amarilla.");
        
        // Jugar una carta incompatible en color (sin color condicionado)
        Carta cartaRoja = new Carta("r", "2");
        resultado = mazo.actualizarUltimaCartaJugada(cartaRoja);
        
        assertFalse(resultado, "La carta roja no debería ser válida ya que no coincide con la carta amarilla y no hay color condicionado.");
        assertEquals(cartaAmarilla, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería seguir siendo la carta amarilla.");
    }

    /**
     * 15. Test para verificar que no se puede jugar un `null` como carta.
     * Asegura que el método `actualizarUltimaCartaJugada` lance una excepción cuando se intente jugar una carta `null`.
     */
    @Test
    public void testActualizarUltimaCartaJugadaConNull() {
        assertThrows(AssertionError.class, () -> mazo.actualizarUltimaCartaJugada(null), "No se debería poder jugar un null como carta");
    }

    /**
     * 16. Test para verificar que no se puede establecer un color `null` para un comodín.
     * Asegura que el método `establecerComodinColor` lance una excepción cuando se pase un valor `null` como color.
     */
    @Test
    public void testEstablecerComodinColorNull() {
        assertThrows(AssertionError.class, () -> mazo.establecerComodinColor(null), "No se debería poder pasar un null como color");
    }

    /**
     * 17. Test para verificar que no se puede establecer un color incorrecto para un comodín.
     * Asegura que el método `establecerComodinColor` lance una excepción cuando se pase un color no válido.
     */
    @Test
    public void testEstablecerComodinColorIncorrecto() {
        assertThrows(AssertionError.class, () -> mazo.establecerComodinColor("j"), "No se debería poder pasar un null como color");
    }
}
