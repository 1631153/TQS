package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Partida;
import test.Mock.JugadorMock;
import test.Mock.MazoMock;
import main.Model.Carta;
import main.Model.Jugador;

public class PartidaTest {

    private Partida partida, partidaConMock;
    private JugadorMock jugador1, jugador2, jugador3, jugador4; //para pruebas
    private MazoMock mazo;

    @BeforeEach
    public void setUp() {
        partida = new Partida();
        mazo = new MazoMock();
        partida.setMazoMock(mazo);
        partida.iniciarPartida(4);
        
        partidaConMock = new Partida();

        jugador1 = new JugadorMock("P1");
        jugador2 = new JugadorMock("P2");
        jugador3 = new JugadorMock("P3");
        jugador4 = new JugadorMock("P4");
        partidaConMock.setJugadoresMock(List.of(jugador1, jugador2, jugador3, jugador4));
    }

    /**
     * 1. Test para verificar que al iniciar una partida con un número de jugadores,
     * el número de jugadores sea correcto y que cada jugador reciba 7 cartas iniciales por defecto.
     * Se valida que no se pueda iniciar una partida con menos de 2 jugadores ni más de 4.
     * Los casos de prueba para loop testing simple son los siguientes (num_jugadores):
     * (0), (1), (2), (3), (4), (5)
     */
    @Test
    public void testIniciarPartida_NumeroDeJugadores() {
        partida.iniciarPartida(3);  // Inicializar partida con 3 jugadores (partición equivalente valido)
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        partida.iniciarPartida(4);  // Inicializar partida con 4 jugadores (frontera superior)
        assertEquals(4, partida.getJugadores().size(), "La partida debería tener 4 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        partida.iniciarPartida(2);  // Inicializar partida con 2 jugadores (frontera inferior)
        assertEquals(2, partida.getJugadores().size(), "La partida debería tener 2 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        // Se prueba con un tamaño 0 (siempre se prueba con el 0)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(0);
        }, "No se debería poder inicializar una partida con menos de 2 jugadores.");

        // Se prueba con un tamaño inferior a 2 (limite inferior de la frontera inferior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(1);
        }, "No se debería poder inicializar una partida con menos de 2 jugadores.");

        // Se prueba con un tamaño muy inferior a 2 (partición equivalente invalido)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(-10);
        }, "No se debería poder inicializar una partida con menos de 2 jugadores.");

        // Se prueba con un tamaño superior a 4 (limite superior de la frontera superior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(5);
        }, "No se debería poder inicializar una partida con más de 4 jugadores.");

        // Se prueba con un tamaño muy superior a 4 (partición equivalente invalido)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(10);
        }, "No se debería poder inicializar una partida con más de 4 jugadores.");
    }

    /**
     * 2. Test para verificar que al iniciar una partida con una lista de nombres y un número de cartas,
     * el número de jugadores sea correcto. Además, se asegura de que cada jugador reciba las cartas iniciales especificadas.
     * Se valida que no se pueda iniciar una partida con una lista de nombres vacía o nula.
     * 
     * Los casos de prueba para loop testing anidado son los siguientes (nombres, numCartas):
     * (empty,1), (1 elemento,1), (2 elementos,1), (3 elementos,1), (4 elementos ,1), 
     * (5 elementos,1), (3 elementos,0), (3 elementos,1), (3 elementos,2), 
     * (3 elementos,50), (3 elementos,99), (3 elementos,100), (3 elementos,101)
     */
    @Test
    public void testIniciarPartida_ConNombresYCartas() {
        // Caso válido: iniciar partida con 2 jugadores y 1 carta
        List<String> nombres = List.of("Jugador1", "Jugador2");
        partida.iniciarPartida(nombres, 1);
        assertEquals(2, partida.getJugadores().size(), "La partida debería tener 2 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 1 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres, 1); 
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Caso válido: iniciar partida con 4 jugadores y 1 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3", "Jugador4");
        partida.iniciarPartida(nombres, 1);
        assertEquals(4, partida.getJugadores().size(), "La partida debería tener 4 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Caso con lista vacía (partición no válida)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(new ArrayList<>(), 1);  // Lista vacía
        }, "No se debería poder inicializar una partida con una lista vacía de nombres.");

        // Caso con lista null (partición no válida)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(null, 1);  // Lista null
        }, "No se debería poder inicializar una partida con una lista de nombres null.");

        // Caso no válido: menos de 2 jugadores (excede el límite inferior)
        List<String> nombres1 = List.of("Jugador1");
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(nombres1, 1);
        }, "No se debería poder iniciar una partida con menos de 2 jugadores.");

        // Caso con más de 4 jugadores (excede el límite superior)
        List<String> nombres5 = List.of("Jugador1", "Jugador2", "Jugador3", "Jugador4", "Jugador5");
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(nombres5, 1);  // Lista con más de 4 jugadores
        }, "No se debería poder iniciar una partida con más de 4 jugadores.");

        // Caso válido: iniciar partida con 3 jugadores y 2 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres, 2); 
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(2, jugador.getMano().size(), "Cada jugador debe tener 2 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 50 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres, 50);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(50, jugador.getMano().size(), "Cada jugador debe tener 50 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 99 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres, 99);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(99, jugador.getMano().size(), "Cada jugador debe tener 99 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 100 carta
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres, 100);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(100, jugador.getMano().size(), "Cada jugador debe tener 100 cartas al inicio");
        }

        // Caso no válido: menos de 1 carta (excede el límite inferior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(List.of("Jugador1", "Jugador2", "Jugador3"), 0); // No debe permitir 0 cartas
        }, "El número de cartas debe ser mayor que 0");

        // Caso no válido: mas de 100 cartas (excede el límite superior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(List.of("Jugador1", "Jugador2", "Jugador3"), 101); // No debe permitir más de 100 cartas
        }, "El número de cartas no puede ser mayor a 100");
    }

    /**
     * 3. Test para verificar que al iniciar una partida con una lista de nombres especificada,
     * el número de jugadores sea correcto y que cada jugador reciba 7 cartas iniciales por defecto.
     * Este método debería permitir iniciar la partida con una lista personalizada de nombres.
     * Se valida que no se pueda iniciar una partida con una lista vacía o nula.
     * 
     * Los casos de prueba para loop testing simple son los siguientes (nombres):
     * (empty), (1 elemento), (2 elementos), (3 elementos), (4 elementos), (5 elementos)
     */
    @Test
    public void testIniciarPartida_ConNombres() {
        // Caso válido: iniciar partida con 2 jugadores
        List<String> nombres = List.of("Jugador1", "Jugador2");
        partida.iniciarPartida(nombres);  // Inicializar partida con 2 jugadores
        assertEquals(2, partida.getJugadores().size(), "La partida debería tener 2 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores
        nombres = List.of("Jugador1", "Jugador2", "Jugador3");
        partida.iniciarPartida(nombres);  // Inicializar partida con 3 jugadores
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        // Caso válido: iniciar partida con 4 jugadores
        nombres = List.of("Jugador1", "Jugador2", "Jugador3", "Jugador4");
        partida.iniciarPartida(nombres);  // Inicializar partida con 4 jugadores
        assertEquals(4, partida.getJugadores().size(), "La partida debería tener 4 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }

        // Caso no válido: lista vacía (partición no válida)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(new ArrayList<>());
        }, "No se debería poder inicializar una partida con una lista vacía de nombres.");

        // Caso no válido: lista null (partición no válida)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(null);
        }, "No se debería poder inicializar una partida con una lista de nombres null.");

        // Caso no válido: menos de 2 jugadores (excede el límite inferior)
        List<String> nombres1 = List.of("Jugador1");
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(nombres1);
        }, "No se debería poder iniciar una partida con menos de 2 jugadores.");

        // Caso no válido: más de 4 jugadores (excede el límite superior)
        List<String> nombres5 = List.of("Jugador1", "Jugador2", "Jugador3", "Jugador4", "Jugador5");
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(nombres5);
        }, "No se debería poder iniciar una partida con más de 4 jugadores.");
    }

    /**
     * 4. Test para verificar que al iniciar una partida con un número de jugadores y un número personalizado de cartas,
     * el número de jugadores sea correcto y cada jugador reciba el número de cartas especificado.
     * Se valida que no se pueda iniciar una partida con menos de 2 jugadores ni más de 4 jugadores, 
     * y que el número de cartas sea mayor que 0.
     * 
     * Los casos de prueba para loop testing anidado son los siguientes (num_jugadores, numCartas):
     * (0,1), (1,1), (2,1), (3,1), (4,1), (5,1), (3,0), (3,1), (3,2), (3,50), (3,99), (3,100), (3,101)
     */
    @Test
    public void testIniciarPartidaConNumeroDeJugadoresYCartas() {
        // Caso válido: iniciar partida con 2 jugadores y 1 carta
        partida.iniciarPartida(2, 1);
        assertEquals(2, partida.getJugadores().size(), "La partida debería tener 2 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 1 carta
        partida.iniciarPartida(3, 1); 
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Caso válido: iniciar partida con 4 jugadores y 1 carta
        partida.iniciarPartida(4, 1);
        assertEquals(4, partida.getJugadores().size(), "La partida debería tener 4 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(1, jugador.getMano().size(), "Cada jugador debe tener 1 carta al inicio");
        }

        // Se prueba con un tamaño 0 (siempre se prueba con el 0)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(0, 1);
        }, "No se debería poder inicializar una partida con menos de 2 jugadores.");

        // Caso no válido: menos de 2 jugadores (excede el límite inferior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(1, 1);
        }, "No se debería poder iniciar una partida con menos de 2 jugadores.");

        // Caso con más de 4 jugadores (excede el límite superior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(5, 1);  // Lista con más de 4 jugadores
        }, "No se debería poder iniciar una partida con más de 4 jugadores.");

        // Caso válido: iniciar partida con 3 jugadores y 2 carta
        partida.iniciarPartida(3, 2); 
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(2, jugador.getMano().size(), "Cada jugador debe tener 2 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 50 carta
        partida.iniciarPartida(3, 50);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(50, jugador.getMano().size(), "Cada jugador debe tener 50 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 99 carta
        partida.iniciarPartida(3, 99);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(99, jugador.getMano().size(), "Cada jugador debe tener 99 cartas al inicio");
        }

        // Caso válido: iniciar partida con 3 jugadores y 100 carta
        partida.iniciarPartida(3, 100);
        assertEquals(3, partida.getJugadores().size(), "La partida debería tener 3 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(100, jugador.getMano().size(), "Cada jugador debe tener 100 cartas al inicio");
        }

        // Caso no válido: menos de 1 carta (excede el límite inferior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(3, 0); // No debe permitir 0 cartas
        }, "El número de cartas debe ser mayor que 0");

        // Caso no válido: mas de 100 cartas (excede el límite superior)
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(3, 101); // No debe permitir más de 100 cartas
        }, "El número de cartas no puede ser mayor a 100");
    }

    /**
     * 5. Test para verificar que los turnos en la partida se siguen correctamente en sentido horario.
     * Se simula un ciclo de 8 turnos con 4 jugadores, asegurando que la secuencia de turnos sea la esperada.
     */
    @Test
    public void testCicloDeTurnosEnSentidoHorario() {
        int[] secuenciaEsperada = {0, 1, 2, 3, 0, 1, 2, 3};  // La secuencia de turnos esperada para 8 movimientos
        int[] secuenciaReal = new int[secuenciaEsperada.length];
        
        // Se requiere que al menos una carta sea jugable en cada turno.
        Carta carta = new Carta("r", "5");  // Crear una carta de ejemplo, puede ser cualquier carta válida.

        // Avanzar el turno 8 veces (jugando una carta válida cada vez)
        for (int i = 0; i < secuenciaEsperada.length; i++) {
            secuenciaReal[i] = partidaConMock.getNumeroJugadorActual();
            partidaConMock.getJugadorActual().getMano().add(carta);
            assertTrue(partidaConMock.jugarCarta(carta), "El jugador debería poder jugar una carta válida.");
        }

        assertArrayEquals(secuenciaEsperada, secuenciaReal, "El ciclo de turnos no sigue el orden esperado.");
    }

    /**
     * 6. Test para verificar que un jugador puede jugar una carta compatible con la carta actual en el mazo.
     * Se valida que la carta jugada sea eliminada de la mano del jugador y que se actualice correctamente la última carta jugada.
     */
    @Test
    public void testJugarCarta_CartaCompatible() {
        partidaConMock.setJugadoresMock(List.of(jugador1));

        // Crear cartas de prueba
        Carta cartaActual = new Carta("r", "7");
        Carta cartaCompatible = new Carta("r", "5"); // Carta compatible por color

        partidaConMock.getJugadorActual().getMano().add(cartaActual);
        partidaConMock.getJugadorActual().getMano().add(cartaCompatible);

        partidaConMock.jugarCarta(cartaActual);

        int frecuenciaAntes = Collections.frequency(partidaConMock.getJugadorActual().getMano(), cartaCompatible);

        boolean resultado = partidaConMock.jugarCarta(cartaCompatible);

        int frecuenciaDespues = Collections.frequency(partidaConMock.getJugadorActual().getMano(), cartaCompatible);
        assertTrue(frecuenciaDespues < frecuenciaAntes, "La carta jugada debe eliminarse de la mano del jugador");
        assertTrue(resultado, "La carta compatible debería poder jugarse.");
        assertEquals(cartaCompatible, partidaConMock.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse a la carta compatible.");
    }

    /**
     * 7. Test para verificar que un jugador no puede jugar una carta incompatible con la carta actual en el mazo.
     * Se asegura de que la carta incompatible no sea jugada y que la última carta jugada no se actualice.
     */
    @Test
    public void testJugarCarta_CartaIncompatible() {
        Carta cartaActual = new Carta("b", "7");
        Carta cartaIncompatible = new Carta("r", "9"); // Una carta no compatible con la última jugada

        partidaConMock.getJugadorActual().getMano().add(cartaActual);
        
        partidaConMock.jugarCarta(cartaActual);

        partidaConMock.getJugadorActual().getMano().add(cartaIncompatible);

        boolean resultado = partidaConMock.jugarCarta(cartaIncompatible);
        assertFalse(resultado, "La carta incompatible no debería poder jugarse.");
        assertNotEquals(cartaIncompatible, partidaConMock.obtenerUltimaCartaJugada(), "La última carta jugada no debería actualizarse.");
    }

    /**
     * 8. Test para verificar que un comodín puede ser jugado y el color elegido es correctamente asignado.
     * Se asegura de que el comodín cambie la última carta jugada y que el color se actualice correctamente.
     */
    @Test
    public void testJugarCarta_ComodinConColorElegido() {
        partidaConMock.setJugadoresMock(List.of(jugador1));

        Carta cartaComodin = new Carta(null, "wild");
        partidaConMock.getJugadorActual().getMano().add(cartaComodin);

        // Jugar comodín y establecer color a verde
        boolean resultado = partidaConMock.jugarCarta(cartaComodin, "g");
        assertTrue(resultado, "El comodín debería poder jugarse.");
        assertEquals(cartaComodin, partidaConMock.obtenerUltimaCartaJugada(), "La última carta jugada debería ser el comodín.");
        assertEquals("g", partidaConMock.obtenerComodinColor(), "El comodín debe haber establecido el color en verde.");
    }

    /**
     * 9. Test para verificar que un comodín no puede ser jugado con un color nulo.
     * Se asegura de que el color nulo como entrada para un comodín lance una excepción.
     */
    @Test
    public void testJugarCarta_ComodinConColorElegidoNull() {
        Carta cartaComodin = new Carta(null, "wild");
       
        partidaConMock.getJugadorActual().getMano().add(cartaComodin);

        // Jugar comodín y establecer null como color
        assertThrows(AssertionError.class, () -> {
            partidaConMock.jugarCarta(cartaComodin, null);
        },"No se debería poder establecer null como color.");
    }

    /**
     * 10. Test para verificar que no se puede jugar una carta nula.
     * Se asegura de que si la carta es nula, se lanza una excepción.
     */
    @Test
    public void testJugarCarta_CartaNull() {
        // Jugar null como carta
        assertThrows(AssertionError.class, () -> {
            partidaConMock.jugarCarta(null, null);
        },"No se debería poder establecer null como carta.");
    }

    /**
     * 11. Test para verificar que la carta 'reverse' cambia correctamente el sentido del juego.
     * Se valida que el sentido del juego cambie correctamente y que se pueda jugar la carta.
     */
    @Test
    public void testAplicarCartaEspecial_CambioSentido() {
        assertTrue(partidaConMock.getSentidoHorario(), "El sentido de juego debería iniciar en horario.");

        Carta cartaReverse = new Carta("r", "reverse");
        partidaConMock.getJugadorActual().getMano().add(cartaReverse);

        boolean resultado = partidaConMock.jugarCarta(cartaReverse);
        assertTrue(resultado, "La carta 'reverse' debería poder jugarse.");
        assertFalse(partidaConMock.getSentidoHorario(), "El sentido de juego debería cambiar a antihorario.");
    
        Carta cartaReverseNueva = new Carta("b", "reverse");
        partidaConMock.getJugadorActual().getMano().add(cartaReverseNueva);

        resultado = partidaConMock.jugarCarta(cartaReverseNueva);
        assertTrue(resultado, "La carta 'reverse' debería poder jugarse.");
        assertTrue(partidaConMock.getSentidoHorario(), "El sentido de juego debería cambiar a horario.");
    }

    /**
     * 12. Test para verificar que el ciclo de turnos funciona correctamente en sentido antihorario.
     * Se asegura que al jugar una carta 'reverse', el sentido de los turnos se invierta correctamente.
     */
    @Test
    public void testCicloDeTurnosEnSentidoAntihorario() {
        // La secuencia de turnos esperada en sentido antihorario (2 ciclos de 4 jugadores)
        int[] secuenciaEsperada = {0, 3, 2, 1, 0, 3, 2, 1};
        int[] secuenciaReal = new int[secuenciaEsperada.length];
    
        // Inicializamos la secuencia real con el jugador que comienza la partida
        secuenciaReal[0] = partidaConMock.getNumeroJugadorActual();
    
        // Jugamos una carta "reverse" para cambiar la dirección de los turnos a antihorario
        Carta reverse = new Carta("r", "reverse");  // Carta especial "reverse"
        partidaConMock.getJugadorActual().getMano().add(reverse);
        assertTrue(partidaConMock.jugarCarta(reverse), "El jugador debería poder jugar una carta 'reverse'.");
    
        // Ahora seguimos avanzando el turno en sentido antihorario con cartas comunes
        for (int i = 1; i < secuenciaEsperada.length; i++) {
            secuenciaReal[i] = partidaConMock.getNumeroJugadorActual();
    
            // Jugamos una carta común (de valor "5" del color rojo)
            Carta cartaComún = new Carta("r", "5");
            partidaConMock.getJugadorActual().getMano().add(cartaComún);
            assertTrue(partidaConMock.jugarCarta(cartaComún), "El jugador debería poder jugar una carta común.");
        }
    
        // Comprobamos que la secuencia real de jugadores coincida con la secuencia esperada
        assertArrayEquals(secuenciaEsperada, secuenciaReal, 
            "El ciclo de turnos en sentido antihorario no sigue el orden esperado.");
    }

    /**
     * 13. Test para verificar que la carta 'skip' efectivamente salta el turno de un jugador.
     * Se valida que la secuencia de turnos salte al jugador siguiente tras jugar la carta 'skip'.
     */
    @Test
    public void testAplicarCartaEspecial_SkipTurno() {
        Carta cartaSkip = new Carta("r", "skip");
        partidaConMock.getJugadorActual().getMano().add(cartaSkip);

        int jugadorAntesDeSkip = partidaConMock.getNumeroJugadorActual();
        boolean resultado = partidaConMock.jugarCarta(cartaSkip);
        assertTrue(resultado, "La carta 'skip' debería poder jugarse.");

        // Verificar que se salta un turno completo
        int jugadorDespuesDeSkip = partidaConMock.getNumeroJugadorActual();
        assertNotEquals(jugadorAntesDeSkip, jugadorDespuesDeSkip, "El turno debería haber avanzado.");
        assertNotEquals((jugadorAntesDeSkip + 1) % 4, jugadorDespuesDeSkip, "Debería saltarse al siguiente jugador.");
    }

    /**
     * 14. Test para verificar que la carta '+2' efectivamente hace que el siguiente jugador robe dos cartas.
     * Se asegura que el jugador objetivo reciba dos cartas tras jugar la carta '+2'.
     */
    @Test
    public void testAplicarCartaEspecial_RobarDosCartas() {
        Carta cartaMasDos = new Carta("r", "+2");
        partidaConMock.getJugadorActual().getMano().add(cartaMasDos);

        int jugadorObjetivo = (partidaConMock.getNumeroJugadorActual() + 1) % 4;
        Jugador siguienteJugador = partidaConMock.getJugadores().get(jugadorObjetivo);
        int cartasAntes = siguienteJugador.getMano().size();

        boolean resultado = partidaConMock.jugarCarta(cartaMasDos);
        assertTrue(resultado, "La carta '+2' debería poder jugarse.");
        assertEquals(cartasAntes + 2, siguienteJugador.getMano().size(), "El siguiente jugador debería robar 2 cartas.");
    }

    /**
     * 15. Test para verificar que la carta '+4' efectivamente hace que el siguiente jugador robe cuatro cartas.
     * Además, asegura que el color del comodín se establece correctamente.
     */
    @Test
    public void testAplicarCartaEspecial_RobarCuatroCartas() {
        Carta cartaMasCuatro = new Carta(null, "+4");
        partidaConMock.getJugadorActual().getMano().add(cartaMasCuatro);

        int jugadorObjetivo = (partidaConMock.getNumeroJugadorActual() + 1) % 4;
        Jugador siguienteJugador = partidaConMock.getJugadores().get(jugadorObjetivo);
        int cartasAntes = siguienteJugador.getMano().size();

        boolean resultado = partidaConMock.jugarCarta(cartaMasCuatro, "b");  // Jugar +4 y elegir color azul
        assertTrue(resultado, "La carta '+4' debería poder jugarse.");
        assertEquals(cartasAntes + 4, siguienteJugador.getMano().size(), "El siguiente jugador debería robar 4 cartas.");
        assertTrue(partidaConMock.obtenerComodinColor() == "b", "El color del comodín debería establecerse en azul.");
    }

    /**
     * 16. Test para verificar que el método `esFinPartida` funciona correctamente y detecta cuando un jugador se queda sin cartas.
     * Se simula un escenario en el que un jugador se queda sin cartas y la partida termina.
     */
    @Test
    public void testEsFinPartida() {
        partida = new Partida();

        assertThrows(AssertionError.class, () -> {
            partida.esFinPartida();
        },"No se debería poder llamar a esta función si no hay jugadores.");
        
        Carta cartaComodin = new Carta(null, "wild");
        mazo.definirCartaParaRobar(cartaComodin);
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);

        partida.setMazoMock(mazo);
        partida.iniciarPartida(2);

        // Vaciar la mano del jugador para simular una victoria
        assertFalse(partida.esFinPartida(), "La partida no debería terminar si ningún jugador se queda sin cartas.");
        int turnosJugados = 0;
        while (!partida.esFinPartida() && turnosJugados < 14) {
            boolean resultado = partida.jugarCarta(cartaComodin, "b");
            assertTrue(resultado, "El comodín debería jugarse.");
            turnosJugados++;
        }
        assertTrue(partida.esFinPartida(), "La partida debería terminar si un jugador se queda sin cartas.");
    }

    /**
     * 17. Test para verificar que no se puede vincular un mazo null a la partida.
     * Se asegura de que pasar un mazo null a la función `setMazoMock` lance una excepción.
     */
    @Test
    public void testSetMazoMockNull() {
        assertThrows(AssertionError.class, () -> {
            partida.setMazoMock(null);
        },"No se debería poder vincular un null como mazo.");
    }

    /**
     * 18. Test para verificar que no se puede vincular una lista de jugadores null o vacia a la partida.
     * Se asegura de que pasar una lista de jugadores null a la función `setJugadoresMock` lance una excepción.
     */
    @Test
    public void testSetJugadoresMockNullYEmpty() {
        assertThrows(AssertionError.class, () -> {
            partida.setJugadoresMock(null);
        },"No se debería poder vincular un null como lista de jugadores.");

        assertThrows(AssertionError.class, () -> {
            partida.setJugadoresMock(new ArrayList<>());
        },"La lista de jugadores no deberia de poder estar vacia");
    }
}