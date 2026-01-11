# 🎮 Guía de Implementación para Jaime - Bases del Juego

## 📌 Tu Rol
Eres el encargado del **Desarrollo Core del Juego**: arquitectura base, entidades, mecánicas del cocinero y sistema de juego.

---

## 🚀 FASE 1: Arquitectura Base (Semanas 1-2)

### Paso 1.1: Modificar `Main.java` para usar `Game`

**¿Por qué?** `Game` permite manejar múltiples pantallas (menú, juego, pausa).

**Archivo:** `core/src/main/java/TFG/JaimeOlga/Main.java`

```java
package TFG.JaimeOlga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        // Iniciamos en el menú principal
        setScreen(new screens.MenuScreen(this));
    }
    
    @Override
    public void dispose() {
        batch.dispose();
    }
}
```

---

### Paso 1.2: Crear `MenuScreen.java`

**Archivo:** `core/src/main/java/TFG/JaimeOlga/screens/MenuScreen.java`

```java
package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import TFG.JaimeOlga.Main;

public class MenuScreen implements Screen {
    private Main game;
    private BitmapFont font;
    
    public MenuScreen(Main game) {
        this.game = game;
        font = new BitmapFont(); // Fuente por defecto
    }
    
    @Override
    public void show() {
        // Se llama cuando la pantalla se muestra
    }
    
    @Override
    public void render(float delta) {
        // Limpiar pantalla con color oscuro
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        font.draw(game.batch, "RESTAURANTE ALIENIGENA", 100, 400);
        font.draw(game.batch, "Pulsa ENTER para jugar", 100, 300);
        game.batch.end();
        
        // Detectar input para cambiar de pantalla
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
    
    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        font.dispose();
    }
}
```

---

### Paso 1.3: Crear `GameScreen.java`

**Archivo:** `core/src/main/java/TFG/JaimeOlga/screens/GameScreen.java`

```java
package TFG.JaimeOlga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import TFG.JaimeOlga.Main;

public class GameScreen implements Screen {
    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;
    
    // Resolución del juego (acordar con Olga)
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    
    public GameScreen(Main game) {
        this.game = game;
        
        // Configurar cámara y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.position.set(GAME_WIDTH / 2f, GAME_HEIGHT / 2f, 0);
    }
    
    @Override
    public void show() {
        // Inicializar elementos del juego aquí
    }
    
    @Override
    public void render(float delta) {
        // 1. Actualizar lógica
        update(delta);
        
        // 2. Limpiar pantalla
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // 3. Dibujar
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        // Aquí irán los sprites de clientes, ingredientes, etc.
        game.batch.end();
    }
    
    private void update(float delta) {
        // Actualizar clientes, ingredientes, puntuación...
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {}
}
```

---

### Paso 1.4: Crear clase de configuración `GameConfig.java`

**Archivo:** `core/src/main/java/TFG/JaimeOlga/utils/GameConfig.java`

```java
package TFG.JaimeOlga.utils;

public class GameConfig {
    // Resolución
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    
    // Tiempos (en segundos)
    public static final float TIEMPO_PACIENCIA_CLIENTE = 30f;
    public static final float TIEMPO_MUTACION_INGREDIENTE = 10f;
    
    // Puntuación
    public static final int PUNTOS_PLATO_PERFECTO = 100;
    public static final int PUNTOS_PLATO_BUENO = 50;
    public static final int PENALIZACION_CLIENTE_ENFADADO = -25;
    
    // Dificultad
    public static final float SPAWN_INICIAL_CLIENTES = 8f; // segundos entre clientes
}
```

---

## 🚀 FASE 2: Entidades del Juego (Semanas 3-4)

### Paso 2.1: Crear carpeta `entities`

Crea la carpeta: `core/src/main/java/TFG/JaimeOlga/entities/`

---

### Paso 2.2: Crear `Cliente.java`

