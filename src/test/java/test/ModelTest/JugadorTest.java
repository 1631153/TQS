package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Carta;
import main.Model.Jugador;
import test.Mock.MazoMock;

public class JugadorTest {

    private Jugador jugador;
    private MazoMock mazo;

    @BeforeEach
    public void setUp() {
        jugador = new Jugador("Player1");
        mazo = new MazoMock();
    }

    /**
     * 1. Test que valida la correcta inicialización de un jugador. Verifica que el nombre del jugador se asigne 
     * correctamente, que la mano del jugador no sea nula y que esté vacía al inicio. También comprueba que se 
     * lanza una excepción si se intenta crear un jugador con un nombre nulo.
     */
    @Test
    public void testConstructor() {
        assertEquals("Player1", jugador.getNombre(), "El nombre del jugador debería coincidir con el nombre asignado en la inicialización");
        assertNotNull(jugador.getMano(), "La mano del jugador no debería ser nula tras la inicialización");
        assertTrue(jugador.getMano().isEmpty(), "La mano del jugador debería estar vacía al inicio");

        assertThrows(AssertionError.class, () -> new Jugador(null), "No se debería poder introducir null como nombre");
    }

    /**
     * 2. Test que verifica que el jugador pueda robar una carta del mazo.
     * Comprueba que después de robar una carta, la mano del jugador aumente en tamaño.
     */
    @Test
    public void testRobarCarta() {
        int tamanoInicialMano = jugador.getMano().size();
        jugador.robarCarta(mazo);
        
        assertEquals(tamanoInicialMano + 1, jugador.getMano().size(), "El jugador debería tener una carta más tras robar");
    }

    /**
     * 3. Test para verificar que se lance una excepción si se intenta 
     * robar una carta cuando el mazo es null o cuando el mazo ya está vacío.
     */
    @Test
    public void testRobarCartaError() {
        assertThrows(AssertionError.class, () -> jugador.robarCarta(null), "No se debería poder introducir null como mazo");
    
        mazo.robar();
        assertThrows(AssertionError.class, () -> jugador.robarCarta(mazo), "No se debería poder introducir null como mazo");
    }

    /**
     * 4. Test que verifica si el jugador puede jugar una carta válida.
     * Comprueba que la carta se juegue correctamente, que se actualice la última carta jugada,
     * y que la mano del jugador se actualice (se elimine la carta jugada).
     */
    @Test
    public void testJugarCartaValida() {
        Carta cartaValida = new Carta("r", "5");
        mazo.definirCartaParaRobar(cartaValida);
        jugador.robarCarta(mazo);
        
        mazo.actualizarUltimaCartaJugada(new Carta("r", "2"));  // Establecemos una carta compatible en el mazo

        int cantidadAntes = jugador.getMano().size();
        int FrecuanciaAntes = Collections.frequency(jugador.getMano(), cartaValida);

        assertTrue(jugador.jugarCarta(cartaValida, mazo), "El jugador debería poder jugar una carta compatible");
        
        int cantidadDespues = jugador.getMano().size();
        int FrecuanciaDespues = Collections.frequency(jugador.getMano(), cartaValida);
        
        assertEquals(cartaValida, mazo.obtenerUltimaCartaJugada(), "La carta jugada debería ser la última carta en el mazo");
        assertFalse(jugador.getMano().contains(cartaValida), "La carta jugada debería ser removida de la mano del jugador");
        assertTrue(cantidadDespues == cantidadAntes - 1, "Solo una carta deberia eliminarse");
        assertTrue(FrecuanciaDespues < FrecuanciaAntes, "La carta jugada no debe estar en la mano");
    }

    /**
     * 5. Test que verifica que el jugador no pueda jugar una carta inválida.
     * Si la carta no es compatible con la última carta jugada, debe devolverse `false` y 
     * la carta no debe ser removida de la mano del jugador.
     */
    @Test
    public void testJugarCartaInvalida() {
        Carta cartaInvalida = new Carta("b", "5");  // Carta incompatible con la última en el mazo
        mazo.definirCartaParaRobar(cartaInvalida);
        jugador.robarCarta(mazo);
        
        mazo.actualizarUltimaCartaJugada(new Carta("r", "2"));

        int cantidadAntes = jugador.getMano().size();
        int FrecuanciaAntes = Collections.frequency(jugador.getMano(), cartaInvalida);


        assertFalse(jugador.jugarCarta(cartaInvalida, mazo), "El jugador no debería poder jugar una carta incompatible");
                
        int cantidadDespues = jugador.getMano().size();
        int FrecuanciaDespues = Collections.frequency(jugador.getMano(), cartaInvalida);
        
        assertNotEquals(cartaInvalida, mazo.obtenerUltimaCartaJugada(), "La carta jugada debería ser la última carta en el mazo");
        assertTrue(jugador.getMano().contains(cartaInvalida), "La carta incompatible debería permanecer en la mano del jugador");
        assertTrue(cantidadDespues == cantidadAntes, "Solo una carta deberia eliminarse");
        assertTrue(FrecuanciaDespues == FrecuanciaAntes, "La carta jugada no debe estar en la mano");
    }

    /**
     * 6. Test para comprobar que se lanza una excepción cuando se pasan parámetros nulos 
     * al intentar jugar una carta (como carta o mazo nulo).
     */
    @Test
    public void testJugarCartaError() {
        Carta carta = new Carta(null, "wild");

        assertThrows(AssertionError.class, () -> jugador.jugarCarta(null, mazo), "No se debería poder introducir null como carta");
        assertThrows(AssertionError.class, () -> jugador.jugarCarta(carta, null), "No se debería poder introducir null como mazo");

        Carta cartaNoMano = new Carta("b", "5");

        assertThrows(AssertionError.class, () -> jugador.jugarCarta(cartaNoMano, mazo), "No se debería poder jugar una carta que no se tiene en la mano");

        Carta cartaValida = new Carta("r", "2");
        mazo.definirCartaParaRobar(cartaValida);
        jugador.robarCarta(mazo);
    }
}
