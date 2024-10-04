*(provisional)*
# Arquitectura del Sistema: UNO Digital

UNO Digital sigue una arquitectura basada en el patrón **Modelo-Vista-Controlador**:
-	Modelo: Es responsable de toda la lógica del juego y los datos. Incluye las clases que gestionan el mazo, las cartas, los jugadores y las reglas del juego.
    
Clases principales:
    
    Carta: Representa una carta individual.
    Mazo: Gestiona las cartas disponibles y la pila de descarte.
    Jugador: Representa a cada jugador, con su mano y estado.
    Partida: Controla la lógica de los turnos, las reglas y el estado del juego.

                          (añadir o cambiar si es necesario)

-	Vista: Se encarga de la interfaz de usuario

                          (PRIORIDAD: ConsolaView)
                          (SI ES POSIBLE una versión grafica)

-	Controlador: Actúa como intermediario entre el modelo y la vista. Controla el flujo de la partida y asegura que las acciones del jugador se reflejen correctamente en el modelo y en la vista.


Gestión de Estado:
La clase Partida contiene la lógica de gestión del estado del juego, como el jugador actual, el sentido del juego, y las reglas de las cartas especiales.
Cada instancia de Partida controla las interacciones entre Jugador y Mazo.

                          (AÑADIDOS: MULTIJUGADOR)
                        (Modificar si se logra hacer)