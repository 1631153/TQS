package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Partida;
import test.Mock.MazoMock;
import main.Model.Carta;
import main.Model.Jugador;

public class PartidaTest {

    private Partida partida;
    //private Mazo mazo;
    private MazoMock mazo; //para pruebas

    @BeforeEach
    public void setUp() {
        partida = new Partida();
        partida.iniciarPartida(4);  // Inicializar partida con 4 jugadores
        mazo = new MazoMock();
        partida.setMazoMock(mazo);
    }

    @Test
    public void testIniciarPartida_NumeroDeJugadores() {
        assertEquals(4, partida.getJugadores().size(), "La partida debería tener 4 jugadores");
        for (Jugador jugador : partida.getJugadores()) {
            assertEquals(7, jugador.getMano().size(), "Cada jugador debe tener 7 cartas al inicio");
        }
        // Se tiene que probar con un tamaño inferior a 2
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(1);
        },"No se deberia poder inicializar una partida con menos de 2 jugadores.");
        // Se tiene que probar con un tamaño superior a 4
        assertThrows(AssertionError.class, () -> {
            partida.iniciarPartida(5);
        },"No se deberia poder inicializar una partida con menos de 2 jugadores.");
    }

    @Test
    public void testCicloDeTurnosEnSentidoHorario() {
        // Jugadores iniciales
        int[] secuenciaEsperada = {0, 1, 2, 3, 0, 1, 2, 3};  // La secuencia de turnos esperada para 8 movimientos
        int[] secuenciaReal = new int[secuenciaEsperada.length];
        
        // Se requiere que al menos una carta sea jugable en cada turno.
        // Para simplificar el test, supongamos que siempre jugamos una carta válida (comprobación de compatibilidad).
        Carta carta = new Carta("r", "5");  // Crear una carta de ejemplo, puede ser cualquier carta válida.

        // Avanzar el turno 8 veces (jugando una carta válida cada vez)
        for (int i = 0; i < secuenciaEsperada.length; i++) {
            secuenciaReal[i] = partida.getNumeroJugadorActual();
            mazo.definirCartaParaRobar(carta);
            partida.robarCartaJugadorActual();
            assertTrue(partida.jugarCarta(carta), "El jugador debería poder jugar una carta válida.");
        }

        assertArrayEquals(secuenciaEsperada, secuenciaReal, "El ciclo de turnos no sigue el orden esperado.");
    }

    @Test
    public void testJugarCarta_CartaCompatible() {
        Carta cartaActual = new Carta("r", "7");
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);

        mazo.definirCartaParaRobar(cartaActual);
        partida.robarCartaJugadorActual();
        partida.jugarCarta(cartaActual);

        // Suponemos que el jugador tiene una carta compatible
        Carta cartaCompatible = new Carta(cartaActual.getColor(), "5");  // Carta del mismo color
        mazo.definirCartaParaRobar(cartaCompatible);
        partida.robarCartaJugadorActual();

        boolean resultado = partida.jugarCarta(cartaCompatible);
        assertTrue(resultado, "La carta compatible debería poder jugarse.");
        assertEquals(cartaCompatible, partida.obtenerUltimaCartaJugada(), "La última carta jugada debería actualizarse a la carta compatible.");
    }

    @Test
    public void testJugarCarta_CartaIncompatible() {
        Carta cartaActual = new Carta("b", "7");

        mazo.definirCartaParaRobar(cartaActual);
        partida.robarCartaJugadorActual();
        partida.jugarCarta(cartaActual);
        
        Carta cartaIncompatible = new Carta("r", "9"); // Una carta no compatible con la última jugada
        mazo.definirCartaParaRobar(cartaIncompatible);
        partida.robarCartaJugadorActual();

        boolean resultado = partida.jugarCarta(cartaIncompatible);
        assertFalse(resultado, "La carta incompatible no debería poder jugarse.");
        assertNotEquals(cartaIncompatible, partida.obtenerUltimaCartaJugada(), "La última carta jugada no debería actualizarse.");
    }

    @Test
    public void testJugarCarta_ComodinConColorElegido() {
        Carta cartaComodin = new Carta(null, "wild");
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        mazo.definirCartaParaRobar(cartaComodin);
        partida.robarCartaJugadorActual();

        // Jugar comodín y establecer color a verde
        boolean resultado = partida.jugarCarta(cartaComodin, "g");
        assertTrue(resultado, "El comodín debería poder jugarse.");
        assertEquals(cartaComodin, partida.obtenerUltimaCartaJugada(), "La última carta jugada debería ser el comodín.");
        assertEquals("g", partida.obtenerComodinColor(), "El comodin debe haber establecido el color en amarillo.");
        assertTrue(mazo.actualizarUltimaCartaJugada(new Carta("g", "5")), "El color del comodín debería establecerse en verde.");
    }

    @Test
    public void testAplicarCartaEspecial_CambioSentido() {
        assertTrue(partida.getSentidoHorario(), "El sentido de juego debería iniciar en horario.");

        Carta cartaReverse = new Carta("r", "reverse");
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        mazo.definirCartaParaRobar(cartaReverse);
        partida.robarCartaJugadorActual();

        boolean resultado = partida.jugarCarta(cartaReverse);
        assertTrue(resultado, "La carta 'reverse' debería poder jugarse.");
        assertFalse(partida.getSentidoHorario(), "El sentido de juego debería cambiar a antihorario.");
    
        Carta cartaReverseNueva = new Carta("b", "reverse");
        mazo.definirCartaParaRobar(cartaReverseNueva);
        partida.robarCartaJugadorActual();

        resultado = partida.jugarCarta(cartaReverseNueva);
        assertTrue(resultado, "La carta 'reverse' debería poder jugarse.");
        assertTrue(partida.getSentidoHorario(), "El sentido de juego debería cambiar a horario.");
    }

    @Test
    public void testCicloDeTurnosEnSentidoAntihorario() {
        // La secuencia de turnos esperada en sentido antihorario (2 ciclos de 4 jugadores)
        int[] secuenciaEsperada = {0, 3, 2, 1, 0, 3, 2, 1};
        int[] secuenciaReal = new int[secuenciaEsperada.length];
    
        // Inicializamos la secuencia real con el jugador que comienza la partida
        secuenciaReal[0] = partida.getNumeroJugadorActual();
    
        // Jugamos una carta "reverse" para cambiar la dirección de los turnos a antihorario
        Carta reverse = new Carta("r", "reverse");  // Carta especial "reverse"
        mazo.definirCartaParaRobar(reverse);
        partida.robarCartaJugadorActual();  // Añadimos la carta a la mano del jugador
        assertTrue(partida.jugarCarta(reverse), "El jugador debería poder jugar una carta 'reverse'.");
    
        // Ahora seguimos avanzando el turno en sentido antihorario con cartas comunes
        for (int i = 1; i < secuenciaEsperada.length; i++) {
            secuenciaReal[i] = partida.getNumeroJugadorActual();
    
            // Jugamos una carta común (de valor "5" del color rojo)
            Carta cartaComún = new Carta("r", "5");
            mazo.definirCartaParaRobar(cartaComún);
            partida.robarCartaJugadorActual();  // Añadimos la carta a la mano
            assertTrue(partida.jugarCarta(cartaComún), "El jugador debería poder jugar una carta común.");
        }
    
        // Comprobamos que la secuencia real de jugadores coincida con la secuencia esperada
        assertArrayEquals(secuenciaEsperada, secuenciaReal, 
            "El ciclo de turnos en sentido antihorario no sigue el orden esperado.");
    }

    @Test
    public void testAplicarCartaEspecial_SkipTurno() {
        Carta cartaSkip = new Carta("r", "skip");
        mazo.definirCartaParaRobar(cartaSkip);
        partida.robarCartaJugadorActual();

        int jugadorAntesDeSkip = partida.getNumeroJugadorActual();
        boolean resultado = partida.jugarCarta(cartaSkip);
        assertTrue(resultado, "La carta 'skip' debería poder jugarse.");

        // Verificar que se salta un turno completo
        int jugadorDespuesDeSkip = partida.getNumeroJugadorActual();
        assertNotEquals(jugadorAntesDeSkip, jugadorDespuesDeSkip, "El turno debería haber avanzado.");
        assertNotEquals((jugadorAntesDeSkip + 1) % 4, jugadorDespuesDeSkip, "Debería saltarse al siguiente jugador.");
    }

    @Test
    public void testAplicarCartaEspecial_RobarDosCartas() {
        Carta cartaMasDos = new Carta("r", "+2");
        Carta cartaInicial = new Carta("r", "5");
        mazo.actualizarUltimaCartaJugada(cartaInicial);
        mazo.definirCartaParaRobar(cartaMasDos);
        partida.robarCartaJugadorActual();

        int jugadorObjetivo = (partida.getNumeroJugadorActual() + 1) % 4;
        Jugador siguienteJugador = partida.getJugadores().get(jugadorObjetivo);
        int cartasAntes = siguienteJugador.getMano().size();

        boolean resultado = partida.jugarCarta(cartaMasDos);
        assertTrue(resultado, "La carta '+2' debería poder jugarse.");
        assertEquals(cartasAntes + 2, siguienteJugador.getMano().size(), "El siguiente jugador debería robar 2 cartas.");
    }

    @Test
    public void testAplicarCartaEspecial_RobarCuatroCartas() {
        Carta cartaMasCuatro = new Carta(null, "+4");
        mazo.definirCartaParaRobar(cartaMasCuatro);
        partida.robarCartaJugadorActual();

        int jugadorObjetivo = (partida.getNumeroJugadorActual() + 1) % 4;
        Jugador siguienteJugador = partida.getJugadores().get(jugadorObjetivo);
        int cartasAntes = siguienteJugador.getMano().size();

        boolean resultado = partida.jugarCarta(cartaMasCuatro, "b");  // Jugar +4 y elegir color azul
        assertTrue(resultado, "La carta '+4' debería poder jugarse.");
        assertEquals(cartasAntes + 4, siguienteJugador.getMano().size(), "El siguiente jugador debería robar 4 cartas.");
        assertTrue(mazo.actualizarUltimaCartaJugada(new Carta("b", "5")), "El color del comodín debería establecerse en azul.");
    }

    @Test
    public void testEsFinPartida() {
        Partida partida = new Partida();
        Carta comodin = new Carta(null, "wild");

        mazo.definirCartaParaRobar(comodin);
        partida.setMazoMock(mazo);
        partida.iniciarPartida(2);

        // Vaciar la mano del jugador para simular una victoria
        assertFalse(partida.esFinPartida(), "La partida no debería terminar si ningun jugador se queda sin cartas.");
        int turnosJugados = 0;
        while (!partida.esFinPartida() && turnosJugados < 14) {
            boolean resultado = partida.jugarCarta(comodin, "b");
            assertTrue(resultado, "El comodín debería jugarse.");
            turnosJugados++;
        }
        assertTrue(partida.esFinPartida(), "La partida debería terminar si un jugador se queda sin cartas.");
    }
}