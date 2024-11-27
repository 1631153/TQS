package main.launcher;

import main.Controller.*;
import main.View.*;

public class Main {
    public static void main(String[] args) {
        ConsolaView consola = new ConsolaView();
        JuegoController controlador = new JuegoController();
        controlador.setInterfaz(consola);
        controlador.mostrarMenu();
    }
}