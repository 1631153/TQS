package main.Model;

public class Carta {
    private String color;  // "r" = rojo, "b" = azul, "g" = verde, "y" = amarillo, null = comodín
    private String valor;  // "0" a "9" para números, "skip", "reverse", "+2", "wild", "+4" para especiales

    /*
     * Cartas Numéricas: 
     *      Las cartas que tienen un valor numérico (del "0" al "9") deben tener un color válido (rojo, azul, verde o amarillo). 
     *      No pueden ser comodines.
     * Cartas Especiales:
     *      Las cartas de acción especiales como "skip", "reverse" y "+2" pueden tener cualquier color válido o no tener un color asociado. 
     * Comodines: 
     *      Los comodines (valores "wild" y "+4") no pueden tener un color asociado (es decir, el atributo color debe ser null).
     */

    // Constructor
    public Carta(String color, String valor) {
        // Inicialización (Por desenvolupar)
    }

    // Getters
    public String getColor() {
        return color;
    }

    public String getValor() {
        return valor;
    }

    // Método para verificar si la carta es compatible con otra
    public boolean esCompatible(Carta otraCarta) {
        // Lógica de compatibilidad (Por desenvolupar)
        return false;  // Placeholder
    }
}
