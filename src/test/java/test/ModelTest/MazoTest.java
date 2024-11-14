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

    @Test
    public void testRobarCartaNoDebeSerNull() {
        Carta carta = mazo.robarCarta();
        assertNotNull(carta, "La carta robada no debería ser null");
    }

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

    @Test
    public void testRobarCartaDebeTenerValorCorrecto(){
        Carta carta = mazo.robarCarta();
        assertTrue(carta.getValor().matches("[0-9]|skip|reverse|\\+2|wild|\\+4"), "El valor de la carta deberia ser un numero, especial o comodín");
        //quitarle algun valor del matches hace que falle algunas veces al ser aleatorio
    }

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

    @Test
    public void testActualizarUltimaCartaJugada_PrimeraCarta() {
        Carta cartaInicial = new Carta("r", "5");
        
        // Como es la primera carta, debería actualizarse sin restricciones
        boolean resultado = mazo.actualizarUltimaCartaJugada(cartaInicial);
        
        assertTrue(resultado, "La primera carta jugada debe actualizarse sin restricciones.");
        assertEquals(cartaInicial, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería ser la primera carta.");
    }

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

        // Nueva carta es un comodín, siempre compatible
        Carta cartaComodinNew = new Carta(null, "+4");

        resultado = mazo.actualizarUltimaCartaJugada(cartaComodinNew);
        mazo.establecerComodinColor("b");
        
        assertTrue(resultado, "Un comodín debería permitir actualizar la última carta jugada sin restricciones.");
        assertEquals(cartaComodinNew, mazo.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse al comodín.");
    }
  
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
}
