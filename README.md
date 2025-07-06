# EcoMarket-SPA - Guía para ejecutar el backend en IntelliJ IDEA

Este documento explica paso a paso cómo ejecutar el backend de este proyecto Java usando IntelliJ IDEA.

## Requisitos previos

- **Java JDK 8 o superior** instalado.
- **IntelliJ IDEA** (Community o Ultimate).

## Pasos para ejecutar el backend

### 1. Clona el repositorio

Abre una terminal y ejecuta:

```bash
git clone https://github.com/juatapiaoing/EcoMarket-SPA.git
```

Luego abre IntelliJ IDEA.

### 2. Importa el proyecto en IntelliJ

1. Ve a **File > Open...**.
2. Selecciona la carpeta `EcoMarket-SPA` que clonaste.
3. Haz clic en **OK** para importar el proyecto. IntelliJ detectará el proyecto Java automáticamente.

### 3. Espera a que finalice la indexación

IntelliJ descargará las dependencias y realizará la indexación inicial de los archivos del proyecto.

### 4. Configura el JDK

1. Ve a **File > Project Structure...** o presiona `Ctrl+Alt+Shift+S`.
2. En **Project > Project SDK**, selecciona el JDK instalado (8 o superior).
3. Haz clic en **OK**.

### 5. Ubica la clase principal

Busca en el árbol de archivos del proyecto (normalmente en `src/`) la clase "EcoMarketSpaApplication" que contiene el método `public static void main(String[] args)`. Esta será la clase principal desde donde se inicia el backend.

### 6. Ejecuta el backend

1. Haz clic derecho sobre la clase principal.
2. Selecciona **Run 'EcoMarketSpaApplication.main()'**.
3. El backend debería iniciar y mostrar mensajes en la consola de IntelliJ.

### 7. (Opcional) Configura argumentos o variables de entorno

Si el backend requiere argumentos o variables de entorno específicas:
- Haz clic en la flecha junto al botón de ejecución y selecciona **Edit Configurations...**.
- Añade los parámetros necesarios.

---

## Estructura típica del proyecto

```
EcoMarket-SPA/
├── src/
│   └── ... (código fuente Java)
├── README.md
└── ...
```

## Notas adicionales

- Si el proyecto requiere librerías externas y no utiliza un gestor de dependencias como Maven o Gradle, asegúrate de agregarlas manualmente en **Project Structure > Libraries**.
- Consulta los comentarios en el código fuente para detalles adicionales sobre la configuración del backend.

---

¿Dudas o problemas? Abre un issue en GitHub.