```java
package TFG.JaimeOlga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import TFG.JaimeOlga.utils.GameConfig;

public class Cliente {
    // Estados posibles del cliente
    public enum Estado {
        ESPERANDO,
        SATISFECHO,
        ENFADADO
    }
    
    private Texture sprite;
    private float x, y;
    private Estado estado;
    private float tiempoPaciencia;
    private String pedido; // Descripción del plato que quiere
    
    public Cliente(float x, float y, String pedido) {
        this.x = x;
        this.y = y;
        this.pedido = pedido;
        this.estado = Estado.ESPERANDO;
        this.tiempoPaciencia = GameConfig.TIEMPO_PACIENCIA_CLIENTE;
        
        // TODO: Cargar sprite según tipo de alienígena
        // this.sprite = new Texture("sprites/clientes/alien1.png");
    }
    
    public void update(float delta) {
        if (estado == Estado.ESPERANDO) {
            tiempoPaciencia -= delta;
            
            if (tiempoPaciencia <= 0) {
                estado = Estado.ENFADADO;
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        if (sprite != null) {
            batch.draw(sprite, x, y);
        }
    }
    
    // Getters y Setters
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public String getPedido() { return pedido; }
    public float getTiempoPaciencia() { return tiempoPaciencia; }
    
    public void dispose() {
        if (sprite != null) sprite.dispose();
    }
}
```

---

### Paso 2.3: Crear `Ingrediente.java`

```java
package TFG.JaimeOlga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import TFG.JaimeOlga.utils.GameConfig;

public class Ingrediente {
    public enum Tipo {
        SIROPE_UNICORNIO,
        ESTOFADO_ALIEN,
        CRISTAL_LUNAR,
        PLASMA_VERDE,
        TENTACULO_FRITO,
        NEBULOSA_DULCE
        // Añadir más según necesites
    }
    
    public enum Estado {
        FRESCO,
        MUTANDO,
        CADUCADO
    }
    
    private Tipo tipo;
    private Estado estado;
    private Texture sprite;
    private float x, y;
    private float tiempoVida;
    private boolean seleccionado;
    
    public Ingrediente(Tipo tipo, float x, float y) {
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.estado = Estado.FRESCO;
        this.tiempoVida = GameConfig.TIEMPO_MUTACION_INGREDIENTE;
        this.seleccionado = false;
        
        // TODO: Cargar sprite según tipo
        // this.sprite = new Texture("sprites/ingredientes/" + tipo.name().toLowerCase() + ".png");
    }
    
    public void update(float delta) {
        if (estado != Estado.CADUCADO) {
            tiempoVida -= delta;
            
            if (tiempoVida <= 0) {
                estado = Estado.CADUCADO;
            } else if (tiempoVida <= GameConfig.TIEMPO_MUTACION_INGREDIENTE / 2) {
                estado = Estado.MUTANDO;
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        if (sprite != null) {
            batch.draw(sprite, x, y);
        }
    }
    
    // Para drag & drop
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean contains(float touchX, float touchY) {
        // Asumiendo sprites de 64x64
        return touchX >= x && touchX <= x + 64 &&
               touchY >= y && touchY <= y + 64;
    }
    
    // Getters
    public Tipo getTipo() { return tipo; }
    public Estado getEstado() { return estado; }
    public boolean isSeleccionado() { return seleccionado; }
    public void setSeleccionado(boolean seleccionado) { this.seleccionado = seleccionado; }
    
    public void dispose() {
        if (sprite != null) sprite.dispose();
    }
}
```

---

### Paso 2.4: Crear `Plato.java`

