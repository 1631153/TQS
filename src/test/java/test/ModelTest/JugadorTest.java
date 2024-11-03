package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Carta;
import main.Model.Mazo;
import main.Model.Jugador;

public class JugadorTest {

    private Jugador jugador;
    private Mazo mazo;

    @BeforeEach
    public void setUp() {
        jugador = new Jugador("Player1");
        mazo = new Mazo();
    }

    @Test
    public void testRobarCarta() {
        int tamanoInicialMano = jugador.getMano().size();
        jugador.robarCarta(mazo);
        
        assertEquals(tamanoInicialMano + 1, jugador.getMano().size(), "El jugador debería tener una carta más tras robar");
    }

    @Test
    public void testJugarCartaValida() {
        Carta carta = new Carta("r", "5");
        jugador.getMano().add(carta);
        
        jugador.jugarCarta(carta, mazo);
        
        assertFalse(jugador.getMano().contains(carta), "La carta jugada debería ser removida de la mano del jugador");
        assertEquals(carta, mazo.obtenerUltimaCartaJugada(), "La carta jugada debería estar en la pila de descarte");
    }

    @Test
    public void testDecirUNO() {
        jugador.getMano().add(new Carta("r", "5")); // Simula que le queda una carta
        jugador.decirUNO();
        
        assertTrue(jugador.haDichoUNO(), "El jugador debería haber dicho UNO");
    }

    @Test
    public void testDecirUNO_False() {
        assertFalse(jugador.haDichoUNO(), "El jugador NO debería haber dicho UNO");

        jugador.getMano().add(new Carta("r", "5"));
        jugador.getMano().add(new Carta("b", "7"));
        jugador.decirUNO();
        assertFalse(jugador.haDichoUNO(), "El jugador NO debería haber dicho UNO");
    }

    @Test
    public void testRecibirCarta() {
        Carta carta = new Carta("b", "7");
        jugador.recibirCarta(carta);
        
        assertTrue(jugador.getMano().contains(carta),  "La carta debería estar entre las cartas del jugador");
    }

    @Test
    public void testJugarCartaNOEnMano() {
        Carta carta = new Carta("r", "5");
        Carta cartaNoMano = new Carta("b", "7");

        jugador.getMano().add(new Carta("r", "5"));
        jugador.jugarCarta(cartaNoMano, mazo);

        assertTrue(jugador.getMano().contains(carta),  "La carta en la mano no debería quitarse");
        assertFalse(jugador.getMano().contains(cartaNoMano),  "La carta que no está en la mano no debería afectar la partida");
    }
}
