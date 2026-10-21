<p align="center">
  <img src="Sources/ui/images/logotipo.png" alt="Logotipo de Siempre Contigo" width="180">
</p>

<h1 align="center">Siempre Contigo</h1>

<p align="center">
  Tres minijuegos de memoria y coordinación desarrollados en Java con Swing.
</p>

<p align="center">
  <strong>Isabel Alonso Casas</strong><br>
  Proyecto final de Programación Orientada a Objetos · Curso 2024–2025<br>
  Universidad Pontificia Comillas
</p>

<p align="center">
  <a href="Proyecto.pdf"><strong>Leer la memoria del proyecto (PDF)</strong></a>
</p>

## Sobre el proyecto

**Siempre Contigo** es una aplicación de escritorio creada desde cero como proyecto final de Programación Orientada a Objetos. Reúne tres minijuegos pensados para personas mayores, con actividades de memoria visual, atención y coordinación.

El proyecto está inspirado y dedicado a mi abuela. Combina una interfaz personalizada, fotografías familiares, ilustraciones y efectos de sonido.

## Minijuegos

| Juego | En qué consiste |
| --- | --- |
| **Cuela la colada** | Encestar una prenda en una cesta mediante el control del movimiento, con niveles y vidas. |
| **Emparejados** | Encontrar parejas de cartas con fotografías familiares. |
| **¡Baila conmigo!** | Observar una secuencia de casillas iluminadas y repetirla en el mismo orden, con dificultad progresiva y sonidos. |

La aplicación incluye un menú para elegir el juego, botones para volver al inicio y ventanas personalizadas para los resultados y los cambios de nivel.

## Tecnologías y conceptos utilizados

- **Java y programación orientada a objetos**, con organización en paquetes.
- **Swing y AWT** para las ventanas, los componentes y la distribución de la interfaz.
- **Gestión de eventos** del ratón, botones y ventanas.
- **Temporizadores e hilos** para las animaciones y las secuencias.
- **Java Sound** para reproducir efectos y música de fondo.
- **Colecciones** para gestionar cartas y secuencias.

## Organización del repositorio

| Ruta | Contenido |
| --- | --- |
| `Sources/domain/APP.java` | Punto de entrada y menú principal. |
| `Sources/ui/` | Los tres minijuegos y las ventanas de resultados. |
| `Sources/ui/images/` | Imágenes de la interfaz y de las cartas. |
| `Sources/util/` | Clases auxiliares para imágenes y sonido, y archivos de audio. |
| `Proyecto.pdf` | Memoria original con la explicación, capturas y aprendizajes. |
| `ejecutar.bat` | Compilación y ejecución desde Windows. |

## Cómo ejecutarlo

### Requisitos

- Windows y un **JDK 17 o posterior** que permita utilizar `java` y `javac` desde la terminal.
- Una pantalla gráfica y, para los efectos de sonido, salida de audio.

El código original utiliza rutas de Windows para las imágenes y el sonido. Es importante mantener las carpetas y ejecutar la aplicación desde `Sources`.

### Opción 1: archivo de inicio

1. Descarga el repositorio desde **Code → Download ZIP** y descomprímelo.
2. Abre la carpeta del proyecto y haz doble clic en **`ejecutar.bat`**.
3. El script compilará el código en `build` y abrirá el menú principal.

### Opción 2: terminal de Windows

Desde la carpeta principal del repositorio:

```bat
cd Sources
javac -encoding UTF-8 -d ..\build domain\*.java ui\*.java util\*.java
java -cp ..\build domain.APP
```

### Desde un entorno de desarrollo

Abre el proyecto en tu IDE, configura `Sources` como carpeta de código fuente y **también como directorio de trabajo**, y ejecuta la clase `domain.APP`.

## Documentación

La [memoria en PDF](Proyecto.pdf) contiene la motivación del proyecto, la descripción de los minijuegos, capturas de las pantallas y los conocimientos adquiridos.

## Autoría

**Isabel Alonso Casas** · Mayo de 2025.

Proyecto académico de Programación Orientada a Objetos, realizado en la Universidad Pontificia Comillas.