```java
package TFG.JaimeOlga.entities;

import java.util.ArrayList;
import java.util.List;

public class Plato {
    private List<Ingrediente> ingredientes;
    private static final int MAX_INGREDIENTES = 5;
    
    public Plato() {
        ingredientes = new ArrayList<>();
    }
    
    public boolean agregarIngrediente(Ingrediente ingrediente) {
        if (ingredientes.size() < MAX_INGREDIENTES && 
            ingrediente.getEstado() != Ingrediente.Estado.CADUCADO) {
            ingredientes.add(ingrediente);
            return true;
        }
        return false;
    }
    
    public void quitarIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
    }
    
    public void limpiar() {
        ingredientes.clear();
    }
    
    /**
     * Calcula qué tan similar es este plato al pedido del cliente.
     * @param pedido Lista de tipos de ingredientes que pide el cliente
     * @return Puntuación de 0.0 a 1.0 (1.0 = perfecto)
     */
    public float calcularSimilitud(List<Ingrediente.Tipo> pedido) {
        if (pedido.isEmpty()) return 0f;
        
        int coincidencias = 0;
        List<Ingrediente.Tipo> pedidoCopia = new ArrayList<>(pedido);
        
        for (Ingrediente ing : ingredientes) {
            if (pedidoCopia.contains(ing.getTipo())) {
                coincidencias++;
                pedidoCopia.remove(ing.getTipo());
            }
        }
        
        // Penalizar ingredientes extra o faltantes
        int totalEsperado = pedido.size();
        int totalPuesto = ingredientes.size();
        int diferencia = Math.abs(totalEsperado - totalPuesto);
        
        float similitud = (float) coincidencias / totalEsperado;
        similitud -= diferencia * 0.1f; // Penalización por cada ingrediente de más/menos
        
        return Math.max(0f, Math.min(1f, similitud));
    }
    
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }
    
    public boolean estaVacio() {
        return ingredientes.isEmpty();
    }
}
```

---

## 🚀 FASE 3: Mecánicas del Cocinero (Semanas 5-6)

### Paso 3.1: Crear `InputHandler.java` para Drag & Drop

**Archivo:** `core/src/main/java/TFG/JaimeOlga/controllers/InputHandler.java`

```java
package TFG.JaimeOlga.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import TFG.JaimeOlga.entities.Ingrediente;
import java.util.List;

public class InputHandler extends InputAdapter {
    private OrthographicCamera camera;
    private List<Ingrediente> ingredientes;
    private Ingrediente ingredienteSeleccionado;
    private Vector3 touchPos;
    
    public InputHandler(OrthographicCamera camera, List<Ingrediente> ingredientes) {
        this.camera = camera;
        this.ingredientes = ingredientes;
        this.touchPos = new Vector3();
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos); // Convertir a coordenadas del juego
        
        // Buscar si tocamos algún ingrediente
        for (Ingrediente ing : ingredientes) {
            if (ing.contains(touchPos.x, touchPos.y)) {
                ingredienteSeleccionado = ing;
                ing.setSeleccionado(true);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (ingredienteSeleccionado != null) {
            touchPos.set(screenX, screenY, 0);
            camera.unproject(touchPos);
            ingredienteSeleccionado.setPosition(touchPos.x - 32, touchPos.y - 32);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (ingredienteSeleccionado != null) {
            ingredienteSeleccionado.setSeleccionado(false);
            // Aquí verificar si lo soltó en la zona del plato
            ingredienteSeleccionado = null;
            return true;
        }
        return false;
    }
}
```

---

## 🚀 FASE 4: Sistema de Juego (Semanas 7-8)

### Paso 4.1: Crear `GameManager.java`

