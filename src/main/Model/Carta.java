
public class Carta {
    private String color;  // "r" = rojo, "b" = azul, "g" = verde, "y" = amarillo, null = comodín
    private String valor;  // "0" a "9" para números, "skip", "reverse", "+2", "wild", "+4" para especiales

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
