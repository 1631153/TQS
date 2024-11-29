package main.View;

import main.Model.Carta;
import main.Model.Jugador;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class ConsolaView implements InterfazJuego {

    private Scanner scanner;

    // Constructor
    public ConsolaView() {
        this.scanner = new Scanner(System.in); // Usa el flujo estándar por defecto
    }

    // Constructor que permite configurar un flujo de entrada alternativo
    public ConsolaView(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void mostrarEstadoPartida(
        List<Jugador> jugadores, 
        Carta ultimaCarta, 
        String colorComodin, 
        Jugador jugadorActual
    ) {
        // Limpiar la pantalla
        clearScreen();

        // Mostrar el encabezado de la sección
        mostrarMensaje("===============================");
        mostrarMensaje("       Estado de Partida       ");
        mostrarMensaje("===============================");

        // Mostrar la lista de jugadores y sus manos
        mostrarMensaje("Jugadores:");
        for (Jugador jugador : jugadores) {
            String mano = "Cartas en mano: " + jugador.getMano().size();
            if (jugador.equals(jugadorActual)) {
                mostrarMensaje("-> " + jugador.getNombre() + " (Tu turno) - " + mano);
            } else {
                mostrarMensaje("- " + jugador.getNombre() + " - " + mano);
            }
        }

        // Mostrar quién tiene el turno
        mostrarMensaje("Es el turno de: " + jugadorActual.getNombre());

        // Mostrar la última carta jugada
        String color = null;
        String valor = null;
        if (ultimaCarta != null) {
            mostrarMensaje("Carta en juego: " + obtenerColorCompleto(ultimaCarta.getColor()) + " " + ultimaCarta.getValor());
            // Mostrar el color del comodín, si aplica
            if (ultimaCarta.getColor() == null) {
                mostrarMensaje("El color activo es: " + obtenerColorCompleto(colorComodin));
                color = colorComodin;
            }
            else {
                color = ultimaCarta.getColor();
            }
            valor = ultimaCarta.getValor();
        } else {
            mostrarMensaje("Carta en juego: No hay carta en juego.");
        }

        mostrarMensaje("===============================");
        mostrarEstado(jugadorActual.getMano(), color, valor);
    }

    @Override
    public void mostrarEstado(List<Carta> cartasMano, String color, String valor) {
        System.out.println("Cartas en mano:");

        // Mostrar cada carta con un formato alineado
        for (int i = 0; i < cartasMano.size(); i++) {
            Carta carta = cartasMano.get(i);
            String colorCompleto = obtenerColorCompleto(carta.getColor());
            // Asegura que la posición, color y valor estén alineados correctamente
            System.out.printf("%-4d -> [%-9s | %-5s]%n", i + 1, colorCompleto, carta.getValor());
        }
    }

    @Override
    public void mostrarGanador(Jugador jugador) {
        System.out.println("¡" + jugador.getNombre() + " ha ganado la partida!");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String solicitarAccion() {
        return scanner.nextLine().trim();
    }

    @Override
    public String solicitarColorComodin() {
        String colorSeleccionado = "";
        boolean entradaValida = false;
    
        // Lista de colores válidos
        String[] coloresValidos = {"r", "b", "g", "y"};
    
        System.out.println("Elige un color: (r = red, b = blue, g = green, y = yellow)");

        // Continuar solicitando hasta que la entrada sea válida
        while (!entradaValida) {
            // Leer la entrada del usuario y eliminar espacios en blanco
            colorSeleccionado = scanner.nextLine().trim().toLowerCase();
    
            // Verificar si la entrada es válida
            for (String color : coloresValidos) {
                if (colorSeleccionado.equals(color)) {
                    entradaValida = true;
                    break;
                }
            }
    
            clearScreen();

            // Si la entrada no es válida, mostrar un mensaje y seguir pidiendo una entrada
            if (!entradaValida) {
                System.out.println("Entrada no válida. Por favor, elige un color válido: (r = red, b = blue, g = green, y = yellow)");
            }
        }
    
        return colorSeleccionado;
    }

    @Override
    public void clearScreen() {
        try {
            // Windows
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
            pb.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            // Si ocurre algún error al ejecutar el comando
            System.err.println("No se pudo limpiar la pantalla.");
        }
    }

    @Override
    public void mostrarCarta(Carta carta) {
        if (carta == null) {
            System.out.println("No hay carta para mostrar.");
            return;
        }

        // Obtener el nombre completo del color de la carta
        String colorCompleto = obtenerColorCompleto(carta.getColor());

        // Imprimir la carta en formato alineado
        System.out.printf("Carta: [%-9s | %-5s]%n", colorCompleto, carta.getValor());
    }

    /**
     * Método para convertir los colores abreviados a su nombre completo
     */
    public String obtenerColorCompleto(String color) {
        if (color == null) {
            return "AllColors";
        }
        switch (color.toLowerCase()) {
            case "r":
                return "Red";
            case "b":
                return "Blue";
            case "g":
                return "Green";
            case "y":
                return "Yellow";
            default:
                return "Error"; // Si no es un color válido, lo devuelve un string de error
        }
    }
}