```java
package TFG.JaimeOlga.controllers;

import java.util.ArrayList;
import java.util.List;
import TFG.JaimeOlga.entities.*;
import TFG.JaimeOlga.utils.GameConfig;

public class GameManager {
    private List<Cliente> clientes;
    private List<Ingrediente> ingredientesDisponibles;
    private Plato platoActual;
    
    private int puntuacion;
    private int reputacion; // 0-100
    private float tiempoParaSiguienteCliente;
    private float dificultad;
    
    public GameManager() {
        clientes = new ArrayList<>();
        ingredientesDisponibles = new ArrayList<>();
        platoActual = new Plato();
        
        puntuacion = 0;
        reputacion = 50; // Empezamos en 50%
        dificultad = 1f;
        tiempoParaSiguienteCliente = GameConfig.SPAWN_INICIAL_CLIENTES;
    }
    
    public void update(float delta) {
        // 1. Actualizar clientes
        for (int i = clientes.size() - 1; i >= 0; i--) {
            Cliente cliente = clientes.get(i);
            cliente.update(delta);
            
            if (cliente.getEstado() == Cliente.Estado.ENFADADO) {
                // Cliente se fue enfadado
                reputacion += GameConfig.PENALIZACION_CLIENTE_ENFADADO;
                clientes.remove(i);
            }
        }
        
        // 2. Actualizar ingredientes
        for (Ingrediente ing : ingredientesDisponibles) {
            ing.update(delta);
        }
        
        // 3. Spawn de nuevos clientes
        tiempoParaSiguienteCliente -= delta;
        if (tiempoParaSiguienteCliente <= 0) {
            spawnCliente();
            // Aumentar dificultad (clientes más frecuentes)
            tiempoParaSiguienteCliente = GameConfig.SPAWN_INICIAL_CLIENTES / dificultad;
            dificultad += 0.1f;
        }
        
        // 4. Verificar condición de fin
        if (reputacion <= 0) {
            // GAME OVER
        }
    }
    
    private void spawnCliente() {
        // Generar pedido aleatorio
        String pedido = generarPedidoAleatorio();
        float x = 50; // Posición en la cola
        float y = 400;
        
        clientes.add(new Cliente(x, y, pedido));
    }
    
    private String generarPedidoAleatorio() {
        // TODO: Implementar generación de pedidos
        return "Plato misterioso";
    }
    
    public void entregarPlato(Cliente cliente) {
        // Calcular puntuación según similitud
        // TODO: Convertir pedido a lista de ingredientes esperados
        float similitud = 0.5f; // Temporal
        
        if (similitud >= 0.9f) {
            puntuacion += GameConfig.PUNTOS_PLATO_PERFECTO;
            cliente.setEstado(Cliente.Estado.SATISFECHO);
            reputacion += 5;
        } else if (similitud >= 0.5f) {
            puntuacion += GameConfig.PUNTOS_PLATO_BUENO;
            cliente.setEstado(Cliente.Estado.SATISFECHO);
        } else {
            cliente.setEstado(Cliente.Estado.ENFADADO);
            reputacion -= 10;
        }
        
        platoActual.limpiar();
        clientes.remove(cliente);
    }
    
    // Getters
    public int getPuntuacion() { return puntuacion; }
    public int getReputacion() { return reputacion; }
    public List<Cliente> getClientes() { return clientes; }
    public Plato getPlatoActual() { return platoActual; }
}
```

---

## ✅ Checklist de Progreso

### Fase 1: Arquitectura Base
- [ ] Modificar `Main.java` para extender `Game`
- [ ] Crear `MenuScreen.java`
- [ ] Crear `GameScreen.java`
- [ ] Crear `GameConfig.java`
- [ ] Probar navegación entre pantallas

### Fase 2: Entidades
- [ ] Crear carpeta `entities/`
- [ ] Implementar `Cliente.java`
- [ ] Implementar `Ingrediente.java`
- [ ] Implementar `Plato.java`
- [ ] Probar que las entidades funcionan

### Fase 3: Mecánicas
- [ ] Crear carpeta `controllers/`
- [ ] Implementar `InputHandler.java` para drag & drop
- [ ] Zona de preparación del plato
- [ ] Botón de entrega

### Fase 4: Sistema de Juego
- [ ] Implementar `GameManager.java`
- [ ] Sistema de spawn de clientes
- [ ] Sistema de puntuación
- [ ] Condición de fin de partida

---

## 📞 Coordinación con Olga

