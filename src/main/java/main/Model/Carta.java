package main.Model;

import java.util.Objects;
import java.io.Serializable;

public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private String color;  // "r" = rojo, "b" = azul, "g" = verde, "y" = amarillo, null = comodín
    private String valor;  // "0" a "9" para números, "skip", "reverse", "+2", "wild", "+4" para especiales

    /*
     * La clase Carta representa una carta en un juego de cartas tipo UNO.
     * Las cartas pueden ser de un color específico o comodines. Los comodines
     * no deben tener color asociado, y su valor será "wild" o "+4".
     * Los valores de las cartas pueden ser números entre "0" y "9", 
     * o cartas especiales como "skip", "reverse", y "+2".
     */

    /**
     * Crea una nueva carta con un color y un valor específicos.
     * 
     * @param color El color de la carta (puede ser "r", "b", "g", "y", o null para comodines).
     * @param valor El valor de la carta (puede ser un número entre "0" y "9", o valores especiales como 
     *              "skip", "reverse", "+2", "wild", "+4").
     * 
     * @throws AssertionError Si el color o valor no son válidos según las reglas del juego.
     */
    public Carta(String color, String valor) {
        // Precondiciones para color y valor:

        // Valida el valor de la carta y asegura que el valor sea uno de los válidos (número o carta especial).
        assert isValorValido(valor) : "Valor no válido: " + valor;

        // Valida el color de la carta y asegura que el color sea válido (si el color es null, debe ser comodín)
        assert (valor.equals("wild") || valor.equals("+4")) ? (color == null) : (color != null && isColorValido(color)) : "El color debe ser válido o null para comodines";

        // Asignar color y valor a la carta
        this.color = color;
        this.valor = valor;
    }

    // Getters

    /**
     * Obtiene el color de la carta.
     * 
     * @return El color de la carta, o null si es un comodín.
     */
    public String getColor() {
        return color;
    }

    /**
     * Obtiene el valor de la carta.
     * 
     * @return El valor de la carta (número o especial).
     */
    public String getValor() {
        return valor;
    }

    /**
     * Verifica si la carta es compatible con otra.
     * 
     * Dos cartas son compatibles si tienen el mismo color o el mismo valor. 
     * Las cartas comodín siempre son compatibles con cualquier otra carta.
     * 
     * @param otraCarta La carta con la que se va a comparar.
     * @return true si las cartas son compatibles, false si no lo son.
     * 
     * @throws AssertionError Si la carta comparada es null.
     */
    public boolean esCompatible(Carta otraCarta) {
        // Precondicion: Se asegura que la carta a comparar no sea null.
        assert (otraCarta != null) : "La carta comparada no puede ser null";
       
        // Verificar compatibilidad
        if (this.color == null || otraCarta.color == null) {
            return true;  // Los comodines son compatibles con cualquier otra carta
        }

        // Dos cartas son compatibles si tienen el mismo color o el mismo valor
        return Objects.equals(this.color, otraCarta.color) || Objects.equals(this.valor, otraCarta.valor);
    }

    /**
     * Verifica si un color es válido en el juego.
     * 
     * Los colores válidos son: "r" (rojo), "b" (azul), "g" (verde), "y" (amarillo).
     * 
     * @param color El color a verificar.
     * @return true si el color es válido, false si no lo es.
     */
    private boolean isColorValido(String color) {
        return color.equals("r") || color.equals("b") || color.equals("g") || color.equals("y");
    }

    /**
     * Verifica si el valor de la carta es válido.
     * 
     * Un valor válido puede ser un número de "0" a "9", o una carta especial como "skip", "reverse", "+2", "wild", "+4".
     * 
     * @param valor El valor de la carta a verificar.
     * @return true si el valor es válido, false si no lo es.
     */
    private boolean isValorValido(String valor) {
        if (valor == null) {
            return false;
        }

        return valor.matches("[0-9]") || isValorEspecial(valor);
    }

    /**
     * Verifica si el valor de la carta es una acción especial.
     * 
     * Las cartas con valores especiales son: "skip", "reverse", "+2", "wild", "+4".
     * 
     * @param valor El valor a verificar.
     * @return true si el valor es una carta especial, false si no lo es.
     */
    public boolean isValorEspecial(String valor) {
        return valor.equals("skip") || valor.equals("reverse") || valor.equals("+2") || 
               valor.equals("wild") || valor.equals("+4");
    }
}
