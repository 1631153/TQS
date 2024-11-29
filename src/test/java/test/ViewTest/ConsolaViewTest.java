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
    @Mock
    private Jugador jugadorMock;

    @Mock
    private Carta cartaMock;

    private ConsolaView consolaView;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        consolaView = new ConsolaView(); // Usamos el constructor por defecto

        // Captura la salida estándar
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testMostrarEstadoPartida() {
        // Crear mocks para los jugadores y las cartas
        Jugador jugador1Mock = mock(Jugador.class);
        Jugador jugador2Mock = mock(Jugador.class);
        Jugador jugadorActualMock = mock(Jugador.class);
        Carta cartaEnJuegoMock = mock(Carta.class);

        // Configurar los mocks
        when(jugador1Mock.getNombre()).thenReturn("Alice");
        when(jugador1Mock.getMano()).thenReturn(List.of(mock(Carta.class), mock(Carta.class), mock(Carta.class)));

        when(jugador2Mock.getNombre()).thenReturn("Bob");
        when(jugador2Mock.getMano()).thenReturn(List.of(mock(Carta.class), mock(Carta.class), mock(Carta.class), mock(Carta.class), mock(Carta.class)));

        when(jugadorActualMock.getNombre()).thenReturn("Charlie");
        when(jugadorActualMock.getMano()).thenReturn(List.of(mock(Carta.class), mock(Carta.class)));

        // Llamar al método a probar con los parámetros correspondientes
        consolaView.mostrarEstadoPartida(
            List.of(jugador1Mock, jugador2Mock, jugadorActualMock),
            null,
            null,
            jugadorActualMock
        );

        // Verificar que se muestra el mensaje "Cartas en mano"
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("Alice"));
        assertTrue(outputStreamCaptor.toString().contains("Bob"));
        assertTrue(outputStreamCaptor.toString().contains("Charlie"));
        assertTrue(outputStreamCaptor.toString().contains("Carta en juego: No hay carta en juego."));

        when(cartaEnJuegoMock.getColor()).thenReturn("r");
        when(cartaEnJuegoMock.getValor()).thenReturn("5");

        // Llamar al método a probar con los parámetros correspondientes
        consolaView.mostrarEstadoPartida(
            List.of(jugador1Mock, jugador2Mock, jugadorActualMock),
            cartaEnJuegoMock,
            "g",
            jugadorActualMock
        );

        // Verificar que se muestra el mensaje "Cartas en mano"
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("Alice"));
        assertTrue(outputStreamCaptor.toString().contains("Bob"));
        assertTrue(outputStreamCaptor.toString().contains("Charlie"));
        assertTrue(outputStreamCaptor.toString().contains("Red 5"));
        assertFalse(outputStreamCaptor.toString().contains("El color activo es: Green"));

        when(cartaEnJuegoMock.getColor()).thenReturn(null);
        when(cartaEnJuegoMock.getValor()).thenReturn("+4");

        // Llamar al método a probar con los parámetros correspondientes
        consolaView.mostrarEstadoPartida(
            List.of(jugador1Mock, jugador2Mock, jugadorActualMock),
            cartaEnJuegoMock,
            "g",
            jugadorActualMock
        );

        // Verificar que se muestra el mensaje "Cartas en mano"
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("Alice"));
        assertTrue(outputStreamCaptor.toString().contains("Bob"));
        assertTrue(outputStreamCaptor.toString().contains("Charlie"));
        assertTrue(outputStreamCaptor.toString().contains("AllColors +4"));
        assertTrue(outputStreamCaptor.toString().contains("El color activo es: Green"));
    }

    @Test
    public void testMostrarEstado() {
        // Red
        when(cartaMock.getColor()).thenReturn("r");
        when(cartaMock.getValor()).thenReturn("5");

        List<Carta> cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, "r", "5");

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("1 "));
        assertTrue(outputStreamCaptor.toString().contains("-> [Red "));
        assertTrue(outputStreamCaptor.toString().contains("| 5 "));
        assertTrue(outputStreamCaptor.toString().contains(" ]"));

        // Blue
        when(cartaMock.getColor()).thenReturn("b");
        when(cartaMock.getValor()).thenReturn("1");

        cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, "b", "1");

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("1 "));
        assertTrue(outputStreamCaptor.toString().contains("-> [Blue "));
        assertTrue(outputStreamCaptor.toString().contains("| 1 "));
        assertTrue(outputStreamCaptor.toString().contains(" ]"));

        // Yellow
        when(cartaMock.getColor()).thenReturn("y");
        when(cartaMock.getValor()).thenReturn("3");

        cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, "y", "3");

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("1 "));
        assertTrue(outputStreamCaptor.toString().contains("-> [Yellow "));
        assertTrue(outputStreamCaptor.toString().contains("| 3 "));
        assertTrue(outputStreamCaptor.toString().contains(" ]"));

        // Green
        when(cartaMock.getColor()).thenReturn("g");
        when(cartaMock.getValor()).thenReturn("0");

        cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, "g", "0");

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("1 "));
        assertTrue(outputStreamCaptor.toString().contains("-> [Green "));
        assertTrue(outputStreamCaptor.toString().contains("| 0 "));
        assertTrue(outputStreamCaptor.toString().contains(" ]"));

        // Invalido
        when(cartaMock.getColor()).thenReturn("x");
        when(cartaMock.getValor()).thenReturn("8");

        cartas = Arrays.asList(cartaMock);
        consolaView.mostrarEstado(cartas, "x", "8");

        // Verificaciones
        assertTrue(outputStreamCaptor.toString().contains("Cartas en mano"));
        assertTrue(outputStreamCaptor.toString().contains("1 "));
        assertTrue(outputStreamCaptor.toString().contains("-> [Error "));
        assertTrue(outputStreamCaptor.toString().contains("| 8 "));
        assertTrue(outputStreamCaptor.toString().contains(" ]"));
    }

    @Test
    public void testSolicitarColorComodin_EntradaValida() {
        // Simula la entrada de un color válido ('r' para rojo)
        String input = "r\n"; // 'r' + Enter
        ConsolaView consolaViewColor = new ConsolaView(new ByteArrayInputStream(input.getBytes()));

        String color = consolaViewColor.solicitarColorComodin();

        // Verifica que el color retornado sea "r"
        assertEquals("r", color); // Esperamos que el color sea "r"
    }

    @Test
    public void testSolicitarColorComodin_EntradaInvalida() {
        // Simula la entrada de un color no válido ('x' no es un color válido)
        String input = "x\ny\n"; // Primer intento inválido ('x'), luego válido ('r')
        ConsolaView consolaViewColor = new ConsolaView(new ByteArrayInputStream(input.getBytes()));

        // Llamamos al método, debería pedir el color nuevamente después del valor inválido
        String color = consolaViewColor.solicitarColorComodin();

        // Verifica que el color retornado sea "y" (el segundo valor válido que se ingresó)
        assertEquals("y", color); // Esperamos que el color sea "y"
    }

    @Test
    public void testSolicitarColorComodin_EntradaInvalidaVariasVeces() {
        // Simula la entrada de valores inválidos seguidos de un valor válido
        String input = "a\nz\nb\n"; // 'a' y 'z' son inválidos, 'b' es válido
        ConsolaView consolaViewColor = new ConsolaView(new ByteArrayInputStream(input.getBytes()));

        // Llamamos al método, debería pedir el color varias veces antes de aceptar 'b'
        String color = consolaViewColor.solicitarColorComodin();

        // Verifica que el color retornado sea "b"
        assertEquals("b", color); // Esperamos que el color sea "b" (el valor válido ingresado después de los inválidos)
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

    @Test
    public void testSolicitarAccion() {
        // Simula la entrada de "jugar"
        String input = "jugar\n"; 
        ConsolaView consolaViewAccion = new ConsolaView(new ByteArrayInputStream(input.getBytes()));

        String accion = consolaViewAccion.solicitarAccion();

        assertEquals("jugar", accion); // Verifica que la acción seleccionada es "jugar"
    }
}