| Tema | Decisión Pendiente |
|------|-------------------|
| Resolución | ¿1280x720? |
| Sprites | Olga creará los assets gráficos |
| Canvas | El canvas de dibujo se integrará con tu sistema de pedidos |
| HUD | Olga creará el HUD, tú le pasas puntuación/reputación |

---

## 🔧 Comandos Útiles

```bash
# Ejecutar el juego (desde la raíz del proyecto)
./gradlew lwjgl3:run

# Limpiar y compilar
./gradlew clean build
```

---

**¡Buena suerte con la implementación!** 🚀

---

## 🌟 PRÓXIMOS PASOS - Basado en la Nueva Idea Top-Down Roguelike

> **Nota:** El proyecto ha evolucionado hacia un **juego roguelike top-down** con estética inspirada en **Cult of the Lamb**. A continuación se detallan las funcionalidades pendientes basadas en el documento de diseño.

---

### 🎮 Estructura General del Mundo

El juego consiste en un **mundo explorable por zonas**, todas compartiendo una base de bosque vivo con diferentes temáticas espirituales/culturales. Cada zona tiene:
- Un **Dios Dragón** (puede ser jefe final, NPC, o estar muerto)
- Una **idea emocional o filosófica** central
- **Enemigos y NPCs** únicos

---

### 🗺️ ZONAS A IMPLEMENTAR

#### Zona 1: "La Foresta de los Sueños" (Angelical/Onírico)
- [ ] **Estética:** Bosque frondoso con luz suave, estrellas flotando, nubes bajas
- [ ] **Paleta de colores:** Azul, blanco, dorado, azul pálido
- [ ] **Criaturas a crear:**
  - [ ] Ciervo blanco con alas translúcidas
  - [ ] Búho con halo flotante y ojos brillantes (observadores del dragón)
  - [ ] Enemigos: seres hechos del brillo del cielo
- [ ] **Dios Dragón:** *El Dragón Onírico*
  - [ ] Diseño: mezcla de Aurelion Sol + ángel con ojos en las alas + estética "Noche Estrellada"
  - [ ] Lore: Su luz controla las mentes de quienes la observan demasiado

#### Zona 2: "La Arboleda Farolín" (Oriental/Liyue)
- [ ] **Estética:** Árboles con faroles, edificios con tejados chinos, luz artificial vs oscuridad del bosque
- [ ] **Paleta de colores:** Amarillo cálido, rojo oscuro, marrón, verde musgo
- [ ] **Criaturas a crear:**
  - [ ] Espíritus con máscaras
  - [ ] Gatos espirituales
  - [ ] Animales del calendario chino
- [ ] **Dios Dragón:** *El Dragón de las Raíces Eternas*
  - [ ] Diseño: Largo y serpentino con raíces y máscaras festivas
  - [ ] Lore: Representa la tradición - "¿Cuándo honrar el pasado se convierte en una prisión?"

#### Zona 3: "El Bosque del Crepúsculo Funerario" (Transilvania/Gótico)
- [ ] **Estética:** Árboles retorcidos como manos, cementerios con raíces, niebla espesa
- [ ] **Paleta de colores:** Morado, verde oscuro, negro azulado
- [ ] **Criaturas a crear:**
  - [ ] Murciélagos gigantes
  - [ ] Espíritus atados a lápidas
  - [ ] Criaturas ni vivas ni muertas
  - [ ] Enemigos: seres que temen desaparecer
- [ ] **Dios Dragón:** *El Dragón del Último Suspiro*
  - [ ] Diseño: Alas de murciélago, cuerpo fragmentado, ojos que brillan al atacar
  - [ ] Lore: Representa la muerte/final/aceptación - "Aceptar el final como parte natural de la vida"

#### Zonas Adicionales (Futuro)
- [ ] **Zona 4: El Bosque Sumergido** - Árboles bajo agua, raíces como corales, dragón anfibio
- [ ] **Zona 5: El Bosque Calcinado** - Árboles quemados vivos, brasas, ceniza como nieve
- [ ] **Zona 6: El Bosque del Reflejo** - Cristales en árboles, reflejos distorsionados, dragón fragmentado

