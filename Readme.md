# ![UNO](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9geNsjpS8cq_mCVGe92dxI1sbkcpNtEgPSA&s)
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

# Arquitectura del Sistema: UNO Digital

UNO Digital sigue una arquitectura basada en el patrón **Modelo-Vista-Controlador (MVC)**:

- **Modelo**: Gestiona la lógica del juego y los datos, incluyendo las clases principales:
  - `Carta`: Representa una carta individual.
  - `Mazo`: Gestiona las cartas disponibles y la pila de descarte.
  - `Jugador`: Representa a cada jugador, con su mano y estado.
  - `Partida`: Controla la lógica de los turnos, reglas y estado del juego.

- **Vista**: Se encarga de la interfaz de usuario:
  - `InterfazJuego`: Clase base para las diferentes vistas (Consola o GUI).
  - `ConsolaView` y `GUIView`: Implementaciones específicas de la interfaz.

- **Controlador**: Actúa como intermediario entre el modelo y la vista, controlando el flujo de la partida.

## Estructura del Proyecto

El proyecto está organizado en una estructura estándar para aplicaciones Java:

```
/src
  ├── /main
  │      └── /java
  │            └── /main
  │                  └── /Model
  │                        └── Carta.java
  │                        └── Mazo.java
  │                        └── Jugador.java
  │                        └── Partida.java
  │                  └── /Controller
  │                        └── JuegoController.java
  │                  └── /View
  │                        └── InterfazJuego.java
  │                        └── ConsolaView.java
  │                        └── GUIView.java
  └── /test
         └── /java
               └── /test
                     └── /ModelTest
                           └── CartaTest.java
                           └── MazoTest.java
                           └── JugadorTest.java
                           └── PartidaTest.java
                     └── /ControllerTest
                           └── JuegoControllerTest.java
                     └── /ViewTest
                           └── ConsolaViewTest.java
                           └── GUIViewTest.java
```

## Herramientas de Desarrollo

### Usando Maven

El proyecto utiliza **Maven** para la gestión de dependencias y la ejecución de pruebas. Un ejemplo de configuración en `pom.xml`:

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.8.2</version>
        <scope>test</scope>
    </dependency>

    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>4.0.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Diagrama UML

A continuación, se presenta un diagrama simplificado de las clases y sus relaciones:

```plaintext
+------------------+          +---------------------------------+            +----------------------+
|     Carta        | <------> |       Mazo                      | <--------> |      Partida         |
+------------------+          +---------------------------------+            +----------------------+
| - color          |          | - cartas: List                  |            | - jugadores: List    |
| - valor          |          | - ultimaCartaJugada             |            | - jugadorActual: int |
|------------------+          | - comodinColor                  |            | - sentidoHorario     |
| + getColor()     |          +---------------------------------+            | + jugarCarta()       |
| + getValor()     |          | + robarCarta()                  |            | + aplicarCarta()     |
| + esCompatible() |          | + actualizarUltimaCartaJugada() |            | + cambiarTurno()     |
+------------------+          | + establecerComodinColor()      |            | + esFinPartida()     |
                              +---------------------------------+            | + getJugadorActual   |
                                                                             +----------------------+
                                                                                        ^
                                                                                        |
                                                                            +-----------------------+
                                                                            |       Jugador         |
                                                                            +-----------------------+
                                                                            | - nombre              |
                                                                            | - mano: List<Carta>   |
                                                                            +-----------------------+
                                                                            | + robarCarta()        |
                                                                            | + jugarCarta()        |
                                                                            +-----------------------+

+-----------------------+            +---------------------------+          +-----------------------+
|     InterfazJuego     | <--------- |     JuegoController       | -------> |       Partida         |
+-----------------------+            +---------------------------+          +-----------------------+
            |                        | - partida: Partida        |
            |                        | - interfaz: InterfazJuego |
            |                        +---------------------------+
            |                                  
            +------------------+                                  
                               |                                  
                               |                                  
+-----------------------+      |                                  
|      ConsolaView      |      |                                  
+-----------------------+      |
| + implementar métodos | <----+
|   de InterfazJuego    |      |
+-----------------------+      |
                               |
+-----------------------+      |
|        GUIView        |      |
+-----------------------+      |
| + implementar métodos | <----+
|   de InterfazJuego    |
+-----------------------+
```

## Aporte

¡Cualquier contribución es bienvenida! Puedes enviar tus ideas o realizar un *pull request*.

## Autores

- Javier Comes
- David Bonilla
