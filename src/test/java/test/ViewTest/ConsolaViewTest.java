package test.ViewTest;

import main.Model.Carta;
import main.Model.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import main.View.*;

public class ConsolaViewTest {

    // Mocks
    @Mock private Jugador jugadorMock;
    @Mock private Carta cartaMock;

    private ConsolaView consolaView;
    private ByteArrayOutputStream outputStreamCaptor;
    
    // Inicialización antes de cada test
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        consolaView = new ConsolaView();
        
        // Para capturar lo que se imprime en la consola
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirige la salida a nuestro capturador
    }

    // Test para mostrar el estado de la partida
    @Test
    public void testMostrarEstadoPartida() {
        consolaView.mostrarEstadoPartida();

        // Verificamos que se haya impreso el mensaje de estado de la partida
        assertTrue(outputStreamCaptor.toString().contains("Estado de la partida"));
    }

    // Test para mostrar el turno del jugador
    @Test
    public void testMostrarTurno() {
        when(jugadorMock.getNombre()).thenReturn("Jugador 1");

        consolaView.mostrarTurno(jugadorMock);

        // Verificamos que se haya impreso el turno del jugador
        assertTrue(outputStreamCaptor.toString().contains("Es el turno de Jugador 1"));
    }

    // Test para mostrar el estado del jugador (cartas en mano y última carta jugada)
    @Test
    public void testMostrarEstado() {
        when(cartaMock.getColor()).thenReturn("rojo");
        when(cartaMock.getValor()).thenReturn("5");

        List<Carta> cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, cartaMock);

        // Verificamos que se haya impreso el estado de las cartas
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("Última carta jugada"));
    }

    // Test para mostrar la carta jugada
    @Test
    public void testMostrarCartaJugada() {
        when(cartaMock.getColor()).thenReturn("rojo");
        when(cartaMock.getValor()).thenReturn("5");

        consolaView.mostrarCartaJugada(cartaMock);

        // Verificamos que se haya impreso el mensaje con la carta jugada
        assertTrue(outputStreamCaptor.toString().contains("Jugador 1 ha jugado la carta: rojo 5"));
    }

    // Test para solicitar el color de un comodín
    @Test
    public void testSolicitarColorComodin() {
        // Simulamos la entrada del usuario (por ejemplo, "r" para rojo)
        String input = "r";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes())); // Simula la entrada por consola

        String color = consolaView.solicitarColorComodin();

        // Verificamos que el valor retornado sea el color esperado
        assertEquals("r", color);
    }

    // Test para mostrar el ganador
    @Test
    public void testMostrarGanador() {
        when(jugadorMock.getNombre()).thenReturn("Jugador 1");

        consolaView.mostrarGanador(jugadorMock);

        // Verificamos que se haya impreso el mensaje de ganador
        assertTrue(outputStreamCaptor.toString().contains("¡Jugador 1 ha ganado la partida!"));
    }

    // Test para mostrar un mensaje general
    @Test
    public void testMostrarMensaje() {
        String mensaje = "Se ha producido un error";

        consolaView.mostrarMensaje(mensaje);

        // Verificamos que se haya impreso el mensaje correcto
        assertTrue(outputStreamCaptor.toString().contains(mensaje));
    }
}