---

### 🎨 SISTEMA DE PERSONAJES

- [ ] **Editor de Personajes Leve:**
  - [ ] El editor debe estar vinculado al **rol del personaje**
  - [ ] Ejemplos de roles:
    - Mujer + Mago → Capa azul + bastón
    - Hombre + Mago → Poncho azul + bastón
    - Monstruo + Asesino → Capa roja + cuchillo
  - [ ] La personalización visual depende del rol seleccionado

---

### ⚔️ MECÁNICAS ROGUELIKE

- [ ] **Sistema de muerte/despertar:**
  - [ ] Final del juego: matar al boss y "despertar de un sueño"
  - [ ] Sistema de runs con progresión persistente

- [ ] **Sistema de combate top-down:**
  - [ ] Mejorar el sistema actual de `Player.java` y `Entity.java`
  - [ ] Añadir ataques especiales según rol

- [ ] **Sistema de zonas:**
  - [ ] Implementar transiciones entre zonas
  - [ ] Guardar progreso de zonas completadas
  - [ ] Desbloqueo progresivo de zonas

---

### 🐉 SISTEMA DE JEFES (DIOSES DRAGÓN)

- [ ] **Clase base `DragonGod.java`:**
  - [ ] Patrones de ataque únicos por dragón
  - [ ] Fases de batalla
  - [ ] Diálogos y lore

- [ ] **Implementación por zona:**
  - [ ] Dragón Onírico (Zona 1) - Ataques de luz/control mental
  - [ ] Dragón de las Raíces Eternas (Zona 2) - Ataques de raíces/invocación
  - [ ] Dragón del Último Suspiro (Zona 3) - Ataques fragmentados/niebla

---

### 🎵 AUDIO Y AMBIENTE

- [ ] **Música ambiental por zona:**
  - [ ] Zona 1: Melodía etérea, onírica
  - [ ] Zona 2: Instrumentos orientales, percusión suave
  - [ ] Zona 3: Órgano, sonidos góticos
  
- [ ] **Efectos de sonido:**
  - [ ] Combate, pasos, ambiente
  - [ ] Sonidos únicos para cada Dios Dragón

---

### 📋 CHECKLIST DE IMPLEMENTACIÓN POR PRIORIDAD

#### Alta Prioridad
- [ ] Definir diseño visual base (estilo Cult of the Lamb)
- [ ] Implementar Zona 1 como prototipo
- [ ] Crear sistema de transición de zonas
- [ ] Implementar primer Dios Dragón

#### Media Prioridad
- [ ] Sistema de editor de personajes
- [ ] Implementar Zonas 2 y 3
- [ ] Sistema de lore/diálogos
- [ ] Más tipos de enemigos

#### Baja Prioridad
- [ ] Zonas adicionales (4, 5, 6)
- [ ] Sistema de música dinámica
- [ ] Achievements/logros
- [ ] Modo historia completo

---

### 🔗 Archivos Clave a Modificar/Crear

| Archivo | Propósito |
|---------|-----------|
| `entities/DragonGod.java` | Clase base para jefes |
| `entities/ZoneEnemy.java` | Enemigos específicos por zona |
| `controllers/ZoneController.java` | Gestión de zonas y transiciones |
| `screens/gameScreens/ZoneScreen.java` | Pantalla específica por zona |
| `utils/LoreManager.java` | Sistema de lore y diálogos |
| `entities/CharacterBuilder.java` | Editor de personajes |

---

### 💡 Notas de Diseño

> *"El bosque eterno que adopta distintas formas según el dios que lo habita."*

- Todas las zonas comparten la base de **bosque vivo**
- Cada zona **superpone** una temática espiritual/cultural
- Los Dioses Dragón pueden tener roles diferentes:
  - **Jefe final** de la zona
  - **NPC** que ayuda/guía
  - **Muerto** (lore del pasado)
