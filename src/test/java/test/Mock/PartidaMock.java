package test.Mock;

import main.Model.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaMock extends Partida {
    private List<Jugador> jugadoresMock;
    private int jugadorActualMock;
    private boolean sentidoHorarioMock;
    private boolean finPartidaMock;
    private MazoMock mazoMock;

    public PartidaMock(List<Jugador> jugadores, MazoMock mazo) {
        super(); // Llamada al constructor de la clase base
        this.jugadoresMock = new ArrayList<>(jugadores);
        this.jugadorActualMock = 0; // Inicia con el primer jugador
        this.sentidoHorarioMock = true;
        this.finPartidaMock = false;
        this.mazoMock = mazo;
        setMazoMock(mazo); // Configura el mazo mock en la superclase
    }

    @Override
    public Jugador getJugadorActual() {
        return jugadoresMock.get(jugadorActualMock);
    }

    @Override
    public boolean esFinPartida() {
        return finPartidaMock;
    }

    public void setFinPartida(boolean finPartida) {
        this.finPartidaMock = finPartida;
    }

    @Override
    public List<Jugador> getJugadores() {
        return jugadoresMock;
    }

    @Override
    public boolean getSentidoHorario() {
        return sentidoHorarioMock;
    }

    public void setSentidoHorario(boolean sentido) {
        this.sentidoHorarioMock = sentido;
    }
}