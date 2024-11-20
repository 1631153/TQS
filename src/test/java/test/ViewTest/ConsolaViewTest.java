package test.ViewTest;

import main.Model.Carta;
import main.Model.Jugador;
import main.View.ConsolaView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsolaViewTest {

    // Mocks
    @Mock private Jugador jugadorMock;
    @Mock private Carta cartaMock;

    private ConsolaView consolaView;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        consolaView = new ConsolaView();

        // Captura la salida estándar
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testMostrarEstadoPartida() {
        consolaView.mostrarEstadoPartida();
        assertTrue(outputStreamCaptor.toString().contains("Estado de la partida"));
    }

    @Test
    public void testMostrarTurno() {
        when(jugadorMock.getNombre()).thenReturn("Jugador 1");
        consolaView.mostrarTurno(jugadorMock);
        assertTrue(outputStreamCaptor.toString().contains("Es el turno de Jugador 1"));
    }

    @Test
    public void testMostrarEstado() {
        when(cartaMock.getColor()).thenReturn("rojo");
        when(cartaMock.getValor()).thenReturn("5");
        when(cartaMock.toString()).thenReturn("rojo 5");

        List<Carta> cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, cartaMock);

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("rojo 5"));
        assertTrue(outputStreamCaptor.toString().contains("Última carta jugada"));
    }

    @Test
    public void testMostrarCartaJugada() {
        when(cartaMock.getColor()).thenReturn("rojo");
        when(cartaMock.getValor()).thenReturn("5");

        consolaView.mostrarCartaJugada(cartaMock);

        assertTrue(outputStreamCaptor.toString().contains("Se jugó la carta: rojo 5"));
    }

    @Test
    public void testSolicitarColorComodin() {
        // Simula entrada del usuario
        String input = "r\n"; // 'r' + Enter
        ConsolaView consolaViewColor = new ConsolaView(new ByteArrayInputStream(input.getBytes()));

        String color = consolaViewColor.solicitarColorComodin();

        assertEquals("r", color); // Verifica el color retornado
    }

    @Test
    public void testMostrarGanador() {
        when(jugadorMock.getNombre()).thenReturn("Jugador 1");

        consolaView.mostrarGanador(jugadorMock);

        assertTrue(outputStreamCaptor.toString().contains("¡Jugador 1 ha ganado la partida!"));
    }

    @Test
    public void testMostrarMensaje() {
        String mensaje = "Se ha producido un error";
        consolaView.mostrarMensaje(mensaje);
        assertTrue(outputStreamCaptor.toString().contains(mensaje));
    }
}