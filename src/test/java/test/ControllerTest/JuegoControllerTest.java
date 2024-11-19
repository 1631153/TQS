/*
package test.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Controller.JuegoController;
import main.Model.*;
import test.Mock.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoControllerTest {
    private JuegoController controller;
    private Partida partida;
    private MazoMock mazoMock;

    @BeforeEach
    public void setUp() {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Player1"));
        jugadores.add(new Jugador("Player2"));
        mazoMock = new MazoMock();
        partida = new Partida();
        controller = new JuegoController(partida, null); // Pasar null si no usas la interfaz en estas pruebas
    }

    @Test
    public void testIniciarJuego() {
        partidaMock.iniciarPartida(2);
        assertNotNull(partidaMock.getJugadorActual(), "El jugador actual debería estar inicializado.");
        assertEquals(7, partidaMock.getJugadorActual().getMano().size(), "El jugador debería comenzar con 7 cartas.");
    }

   
}
     */