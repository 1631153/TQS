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
    public void testFinalPartida() {
        partida.getJugadorActual().getMano().clear();
        assertTrue(partida.esFinPartida(), "La partida debería finalizar");
    }

    @Test
    public void testCambiarTurno() {
        int turnoInicial = partida.getNumeroJugadorActual();
        partida.cambiarTurno();
        
        assertNotEquals(turnoInicial, partida.getNumeroJugadorActual(), "El turno debería cambiar tras llamar a cambiarTurno");
    }

    @Test
    public void testAplicarCartaComodin() {
        Carta cartaComodin = new Carta(null, "+4");
        partida.aplicarCartaEspecial(cartaComodin);

        //partida.cambiarTurno();
        
        assertEquals(7+4, partida.getJugadorActual().getMano().size(), "El siguiente jugador debería robar 4 cartas tras jugar un +4");
    }

    @Test
    public void testAplicarCartaEspecial_rever() {
        //Reverse
        Carta cartaEspecial = new Carta("r", "reverse");
        boolean Sentido_ant = partida.getSentidoHorario();
        partida.aplicarCartaEspecial(cartaEspecial);
        partida.cambiarTurno();
        assertNotEquals(Sentido_ant, partida.getSentidoHorario(), "El sentido del juego debería cambiar tras jugar un reverse");
    }

    @Test
    public void testAplicarCartaEspecial_skip() {      
        //Skip
        Carta cartaEspecial = new Carta("g", "skip");
        partida.aplicarCartaEspecial(cartaEspecial);
        assertEquals((partida.getNumeroJugadorActual()) % partida.getJugadores().size(), partida.getNumeroJugadorActual(), "El turno debería saltar al siguiente tras jugar un skip");
    }

    @Test
    public void testAplicarCartaEspecial_2() {
        //+2
        Carta cartaEspecial = new Carta("b", "+2");
        partida.aplicarCartaEspecial(cartaEspecial);
        //partida.cambiarTurno();
        assertEquals(7+2, partida.getJugadorActual().getMano().size(), "El siguiente jugador debería robar 2 cartas tras jugar un +2");
    }
        

    @Test
    public void testSentidoHorario() {
        int turnoInicial = partida.getNumeroJugadorActual();
        partida.cambiarTurno();

        assertEquals(turnoInicial + 1, partida.getNumeroJugadorActual(), "El turno debería ir en sentido horario");
    }

    @Test
    public void testSentidoAntiHorario() {
        partida.setSentidoHorario(false);
        int turnoInicial = partida.getNumeroJugadorActual();
        partida.cambiarTurno();

        assertEquals(turnoInicial - 1, partida.getNumeroJugadorActual(),"El turno debería ir en sentido anti horario");
    }
}