TFG VIDEOJUEGO RESTAURANTE \- Olga Marco, Jaime Casanova

**ANTEPROYECTO**

**Idea general**: Un juego diseñado en javaFX que guarde el proyecto en una base de datos. La idea del juego es que sea una mezcla entre un juego de cocina y uno de dibujo, donde se elegirá entre una lista de ingredientes predeterminada según el dibujo que haga el compañero, ya que pretendemos que sea un multijugador en local.  

**Módulos relacionados**: En este proyecto los dos módulos más implicados serían desarrollo de interfaces, ya que el juego va a tener una interfaz gráfica y el otro módulo que más tocariamos sería acceso a datos teniendo en cuenta que para cargar las imágenes del juego y guardar los datos de la partida se guardará esos datos en una estructura de almacenamiento, todavía no sabemos si en una base de datos en ficheros que se recargará más tarde.

Pensamos que Bruno Alejos podría ser una buena opción de tutor, ya que al ser un videojuego y un ambiente más actual puede ser el que más nos aporte, a ser nuestro tutor tenemos más acceso a el y al ser nuestro profesor de acceso a datos, nos puede ayudar con ese tema, que tal vez es el que más nos puede costar.

**IDEAS POST ANTEPROYECTO**

Estética:

- [ ] Restaurante 🍖  
- [ ] Espacio 🚀  
- [ ] Aliens👽  
- [ ] Monstruos 🧌  
      

Funcionalidad

- [ ] Clientes  
- [ ] Pedido (con paint)  
- [ ] Multijugador (Parece difícil, si lo adaptamos a local sería mil veces mejor)  
- [ ] Version de movil / PC  
- [ ] Cambio de escenario si el restaurante “funciona bien”

IDEAS LOCAS MUY GENERALES

Se me ocurre que los platos sean muy raros como “una pizza de lágrimas de unicornio” o “un sándwich de nube y dragón”

 la lechuga empieza a moverse sola, el pan se quema si lo miras mucho, el queso canta…

A veces, un cliente es “troll” y pide mal a propósito.

A mitad de la partida, los jugadores cambian de rol: quien dibujaba ahora cocina y viceversa.(yo diria que o lo elijan ellos o cambien por rondas)

El dibujante podría exagerar el pedido para despistar, o el cocinero puede malinterpretar adrede y aún así recibir puntos por la creatividad.  
	  
Más modos de juego como 1vs1 en vez de cooperativo

Cliente que solo habla en emojis → el cocinero recibe pistas confusas.

Los clientes son disléxicos por lo que si le pones lechuga morada no se da cuenta, y hay que gastar la lechuga morada.

NECESIDADES

- [ ] Decidir lenguaje de programación primario (Java/kotlin)(javaFX/swing)  
- [ ] Encontrar o crear una batería de npc

**1\. Ideas generales**  
**Descripción:** Un restaurante donde clientes alienígenas piden platos extraños como "sirope de unicornio" o "estofado de alien". Estilo visual pixel art.

**JavaFX**: Creación de la interfaz gráfica de usuario (GUI), manejo de eventos (clics de botones, interacciones con objetos).

Cosas que nos pueden ayudar:  
Carga y **renderizado de sprites.**

Estructuras de Datos: Clases para clientes, pedidos, ingredientes, platos. (Esto podemos hacerlo ya con un diseño de interfaz completamente básico, como un punto)

**Lógica del Juego:** Gestión del tiempo, puntuación, progresión de niveles,

**2\. Mecánica “Gartic Phone” para la Comida (Jugador local 2\)**  
**Descripción:** Un jugador dibuja el pedido del cliente y el otro lo interpreta para preparar y entregar.  
Cosas que nos pueden ayudar:  
**JavaFX Canvas:** Utilizar el Canvas de JavaFX para permitir dibujar. Manejo de eventos de ratón (presionar, arrastrar, soltar) para dibujar líneas y formas.  
**Serialización/Red** (si es multijugador): Enviar los datos del dibujo (coordenadas, colores, grosor de línea) de un cliente a otro.

Lógica del Juego que se me ha ocurrido: Temporizadores para dibujar, sistema para que el jugador "acepte" el dibujo o lo descarte.

**3**.**Multijugador Local**   
   
Yo usaría esta opción si o si, creo que si uno juega con teclado y otro con mando puede ser divertido, problema, no se si es difícil conectar mando, yo imagino que no.  
Dividir la pantalla en dos vistas (SplitPane o GridPane), manejo de entrada separada (un jugador con teclado/ratón principal, otro con otro ratón/teclado o incluso controles personalizados) Pasando referencias entre los objetos de los dos jugadores.

**4\. Clientes con Requisitos Especiales/Mutaciones**

Eventos: Manejar eventos que cambien las condiciones del juego (ej. oscurecer la pantalla temporalmente).

Los ingredientes no son estáticos. Algunos podrían cambiar de forma o color si no se usan rápidamente ("sirope de unicornio" que se convierte en "gelatina de gusano"), otros podrían "caducar" si el jugador los tiene demasiado tiempo, o incluso reaccionar de forma inesperada al combinarse.  
Temporizadores asociados a ingredientes, estados de ingredientes (fresco, mutando, caducado).  
Animaciones/Efectos Visuales: Pequeñas animaciones para mostrar que un ingrediente está cambiando.

**5\. Minijuegos de Preparación Avanzados (Jugador 1\)**  
   
Ya que sentía muy plano al jugador 1 he intentado profundizar más.

En lugar de solo arrastrar y soltar ingredientes, algunos pasos de preparación podrían ser minijuegos. Por ejemplo, "cortar el estofado de alien" podría ser un minijuego rítmico o de precisión con el ratón. "Mezclar el sirope de unicornio" podría ser un minijuego de agitar el ratón. (Podría ser como un piano tiles esto)

Integración de los resultados del minijuego en el plato final.ç

Esto parece coñazo porque es mejor muchos minijuegos, aun asi, no lo veo tan dificil, y creo que suma mucho a la jugabilidad, pero ya me darás tu opinión.

**6\. Sistema de Reputación Galáctica y Decoración del Restaurante**

A medida que sirves bien a los clientes, ganas reputación. Con el dinero o puntos de reputación, puedes comprar decoraciones para tu restaurante (más mesas, luces de neón alienígenas, etc.) que a su vez atraen a más clientes o clientes más exigentes/generosos. Podría haber un sistema de "licencias" para servir ciertos tipos de comida. El sistema de licencias sería la sustitución del sistema de niveles, pienso que se adapta más al estilo del juego, pero aún no descarto lo de los niveles.

Y gracias a este punto podemos entrar en las bases de datos. Guardar y cargar el estado del restaurante (decoraciones, dinero, reputación, licencias y si eso, nivel más alto alcanzado, etc) 

