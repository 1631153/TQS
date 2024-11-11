### **Ficha de Requerimientos Funcionales y Técnicos**

---

#### **Nombre del Proyecto**:  
**UNO Digital**

#### **Propósito**:  
Desarrollar una aplicación digital del juego de cartas **UNO**, con soporte para juego individual contra la IA, multijugador local, y multijugador en línea. La aplicación debe replicar fielmente las reglas del juego físico, asegurando una experiencia intuitiva y entretenida para los usuarios.

---

### **1. Requerimientos Funcionales**

#### **1.1 Funcionalidades del Juego**
1. **Gestión de Jugadores**:
   - El juego permitirá de **2 a 4 jugadores**.
   - Los jugadores podrán ser controlados por la **IA** o por usuarios humanos (local o en línea).
   - Cada jugador tendrá una mano de **7 cartas** al inicio de la partida.
   - Se requiere una opción de **multijugador local** y **multijugador en línea**.

2. **Mecánica del Juego**:
   - Los jugadores deberán **jugar una carta** que coincida en **color** o **número** con la carta en la pila de descarte.
   - Si no pueden jugar una carta, deberán **robar una carta del mazo**.
   - Los jugadores podrán **decir "UNO"** cuando les quede una sola carta. Si no lo hacen y otro jugador los detecta, se les penalizará con la adición de **2 cartas**.
   
3. **Cartas Especiales**:
   - Implementar las cartas especiales:
     - **Cambio de sentido** (Reverse): Cambia la dirección del juego.
     - **Saltar turno** (Skip): El siguiente jugador pierde su turno.
     - **+2**: El siguiente jugador debe robar dos cartas.
     - **Comodín de color**: Permite al jugador cambiar el color de juego.
     - **Comodín +4**: Permite cambiar el color y el siguiente jugador debe robar cuatro cartas.

4. **Reglas del Juego**:
   - Las reglas deben seguir las oficiales de **UNO**, incluyendo:
     - **Cambio de sentido** y **salto de turno**.
     - Aplicación de penalizaciones por no decir "UNO".
     - Fin del juego cuando un jugador se quede sin cartas.
   - Soporte para **continuación de partida** en caso de desconexiones.

#### **1.2 Interfaz de Usuario**
1. **Pantalla Principal**:
   - Menú de inicio con opciones para **Juego Nuevo**, **Configuración**, **Instrucciones**, y **Salir**.
   - Opciones para seleccionar entre **partida individual**, **multijugador local** o **multijugador en línea**.

2. **Interfaz de Juego**:
   - Visualización clara del **mazo** y la **pila de descarte**.
   - Mostrar la **mano de cartas** de cada jugador.
   - Indicador del **turno actual** y visualización de las cartas jugadas.
   - Botón para **robar cartas** y **decir "UNO"** cuando sea el momento.

3. **Notificaciones y Alertas**:
   - Notificación de **acción de cartas especiales** (salto de turno, +2, +4).
   - Alerta cuando un jugador no diga “UNO” y debe ser penalizado.

4. **Modalidad Multijugador**:
   - Implementación de un **chat en tiempo real** (para el multijugador en línea).
   - **Notificaciones en tiempo real** de cambios de estado (turno, cartas jugadas, etc.).

#### **1.3 Modo de Juego**
1. **Individual contra IA**:
   - Diferentes niveles de dificultad para la **IA** (fácil, medio, difícil).
   
2. **Multijugador Local**:
   - Soporte para **multijugador local** en el mismo dispositivo o en red local.

3. **Multijugador en Línea**:
   - Conexión a servidores para soportar **partidas en línea** con amigos o jugadores aleatorios.
   - Gestión de **salas de espera** para iniciar partidas multijugador.

---

### **2. Requerimientos Técnicos**

#### **2.1 Arquitectura del Software**
1. **Modelo-Vista-Controlador (MVC)**:
   - El software debe estar estructurado en tres capas principales:
     - **Modelo**: Contiene la lógica del juego, reglas y manejo de datos (jugadores, cartas, turnos, etc.).
     - **Vista**: La interfaz gráfica o consola que interactúa con el usuario.
     - **Controlador**: Gestiona la comunicación entre el modelo y la vista, asegurando que las reglas del juego se respeten.

