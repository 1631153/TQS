package main.Model;

import java.util.Objects;

import java.io.Serializable;

public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private String color;  // "r" = rojo, "b" = azul, "g" = verde, "y" = amarillo, null = comodín
    private String valor;  // "0" a "9" para números, "skip", "reverse", "+2", "wild", "+4" para especiales

    /*
     * Cartas Numéricas: 
     *      Las cartas que tienen un valor numérico (del "0" al "9") deben tener un color válido (rojo, azul, verde o amarillo) x2.
     * Cartas Especiales:
     *      Las cartas de acción especiales como "skip", "reverse" y "+2" deben tener un color válido (rojo, azul, verde o amarillo) x2. 
     * Comodines: 
     *      Los comodines (valores "wild" x4 y "+4" x4) no pueden tener un color asociado (es decir, el atributo color debe ser null).
     */

     public Carta(String color, String valor) {
        //Precondiciones
        assert (valor != null && !valor.isEmpty()) : "El valor no puede ser nulo o vacío";
        assert (color == null && (valor.equals("wild") || valor.equals("+4"))) || (color != null && isColorValido(color)) : "El color debe ser válido o null para comodines";

        // Verificamos si el valor de la carta es uno de los comodines ("wild" o "+4")
        if (valor.equals("wild") || valor.equals("+4")) {
            // Un comodín no debe tener un color asociado
            if (color != null) {
                throw new IllegalArgumentException("Un comodín no debe tener color");
            }
        } 
        // Si el color es null, verificamos que el valor no sea numérico
        else if (color == null) {
            throw new IllegalArgumentException("Las cartas numéricas no pueden ser comodines (color debe ser no nulo)");
        } 
        // Verificamos que el color proporcionado sea válido
        else if (!isColorValido(color)) {
            throw new IllegalArgumentException("Color no válido: " + color);
        }
    
        // Validamos que el valor de la carta sea un valor permitido
        if (!isValorValido(valor)) {
            throw new IllegalArgumentException("Valor no válido: " + valor);
        }
        
        // Asignamos el color y el valor a la carta si todas las validaciones han pasado
        this.color = color;
        this.valor = valor;

        //Postcondicion
        assert (this.valor != null && !this.valor.isEmpty()) : "El valor de la carta debe ser válido y no vacío.";
        assert (this.color == null && (this.valor.equals("wild") || this.valor.equals("+4"))) || isColorValido(this.color) : "El estado de la carta es inconsistente"; 
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
        // Precondición
        assert (otraCarta != null) : "La carta comparada no puede ser null";
        assert (otraCarta.getColor() == null || isColorValido(otraCarta.getColor())) : "El color de la carta comparada debe ser válido o null para comodines";
        assert (otraCarta.getValor() != null && !otraCarta.getValor().isEmpty()) : "El valor de la carta comparada debe ser válido y no vacío";
    
        //Esto es para casos comodin
        if (this.color == null || otraCarta.color == null) {
            return true;
        }

        // Dos cartas son compatibles si tienen el mismo color o el mismo valor
        boolean res = Objects.equals(this.color, otraCarta.color) || Objects.equals(this.valor, otraCarta.valor);
    
        // Postcondición
        assert res == (Objects.equals(this.color, otraCarta.color) || Objects.equals(this.valor, otraCarta.valor)) : "Compatibilidad inconsistente entre cartas";
    
        return res;
    }
    

    // Método para verificar si el color es válido
    private boolean isColorValido(String color) {
        return color.equals("r") || color.equals("b") || color.equals("g") || color.equals("y");
    }

    // Método para verificar si el valor es válido
    private boolean isValorValido(String valor) {
        return valor.matches("[0-9]") || isValorEspecial(valor);
    }

    // Método para verificar si el valor es una acción especial
    public boolean isValorEspecial(String valor) {
        return valor.equals("skip") || valor.equals("reverse") || valor.equals("+2") || 
               valor.equals("wild") || valor.equals("+4");
    }
}
