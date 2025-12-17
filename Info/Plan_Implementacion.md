# 🎮 Plan de Implementación - TFG Restaurante Alienígena

## Estado Actual
El proyecto tiene la estructura base de LibGDX pero sin funcionalidad implementada.

---

## 📋 División de Tareas

### **JAIME - Desarrollo Core del Juego**

#### Semana 1-2: Arquitectura Base
- [ ] Crear clase `GameScreen` extendiendo `Screen`
- [ ] Crear clase `MenuScreen` para menú principal
- [ ] Modificar `Main.java` para usar `Game` en lugar de `ApplicationAdapter`
- [ ] Configurar sistema de cambio entre pantallas

#### Semana 3-4: Entidades del Juego
- [ ] Clase `Cliente`
  - Sprite del alienígena
  - Pedido (referencia al dibujo/plato esperado)
  - Temporizador de paciencia
  - Estados: esperando, satisfecho, enfadado
- [ ] Clase `Ingrediente`
  - Tipo (sirope unicornio, estofado alien, etc.)
  - Estados: fresco, mutando, caducado
  - Sprite y animaciones de transformación
- [ ] Clase `Plato`
  - Lista de ingredientes
  - Método para calcular similitud con pedido

#### Semana 5-6: Mecánicas Jugador 1 (Cocinero)
- [ ] Sistema drag & drop para ingredientes
- [ ] Zona de preparación del plato
- [ ] Botón de entrega al cliente
- [ ] Lógica de puntuación

#### Semana 7-8: Sistema de Juego
- [ ] Spawn de clientes con dificultad progresiva
- [ ] Sistema de reputación
- [ ] Gestión de dinero/puntos
- [ ] Condición de fin de partida

---

### **OLGA - Sistema de Dibujo + UI + Arte**

#### Semana 1-2: Investigación y Diseño
- [ ] Investigar `ShapeRenderer` para dibujo
- [ ] Crear mockups de todas las pantallas
- [ ] Definir paleta de colores (estética espacial/alien)
- [ ] Decidir estilo visual (pixel art recomendado)

#### Semana 3-4: Sistema de Dibujo (Canvas)
- [ ] Implementar clase `DrawingCanvas`
  - Captura de eventos de ratón
  - Dibujado de líneas con `ShapeRenderer`
  - Selección de colores (4-6 colores básicos)
  - Selección de grosor de línea
  - Botón borrar/limpiar
- [ ] Temporizador para tiempo de dibujo
- [ ] Botón "Enviar dibujo"

#### Semana 5-6: Interfaz de Usuario
- [ ] Menú principal
  - Botón Jugar
  - Botón Opciones
  - Botón Salir
- [ ] HUD durante partida
  - Puntuación
  - Tiempo restante
  - Reputación del restaurante
- [ ] Pantalla dividida (SplitScreen) para 2 jugadores

#### Semana 7-8: Assets Gráficos
- [ ] Sprites de clientes alienígenas (mínimo 5 tipos)
- [ ] Sprites de ingredientes (mínimo 10 tipos)
- [ ] Fondo del restaurante
- [ ] Iconos de UI

---

### **TAREAS CONJUNTAS**

#### Configuración Inicial
- [ ] Decidir resolución del juego (recomendado: 1280x720)
- [ ] Configurar `Viewport` para escalado
- [ ] Crear estructura de carpetas para assets

#### Integración
- [ ] Conectar canvas de dibujo con sistema de pedidos
- [ ] Sistema de comunicación entre los dos jugadores
- [ ] Testing de partida completa

#### Base de Datos (Fase Final)
- [ ] Diseñar esquema de datos (JSON o SQLite)
- [ ] Guardar estado del restaurante
- [ ] Cargar partida guardada

---

## 🎯 Primeros Pasos (Esta Semana)

| Persona | Tarea Inmediata |
|---------|-----------------|
| **Jaime** | Crear `GameScreen` y `MenuScreen` básicos |
| **Olga** | Probar `ShapeRenderer` con líneas simples |
| **Ambos** | Decidir resolución y crear carpetas de assets |

---

## 📁 Estructura de Carpetas Propuesta

```
core/src/main/java/TFG/JaimeOlga/
├── Main.java
├── screens/
│   ├── MenuScreen.java
│   ├── GameScreen.java
│   └── PauseScreen.java
├── entities/
│   ├── Cliente.java
│   ├── Ingrediente.java
│   └── Plato.java
├── ui/
│   ├── DrawingCanvas.java
│   └── HUD.java
└── utils/
    └── GameConfig.java

assets/
├── sprites/
│   ├── clientes/
│   └── ingredientes/
├── ui/
└── audio/
```

---

## 📝 Notas Técnicas

- **LibGDX Version**: Verificar versión en `build.gradle`
- **Java Version**: Configurado para Java 25
- **Multijugador Local**: Un jugador usa teclado/ratón, otro puede usar segundo ratón o mando
- **Renderizado**: Usar `SpriteBatch` para sprites, `ShapeRenderer` para el canvas de dibujo