2. **Lenguaje de Programación**:  
   - El desarrollo se realizará en **Java**. Se podrán utilizar librerías adicionales para soporte gráfico (JavaFX, Swing) y para la implementación del multijugador en línea (Sockets, WebSockets).

3. **Persistencia de Datos**:
   - Posibilidad de **guardar** y **cargar partidas**.
   - Registro de las estadísticas de los jugadores, como número de partidas jugadas y ganadas.

4. **Multijugador en Línea**:
   - Soporte para comunicación cliente-servidor mediante **Sockets** o **WebSockets**.
   - Sincronización en tiempo real de las acciones de los jugadores.
   - Seguridad en las conexiones con cifrado para proteger los datos transmitidos.

#### **2.2 Requerimientos de Rendimiento**
1. **Tiempo de Respuesta**:
   - Las acciones del jugador deben reflejarse en la interfaz en **menos de 100ms**.
   - En el multijugador en línea, el tiempo de latencia debe ser **mínimo** para garantizar una experiencia fluida (idealmente por debajo de **200ms**).

2. **Escalabilidad**:
   - El sistema multijugador debe poder soportar un número elevado de usuarios simultáneamente sin afectar el rendimiento.

3. **Optimización de Recursos**:
   - La aplicación debe tener un **uso óptimo de memoria y CPU**, garantizando una experiencia fluida en dispositivos con recursos limitados.

#### **2.3 Requerimientos de Calidad**
1. **Pruebas Unitarias**:
   - Se deben realizar pruebas unitarias para validar la funcionalidad del modelo (gestión de cartas, reglas del juego, etc.).
   
2. **Pruebas de Integración**:
   - Asegurar que el modelo, vista y controlador interactúan correctamente.
   
3. **Pruebas de Multijugador**:
   - Validar la correcta sincronización entre los jugadores y el servidor.
   - Pruebas de resistencia para asegurar que el sistema multijugador pueda soportar una carga alta.

4. **Cobertura del Código**:
   - Se requiere una cobertura mínima del **85% del código** con pruebas.

#### **2.4 Documentación y Mantenimiento**
1. **Documentación del Código**:
   - Todo el código debe estar bien documentado con comentarios claros que describan su funcionalidad.
   - Se debe generar documentación técnica sobre la estructura del código y la arquitectura general del sistema.

2. **Soporte y Actualizaciones**:
   - Incluir un sistema de **actualizaciones automáticas** para futuras versiones del software.
   - Planificar soporte técnico para resolver errores y agregar nuevas funcionalidades basadas en el feedback de los usuarios.

---

### **3. Requerimientos de Seguridad**

1. **Privacidad de Datos**:
   - En el multijugador en línea, se debe garantizar la **privacidad de los datos de los jugadores**, incluyendo el uso de **cifrado en las comunicaciones**.
   
2. **Protección contra Trampas**:
   - Implementar medidas para evitar **trampas** o **modificación del juego** en partidas multijugador en línea (validación de acciones por el servidor).

---

### **4. Requerimientos de Entrega**

#### **4.1 Entregables**
1. **Código Fuente**: Repositorio en GitHub (privado) que contenga todo el código del proyecto, gestionado bajo control de versiones.
2. **Documentación Técnica**: Guía de instalación, manual de usuario y documentación sobre la arquitectura.
3. **Informe de Pruebas**: Reporte que incluya las pruebas realizadas y los resultados obtenidos.
4. **Versión Ejecutable**: Archivo ejecutable (.jar o .exe) para distribuir el juego a usuarios finales.

#### **4.2 Plazos**
- **Fecha de entrega final**: 2 de diciembre de 2024, a las 10:30 AM. No se permitirán extensiones del plazo.

---

### **5. Restricciones**

1. **Uso de Herramientas**:
   - Se debe utilizar un repositorio privado de **GitHub** para la gestión del código.
   - El sistema de pruebas automáticas debe estar implementado mediante **GitHub Actions** para verificar las versiones del código.

2. **Compatibilidad**:
   - El juego debe ser compatible con **Windows**, **macOS**, y **Linux**.

---

Este documento de requerimientos ofrece una guía clara y detallada para el desarrollo del juego UNO Digital, asegurando que todos los aspectos funcionales, técnicos y de calidad estén claramente definidos desde el inicio del proyecto.