
public class Carta {

    private Color color;
    private String valor;

    public Carta();

    public Color getColor();
    public String getValor();    
    public boolean ValidarCarta(Carta cartaMazo); //comprovar que se puede validar por numero y color (Carta cartaActual)

    public boolean cartaEspecial(); //Reverse, Saltar, +2, +4

    
}