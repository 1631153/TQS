package test.ModelTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Model.Partida;
import main.Model.Carta;

public class PartidaTest {

    private Partida partida;

    @BeforeEach
    public void setUp() {
        partida = new Partida();
        partida.iniciarPartida(); // Inicializar la partida con jugadores y mazo
    }

    @Test
    public void testIniciarPartida() {
        assertEquals(4, partida.getJugadores().size(), "La partida debería comenzar con 4 jugadores");
        assertNotNull(partida.getMazo(), "La partida debería tener un mazo inicializado");
    }

   

 @Test
    public void testCambiarTurno() {
        int turnoInicial = partida.getNumeroJugadorActual();
        partida.cambiarTurno();
        
        assertNotEquals(turnoInicial, partida.getNumeroJugadorActual(), "El turno debería cambiar tras llamar a cambiarTurno");
    }

    @Test
    public void testAplicarCartaEspecial() {
        Carta cartaComodin = new Carta(null, "+4");
        partida.aplicarCartaEspecial(cartaComodin);

        partida.cambiarTurno();
        
        assertEquals(4, partida.getJugadorActual().getMano().size(), "El siguiente jugador debería robar 4 cartas tras jugar un +4");
    }
}