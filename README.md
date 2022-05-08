# EULER
Un simulador de grafos escrito en Java mediante JavaFX

El presente proyecto, comenzó como un peroyeto de asignatura de Estructuras de datos, y cuenta con las características básicas de un diseñador de grafos.

## Principales características
- Inserción de nodos y aristas utilizando sólo el mouse
- Eliminación dinámica de nodos y aristas
- Selección de tipo de grafo (Dirigido/No diriido)
- Visualización de la matriz y lista de adyacencia
- Limpiado total del grafo con un click
- Recorridos BFS y DFS con animación y desde cualquier nodo del grafo
- Exportado a imagen PNG (fondo transparente)
- Permite el guardado del editable del grafo

## Capturas de pantalla

## Uso

### Soy un usuario, me gustan los dispositivos plug-and-play

Si eres un usuario, solo dirigete a la carpeta ```prodction```, donde encontraras la carpeta con el bundle para Windows, solo ejecuta el archivo ```.exe```

### Soy desarrollador y me gusta el dolor

Mi querido desarrollador, tu lo tienes más complicado

1. Se recomienda el uso de [IntelIJ IDEA](https://www.jetbrains.com/idea/)
2. Descargar el [JavaFX SDK](https://gluonhq.com/products/javafx/) y decomprimirlo
3. Descargar e instalar el [JDK](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) y decomprimirlo
4. Si deseas editar los FXML sin saber FXML puedes utilizar [Scene Builder](https://gluonhq.com/products/scene-builder/)
5. Una vez clonado el repositorio y abierto en el IDE, ve a ``` File > project structure > modules > EULER > Dependencies``` y agrega la la carpeta ```lib``` de el JavaFX SDK
6. Seleccionar el Lenguage Level 8 y el JDK 1.8 en ```File > Project structure > Project```
7. Luego ve a la pestaña ```Run > Edit Configuration``` y selecciona ```Modify Options > Add VM Options```
8. En este paso, es importante reemplazar los parametros correctamente:
  ```
    --module-path "<Carpeta lib del JavaFX SDK>;<Carpeta donde se generan los .class del proyecto>" --add-modules javafx.controls,javafx.fxml
  ```
  por ejemplo
  ```
    --module-path "D:\javafx-sdk-18.0.1\lib;E:\Proyectos\EULER\out\production\EULER" --add-modules javafx.controls,javafx.fxml
  ```
7. Ejecuta y disfruta. 

## Datos técnicos
- JDK 1.8
- openSDK JavaFX SDK 18.0.1 (FXML)
- Lenguage level 8

## Contribuidores
- Paulo Aguilar
- Geovanny Vega
- Freddy Lamar
- Javier Matamoros
