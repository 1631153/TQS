package test.ControllerTest;

import main.Controller.JuegoController;
import main.Model.Carta;
import main.Model.Jugador;
import main.Model.Partida;
import main.View.ConsolaView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JuegoControllerTest {

    private JuegoController juegoController;

    @Mock
    private Partida partidaMock;

    @Mock
    private ConsolaView interfazMock;

    @Mock
    private Jugador jugadorMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        juegoController = new JuegoController();
        juegoController.setPartida(partidaMock);
        juegoController.setInterfaz(interfazMock);
    }

    // Tests relacionados al menu principal
    @Test
    void testMostrarMenu_Opcion3() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje del Menú Principal
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Principal ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Modo Offline (local)");
        inOrder.verify(interfazMock).mostrarMensaje("2. Modo Online (en desarrollo)");
        inOrder.verify(interfazMock).mostrarMensaje("3. Salir");
        inOrder.verify(interfazMock).mostrarMensaje("Selecciona una opción: ");

        // Para finalizar el test siempre hay que finalizar el metodo, es por este motivo 
        // que siempre incluyo la opcion 3 para salir del metodo.

        inOrder.verify(interfazMock).mostrarMensaje("Gracias por jugar. ¡Hasta la próxima!");
    }

    @Test
    void testMostrarMenu_Opcion2() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("2")    // Entrar en menu online (todavia en desarollo y posiblemente no de tiempo)
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de que todavía está en desarrollo
        inOrder.verify(interfazMock).mostrarMensaje("El modo online todavía está en desarrollo.");

        // Verificar que se volvio al Menú Principal
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Principal ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Modo Offline (local)");
        inOrder.verify(interfazMock).mostrarMensaje("2. Modo Online (en desarrollo)");
        inOrder.verify(interfazMock).mostrarMensaje("3. Salir");
    }

    @Test
    void testMostrarMenu_OpcinoNoValido() throws Exception {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("0")    // Valor no valido
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de aviso de no vaildo
        inOrder.verify(interfazMock).mostrarMensaje("Opción no válida. Por favor, selecciona una opción del menú.");

        // Verificar que se volvio al Menú Principal
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Principal ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Modo Offline (local)");
        inOrder.verify(interfazMock).mostrarMensaje("2. Modo Online (en desarrollo)");
        inOrder.verify(interfazMock).mostrarMensaje("3. Salir");
    }

    // Tests relacionados al menu offline
    @Test
    void testMostrarMenu_Opcion1_Opcion3() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje del Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");

        // Verificar que se volvio al Menú Principal
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Principal ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Modo Offline (local)");
        inOrder.verify(interfazMock).mostrarMensaje("2. Modo Online (en desarrollo)");
        inOrder.verify(interfazMock).mostrarMensaje("3. Salir");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion2() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("2")    // Cargar partida
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de cargar partida (en desarollo)
        inOrder.verify(interfazMock).mostrarMensaje("Proximamente, todavia en desarollo");

        // Verificar que se mostró el mensaje del Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }


    @Test
    void testMostrarMenu_Opcion1_OpcionNoValido() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("0")    // Valor no valido
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de aviso de no vaildo
        inOrder.verify(interfazMock).mostrarMensaje("Opción no válida. Por favor, selecciona una opción del menú.");

        // Verificar que se volvio al Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion1_Salir() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("1")    // Iniciar nueva partida
        .thenReturn("S")    // Elegir 2 jugadores (en offline no se introduce nombres)
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de seleccion de numero jugadores 
        inOrder.verify(interfazMock).mostrarMensaje("Introduce el número de jugadores (2-4), o (S) para salir: ");

        // Verificar que se volvio al Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion1_Introducir0y5() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("1")    // Iniciar nueva partida
        .thenReturn("0")    // Elegir 0 jugadores (es un numero pero es menor de 2 jugadores)
        .thenReturn("\n")   // Se deberia de introducir (enter) para quitar los avisos, esto sirve para
                                  // dar tiempo a leer el aviso y para poder limpiar la escena de forma conciente
        .thenReturn("5")    // Elegir 5 jugadores (es un numero pero es mayor de 4 jugadores)
        .thenReturn("S")    // Salir del selector
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de numero no valido por ser menor
        inOrder.verify(interfazMock).mostrarMensaje("Por favor, introduce un número válido entre 2 y 4, o (S) para salir: ");
        // Verificar que se mostró el mensaje de numero no valido por ser mayor
        inOrder.verify(interfazMock).mostrarMensaje("Por favor, introduce un número válido entre 2 y 4, o (S) para salir: ");

        // Verificar que se volvio al Menú Offline (nunca esta de mas)
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion1_IntroducirX() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("1")    // Iniciar nueva partida
        .thenReturn("X")    // Elegir X jugadores (no es un numero y tampoco (S) para salir)
        .thenReturn("S")    // Salir del selector
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de entrada no valida por no ser un numero ni (S)
        inOrder.verify(interfazMock).mostrarMensaje("Entrada no válida. Introduce un número entre 2 y 4, o (S) para salir: ");

        // Verificar que se volvio al Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion1_2Jugadores_SalirSinGuardar() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("1")    // Iniciar nueva partida
        .thenReturn("2")    // Elegir 2 jugadores (en offline no se introduce nombres)
        .thenReturn("S")    // Salir de la partida (deberia de poderse dejar en cualquier momento)
        .thenReturn("N")    // Elegir no guardar
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se muestra la interfaz para jugar el turno
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");

        // Verificar que se mostró el mensaje de seleccion de carta
        inOrder.verify(interfazMock).mostrarMensaje("Escribe un número para jugar una carta, '+' para robar una carta, o 'S' para salir: ");

        // Verificar que se mostró el mensaje de guardar partida
        inOrder.verify(interfazMock).mostrarMensaje("¿Deseas guardar la partida antes de salir? (S/N): ");

        // Verificar que se puede salir sin guardar partida (no mostrar el mensaje de guardar partida)
        inOrder.verify(interfazMock, never()).mostrarMensaje("Proximamente, todavia en desarollo");
        inOrder.verify(interfazMock).mostrarMensaje("Saliendo de la partida...");

        // Verificar que se volvio al Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    @Test
    void testMostrarMenu_Opcion1_Opcion1_2Jugadores_SalirGuardando() {
        // Simular la interacción en el menú
        when(interfazMock.solicitarAccion())
        .thenReturn("1")    // Entrar en menu offline
        .thenReturn("1")    // Iniciar nueva partida
        .thenReturn("2")    // Elegir 2 jugadores (en offline no se introduce nombres)
        .thenReturn("S")    // Salir de la partida (deberia de poderse dejar en cualquier momento)
        .thenReturn("S")    // Elegir guardar (todavia no implementado, tengo que buscar como hacerlo para poder hacer el test)
        .thenReturn("3");   // Salir

        // Ejecutar el método mostrarMenu
        juegoController.mostrarMenu();

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se mostró el mensaje de seleccion de carta 
        inOrder.verify(interfazMock).mostrarMensaje("Escribe un número para jugar una carta, '+' para robar una carta, o 'S' para salir: ");

        // Verificar que se mostró el mensaje de guardar partida
        inOrder.verify(interfazMock).mostrarMensaje("¿Deseas guardar la partida antes de salir? (S/N): ");

        // Verificar que se puede salir guardardo partida (en desarrollo)
        inOrder.verify(interfazMock).mostrarMensaje("Proximamente, todavia en desarollo");
        inOrder.verify(interfazMock).mostrarMensaje("Saliendo de la partida...");

        // Verificar que se volvio al Menú Offline
        inOrder.verify(interfazMock).mostrarMensaje("=== Menú Offline ===");
        inOrder.verify(interfazMock).mostrarMensaje("1. Iniciar nueva partida");
        inOrder.verify(interfazMock).mostrarMensaje("2. Cargar partida guardada");
        inOrder.verify(interfazMock).mostrarMensaje("3. Regresar al menú principal");
    }

    //Apartir de aqui uso spy para poder usar metodos privados y simular situaciones que se deben cumplir

    @Test
    void testIniciarPartida() throws Exception {
        // Usamos reflexión para invocar el método privado `iniciarPartida`
        Method metodoIniciarPartida = JuegoController.class.getDeclaredMethod("iniciarNuevaPartidaOffline");
        metodoIniciarPartida.setAccessible(true);

        when(interfazMock.solicitarAccion())
        .thenReturn("2")
        .thenReturn("n")
        .thenReturn("n")
        .thenReturn("S")
        .thenReturn("N");

        // Ejecutar el método privado iniciarPartida
        metodoIniciarPartida.invoke(juegoController);

        // Verificar que se inicializó la partida con 2 jugadores
        verify(interfazMock).mostrarMensaje("¡Gracias por jugar!");
    }

    // Test relacionados a jugar turno
    @Test
    void testJugarTurno_robarCarta() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida y entrada de usuario
        Jugador jugadorMock = mock(Jugador.class);
        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(interfazMock.solicitarAccion()).thenReturn("+");

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que se llamó a robarCartaJugadorActual
        verify(partidaMock).robarCartaJugadorActual();
        verify(interfazMock).mostrarMensaje("Has robado una carta.");
    }

    @Test
    void testJugarTurno_jugarCartaValida1() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getColor()).thenReturn(null);
        when(cartaMock.getValor()).thenReturn("wild");

        List<Carta> manoMock = new ArrayList<>();
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion()).thenReturn("1");
        when(partidaMock.jugarCarta(cartaMock, null)).thenReturn(true);

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que se jugó la carta
        verify(partidaMock).jugarCarta(cartaMock, null);
    }

    @Test
    void testJugarTurno_jugarCartaValida2() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getColor()).thenReturn(null);
        when(cartaMock.getValor()).thenReturn("+4");

        List<Carta> manoMock = new ArrayList<>();
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion()).thenReturn("1");
        when(partidaMock.jugarCarta(cartaMock, null)).thenReturn(true);

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que se jugó la carta
        verify(partidaMock).jugarCarta(cartaMock, null);
    }

    @Test
    void testJugarTurno_jugarCartaValida3() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getColor()).thenReturn("r");
        when(cartaMock.getValor()).thenReturn("4");

        List<Carta> manoMock = new ArrayList<>();
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion()).thenReturn("1");
        when(partidaMock.jugarCarta(cartaMock, null)).thenReturn(true);

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que se jugó la carta
        verify(partidaMock).jugarCarta(cartaMock, null);
    }


    @Test
    void testJugarTurno_jugarCartaInvalida() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);
    
        // Simulación de la última carta jugada
        Carta cartaMockInvalida = mock(Carta.class);
        when(cartaMockInvalida.getColor()).thenReturn("b");
        when(cartaMockInvalida.getValor()).thenReturn("2");
        when(partidaMock.obtenerUltimaCartaJugada()).thenReturn(cartaMockInvalida);
            
        // Configuración del mock para simular una partida con cartas no jugables
        Jugador jugadorMock = mock(Jugador.class);

        // Añadimos la carta no valida a la mano del jugador
        Carta cartaMock = mock(Carta.class);
        List<Carta> manoMock = new ArrayList<>();
        when(cartaMock.getColor()).thenReturn("r");
        when(cartaMock.getValor()).thenReturn("1");
        manoMock.add(cartaMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);

        // Configuración de las respuestas de las acciones del jugador
        when(interfazMock.solicitarAccion()).thenReturn("1");
    
        // Configuración de las respuestas del método jugarCarta
        when(partidaMock.jugarCarta(cartaMock, null)).thenReturn(false);  // Carta no válida
    
        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);
    
        // Verificar el flujo de ejecución
        verify(partidaMock).jugarCarta(cartaMock, null); // Verificar que intentó jugar la carta no válida
        verify(interfazMock).mostrarMensaje("Carta no válida."); // Verificar que mostró el mensaje de carta no válida
    }

    @Test
    void testJugarTurno_jugarCartaFueraDeMano1() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        List<Carta> manoMock = new ArrayList<>();
        when(cartaMock.getColor()).thenReturn(null);
        when(cartaMock.getValor()).thenReturn("wild");
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion())
        .thenReturn("0");

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se jugó la carta
        inOrder.verify(interfazMock).mostrarMensaje("Número de carta no válido."); // Verificar que mostró el mensaje de carta no válida
    }

    @Test
    void testJugarTurno_jugarCartaFueraDeMano2() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        List<Carta> manoMock = new ArrayList<>();
        when(cartaMock.getColor()).thenReturn(null);
        when(cartaMock.getValor()).thenReturn("wild");
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion())
        .thenReturn("2");
        
        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que los mensajes ocurrieron en este orden específico
        InOrder inOrder = inOrder(interfazMock);

        // Verificar que se jugó la carta
        inOrder.verify(interfazMock).mostrarMensaje("Número de carta no válido."); // Verificar que mostró el mensaje de carta no válida
    }

    @Test
    void testJugarTurno_jugarCartaNoNumerica() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida con una carta jugable
        Jugador jugadorMock = mock(Jugador.class);
        Carta cartaMock = mock(Carta.class);
        List<Carta> manoMock = new ArrayList<>();
        when(cartaMock.getColor()).thenReturn(null);
        when(cartaMock.getValor()).thenReturn("wild");
        manoMock.add(cartaMock);

        when(partidaMock.getJugadorActual()).thenReturn(jugadorMock);
        when(jugadorMock.getMano()).thenReturn(manoMock);
        when(interfazMock.solicitarAccion())
        .thenReturn("X");

        // Ejecutar el método privado jugarTurno
        metodoJugarTurno.invoke(juegoController);

        // Verificar que se jugó la carta
        verify(interfazMock).mostrarMensaje("Acción no válida."); // Verificar que mostró el mensaje de carta no válida
    }

    @Test
    void testJugarTurno_jugadorGana() throws Exception {
        // Usamos reflexión para invocar el método privado `jugarTurno`
        Method metodoJugarTurno = JuegoController.class.getDeclaredMethod("jugarTurno");
        metodoJugarTurno.setAccessible(true);

        // Configuración del mock para simular una partida
        Jugador ganadorMock = mock(Jugador.class); // El jugador que está a punto de ganar
        Carta cartaMock = mock(Carta.class);       // Última carta del jugador
        List<Carta> manoMock = new ArrayList<>();  // El jugador solo tiene una carta
        manoMock.add(cartaMock);

        // Configurar la carta y el jugador
        when(cartaMock.getColor()).thenReturn("red");
        when(cartaMock.getValor()).thenReturn("4");
        when(partidaMock.getJugadorActual()).thenReturn(ganadorMock);
        when(ganadorMock.getMano()).thenReturn(manoMock);

        // Simular que el jugador elige la carta correcta para ganar
        when(interfazMock.solicitarAccion()).thenReturn("1");
        when(partidaMock.jugarCarta(cartaMock, null)).thenReturn(true);

        // Simular el estado de la partida antes y después del turno
        when(partidaMock.esFinPartida()).thenReturn(true); // La partida finaliza después de este turno

        // Ejecutar el método privado jugarTurno
        boolean resultado = (boolean) metodoJugarTurno.invoke(juegoController);

        // Verificar que el flujo indica que la partida ha terminado
        assertFalse(resultado, "El método jugarTurno no indicó correctamente que la partida terminó.");

        // Verificar interacciones clave
        verify(partidaMock).jugarCarta(cartaMock, null); // Verificar que se intentó jugar la carta
        verify(partidaMock).esFinPartida();             // Verificar que se comprobó si la partida terminó
    }

    @Test
    void testVerificarFinDelJuego_ganador() throws Exception {
        // Usamos reflexión para invocar el método privado `verificarFinDelJuego`
        Method metodoVerificarFinDelJuego = JuegoController.class.getDeclaredMethod("verificarFinDelJuego");
        metodoVerificarFinDelJuego.setAccessible(true);

        // Simular que la partida ha terminado y un jugador ha ganado
        Jugador ganadorMock = mock(Jugador.class);
        List<Jugador> jugadoresMock = new ArrayList<>();
        jugadoresMock.add(ganadorMock);

        when(partidaMock.esFinPartida()).thenReturn(true);
        when(partidaMock.getJugadores()).thenReturn(jugadoresMock);
        when(ganadorMock.getMano()).thenReturn(new ArrayList<>());

        // Ejecutar el método privado verificarFinDelJuego
        boolean finDelJuego = (boolean) metodoVerificarFinDelJuego.invoke(juegoController);

        // Verificar que se detectó el fin de la partida y se mostró el ganador
        assertTrue(finDelJuego, "La partida no detectó correctamente el fin del juego.");
        verify(interfazMock).mostrarGanador(ganadorMock);
    }
}
