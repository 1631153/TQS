package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testRobarCarta() {
        int tamanoInicialMano = jugador.getMano().size();
        jugador.robarCarta(mazo);
        
        assertEquals(tamanoInicialMano + 1, jugador.getMano().size(), "El jugador debería tener una carta más tras robar");
    }

    @Test
    public void testJugarCartaValida() {
        Carta cartaValida = new Carta("r", "5");
        mazo.definirCartaParaRobar(cartaValida);
        jugador.robarCarta(mazo);
        
        mazo.actualizarUltimaCartaJugada(new Carta("r", "2"));  // Establecemos una carta compatible en el mazo

        assertTrue(jugador.jugarCarta(cartaValida, mazo), "El jugador debería poder jugar una carta compatible");
        assertFalse(jugador.getMano().contains(cartaValida), "La carta jugada debería ser removida de la mano del jugador");
        assertEquals(cartaValida, mazo.obtenerUltimaCartaJugada(), "La carta jugada debería ser la última carta en el mazo");
    }

    @Test
    public void testJugarCartaInvalida() {
        Carta cartaInvalida = new Carta("b", "5");  // Carta incompatible con la última en el mazo
        mazo.definirCartaParaRobar(cartaInvalida);
        jugador.robarCarta(mazo);
        
        mazo.actualizarUltimaCartaJugada(new Carta("r", "2"));

        assertFalse(jugador.jugarCarta(cartaInvalida, mazo), "El jugador no debería poder jugar una carta incompatible");
        assertTrue(jugador.getMano().contains(cartaInvalida), "La carta incompatible debería permanecer en la mano del jugador");

        Carta cartaNoMano = new Carta("r", "5");

        assertFalse(jugador.jugarCarta(cartaNoMano, mazo),  "No se deberia poder jugar una carta que no se tiene en la mano");
    }

    @Test
    public void testDecirUNOConUnaCarta() {
        jugador.robarCarta(mazo); // Agregar una carta a la mano
        assertEquals(1, jugador.getMano().size(), "El jugador debería tener exactamente una carta.");
        
        jugador.decirUNO();
        assertTrue(jugador.haDichoUNO(), "El jugador debería haber dicho 'UNO' al tener una sola carta.");
    }

    @Test
    public void testDecirUNOConMasDeUnaCarta() {
        jugador.robarCarta(mazo);
        jugador.robarCarta(mazo); // Agregar dos cartas a la mano
        
        assertThrows(IllegalStateException.class, () -> {
            jugador.decirUNO();
        },"El jugador no debería poder decir 'UNO' con más de una carta.");
    }

    @Test
    public void testRobarCartaReseteaHaDichoUNO() {
        jugador.robarCarta(mazo);
        jugador.decirUNO(); // Decir "UNO" cuando tiene una sola carta
        assertTrue(jugador.haDichoUNO(), "El jugador debería haber dicho 'UNO'.");
        
        jugador.robarCarta(mazo); // Robar otra carta debería resetear el estado
        assertFalse(jugador.haDichoUNO(), "El jugador no debería haber dicho 'UNO' después de robar una carta.");
    }

    @Test
    public void testGetNombre() {
        assertEquals("Player1", jugador.getNombre(), "El nombre del jugador debería coincidir con el nombre asignado en la inicialización");
    }

    @Test
    public void testGetMano() {
        assertNotNull(jugador.getMano(), "La mano del jugador no debería ser nula tras la inicialización");
        assertTrue(jugador.getMano().isEmpty(), "La mano del jugador debería estar vacía al inicio");
    }
}
