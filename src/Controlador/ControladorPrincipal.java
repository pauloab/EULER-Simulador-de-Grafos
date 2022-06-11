package Controlador;

import Componentes.AristaDirigida;
import Componentes.PanelPrincipal;
import Componentes.Arista;
import Componentes.Vertice;
import Componentes.AristaNoDirigida;
import Componentes.ToggleSwitch;
import Utilidad.FileManager;
import grafos.GrafoMA;
import Utilidad.Recorridos;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;


/**
 * EULER 1.3 Simulador de grafos
 * Controlador de la ventana principal.
 * Contiene todos los eventos y manejo de graficas de grafos del programa.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class ControladorPrincipal implements Initializable {

    /*
    Problemas conocidos
    -Paneo de la superficie
     */

    @FXML
    private Pane panelModoDiseño;

    @FXML
    private Label lbMsg;

    // Panel que ordena los elementos a los bordes y entro de si
    @FXML
    private BorderPane panelBorde;

    @FXML
    private Label profundidadID;

    @FXML
    private Label amplitudID;

    // Panel principal personalizado donde se insertan los nodos
    private PanelPrincipal panelPrincipal;
    
    // Menú contextual que se cambia conforme sea necesario - oara toda la clase
    private ContextMenu menuContextual;

    // Indica el modo de dibujado del grafo (direccionado o no direccionado)
    private boolean dirigido;
    
    // Coordenadas de mouse que permiten calcular el arrastrado de los nodos
    private double cordX;
    private double cordY;
    
    // Grafo TAD
    private GrafoMA grafo;
    
    
    // Indica los controladores de Matriz y Lista de adyacencia
    private ControladorDeMatrizAdj matrizAdjControlador;
    private Stage matrizAdjStage;
    private ControladorDeListaAdj listaAdjControlador;
    private Stage listaAdjStage;

    private Stage aboutStage;
    private boolean clickBloqueado;

    
    
    /**
     * Eventos de Aristas, cuando el usario interactua con la arista mediante el click del mouse.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void eventoAristaClick(MouseEvent event) {

        if (event.getButton() == MouseButton.SECONDARY) {
            menuContextual.getItems().clear();
            Arista arista = (Arista) event.getSource();
            MenuItem itemEliminar = new MenuItem("Eliminar arista");
            itemEliminar.setOnAction(e -> {
                grafo.eliminaArista(arista.getIdNodoInicio(), arista.getIdNodoFinal());
                panelPrincipal.getChildren().remove(arista);
                profundidadID.setText("");
                amplitudID.setText("");
                actualizarMatrizLista();
            });
            // Añade la opcion Eliminar al Menu contextual  
            menuContextual.getItems().add(itemEliminar);

            // Indicar donde aparece el Menu contextual
            menuContextual.show(arista, event.getScreenX(), event.getScreenY());
        }
    }

    /**
     * Evento que se lanza al completar un click sobre un nodo.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void eventoNodoClick(MouseEvent event) {
        Vertice nodo = (Vertice) (event.getSource());
        // Boton derecho
        if (event.getButton().equals(MouseButton.SECONDARY) && !clickBloqueado) {
            // Liampiar el menu para evitar que el usuario al hacer doble click y se cree 2 vces de forma innecesaria
            menuContextual.getItems().clear();
            // Creamos un objeto de eliminar dentro del menu contextual
            MenuItem itemEliminar = new MenuItem("Eliminar Nodo");
            // Se le asigna comportamineto al item
            // Funcion anonima - operador landa
            itemEliminar.setOnAction(e -> {
                eliminarNodo(e, nodo);

            });

            // TAD profesor
            // Recorrido Profundidad
            MenuItem itemDFS = new MenuItem("Iniciar DFS aquí");
            // Funcion anonima
            itemDFS.setOnAction(e -> {
                String recorrido = Recorridos.profundidad(grafo, nodo.getNodoId());
                String recorridoIndividual[] = recorrido.split(" ");
                double intervaloAnimacion = 100;
                for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
                    // Buscar nodos, meidante la parte visual, los circulos
                    panelPrincipal.getNodoPorId(Integer.parseInt(recorridoIndividual[i])).startColorAnimation(intervaloAnimacion);
                    intervaloAnimacion += 1000;
                }
                profundidadID.setText(recorrido);
            });

            MenuItem itemBFS = new MenuItem("Iniciar BFS aquí");
            itemBFS.setOnAction(e -> {
                String recorrido = Recorridos.amplitud(grafo, nodo.getNodoId());
                String recorridoIndividual[] = recorrido.split(" ");
                double intervaloAnimacion = 100;
                for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
                    // Buscar nodos, meidante la parte visual, los circulos
                    panelPrincipal.getNodoPorId(Integer.parseInt(recorridoIndividual[i])).startColorAnimation(intervaloAnimacion);
                    intervaloAnimacion += 1000;
                }
                amplitudID.setText(recorrido);

            });

            // Agrega Menu Contextual
            menuContextual.getItems().addAll(itemEliminar, itemDFS, itemBFS);
            // Se lo muestra si aparece a la izquierda o derecha
            menuContextual.show(nodo, Side.RIGHT, 5, 5);
        }
        clickBloqueado = false;
        event.consume();
    }

    /**
     * Evento que se lanza al presionar una click del mouse sobre un nodo.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void mousePresionado(MouseEvent event) {
        menuContextual.hide();
        // Obtiene el nodo
        Vertice nodo = (Vertice) (event.getSource());
        panelPrincipal.setFocusTo(nodo);
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            cordX = event.getSceneX();
            cordY = event.getSceneY();
            nodo.toFront();
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            //Para saber le radio del nodo
            Circle circulo = nodo.getCirculo();
            //Obtiene la linea temporal desde el Grafo
            Line lineaTemporal = panelPrincipal.getLineaTemporal();
            lineaTemporal.setVisible(false);

            // Establece la posiccion inicial de la linea temporal
            lineaTemporal.setStartX(nodo.getLayoutX() + circulo.getRadius());
            lineaTemporal.setStartY(nodo.getLayoutY() + circulo.getRadius());

            // Le indica al grafo cual será el nodo inicial de la posible coneccion
            panelPrincipal.setConexionNodo(nodo);
        }
        // Consume el evento para que no se lance en otro elemento    
        event.consume();
    }

    /**
     * Evento que se lanza al arrastrar un click sobre el nodo.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void mouseArrastrado(MouseEvent event) {
        Vertice nodo = (Vertice) (event.getSource());

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            double posX, posY;
            double offsetX = event.getSceneX() - cordX;
            double offsetY = event.getSceneY() - cordY;

            if (nodo.getIsFocused()) {

                posX = nodo.getLayoutX() + offsetX;
                posY = nodo.getLayoutY() + offsetY;

                // Verifica que no salga del Pane
                if (posX >= panelPrincipal.getLayoutX() && posX <= panelPrincipal.getWidth() - 60
                        && posY >= panelPrincipal.getLayoutY() && posY <= panelPrincipal.getHeight() - 66) {

                    // Esteblece la posición gráfica del nodo
                    nodo.setLayoutX(posX);
                    nodo.setLayoutY(posY);
                }

                cordX = event.getSceneX();
                cordY = event.getSceneY();
            }
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            clickBloqueado = true;
            Line tempLine = panelPrincipal.getLineaTemporal();

            //Actualiza la posicion de la línea temporal con la de la posición del mouse
            tempLine.setEndX(event.getSceneX() - panelBorde.getLayoutX());
            tempLine.setEndY(event.getSceneY() - panelBorde.getLayoutY());
            tempLine.setVisible(true);
        }
    }

    /**
     * Evento que se lanza al soltar el arastrado de click sobre el elemento.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void liberarArrastrado(MouseDragEvent event) {
        // Obtener los nodos de inicio y final y el grafo
        Vertice finNodo = (Vertice) (event.getSource());
        Vertice nodoPrincipio = panelPrincipal.getConexionNodo();
        AristaDirigida arista;
        AristaNoDirigida aristaNo;
        if (event.getButton().equals(MouseButton.SECONDARY) && nodoPrincipio != null) {
            // Evaluar si ya estan conexos
            if (!grafo.existeArista(nodoPrincipio.getNodoId(), finNodo.getNodoId())) {
                // Construye la arista y reemplaza a la línea temporal
                if (dirigido) {
                    arista = new AristaDirigida(nodoPrincipio, finNodo);
                    arista.setOnMouseClicked(this::eventoAristaClick);
                    panelPrincipal.getChildren().add(arista);
                    arista.toBack();
                } else {
                    aristaNo = new AristaNoDirigida(nodoPrincipio, finNodo);
                    aristaNo.setOnMouseClicked(this::eventoAristaClick);
                    panelPrincipal.getChildren().add(aristaNo);
                    aristaNo.toBack();
                }

                grafo.insertaArista(nodoPrincipio.getNodoId(), finNodo.getNodoId());
                grafo.imprimirGrafo();

                // Limpia los recorridos
                limpiarRecorridos();

                // Actualiza mariz y Lista ady
                actualizarMatrizLista();
            }
        }
        event.consume();
    }

    /**
     * Evento que se lanza al levantar un botón del mouse.
     * @param event Evento de mouse que contiene la información relacionada al evento
     */
    public void clickLiberado(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            /* Objener y ocultar la línea temporal que se traza cuando
                el usuario mantiene arrastra el click */
            Line lineaTemporal = panelPrincipal.getLineaTemporal();
            lineaTemporal.setVisible(false);

        }
        event.consume();
    }

    /**
     * Eventos de comportamiento para el panel.
     * @param e Evento de mouse que contiene la información relacionada al evento
     */
    public void panelPrincipalClickLevantado(MouseEvent e) {
        menuContextual.hide();
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            double posX = e.getSceneX();
            double posY = e.getSceneY();
            if (grafo.obtenerNumVertices() <= 40) {
                if (posX >= panelPrincipal.getLayoutX() + panelBorde.getLayoutX() + 30 && posX <= panelPrincipal.getScene().getWidth() - 45
                        && posY >= panelPrincipal.getLayoutY() + panelBorde.getLayoutY() + 30 && posY <= panelPrincipal.getHeight() - 45) {
                    Vertice nodo = new Vertice(e.getX(), e.getY(), 30, "" + grafo.obtenerNumVertices());
                    nodo.setNodoId(grafo.obtenerNumVertices());

                    // Añade event listeners 
                    nodo.setOnMouseClicked(this::eventoNodoClick);
                    nodo.setOnMouseDragged(this::mouseArrastrado);
                    nodo.setOnMouseDragReleased(this::liberarArrastrado);
                    nodo.setOnMousePressed(this::mousePresionado);
                    nodo.setOnMouseReleased(this::clickLiberado);
                    nodo.setOnDragDetected(event -> nodo.startFullDrag());

                    panelPrincipal.getChildren().add(nodo);

                    // Añadir el nodo en el TAD
                    grafo.insertaVertice(1);

                    // Actualiza mariz y Lista ady
                    actualizarMatrizLista();

                    // Limpia los recorridos
                    limpiarRecorridos();
                }
            } else {
                showTempMsg("El número de vértices del grafo ha superado el límite de 40");
            }
        }else {
            panelPrincipal.setFocusTo(null);
        }
        e.consume();
    }

   /**
    * Evento que ocurre al preisonar el teclado sobre la ventana.
    * @param event Evento de tecaldo que contiene la información relacionada al evento
    */
    public void eventoKeyRealessed(KeyEvent event) {
        Vertice nodo = panelPrincipal.getFocusedNode();

        if (event.getCode() == KeyCode.DELETE && nodo != null) {
            eliminarNodo(event, nodo);
        }
    }

    /**
     * Event Listener de el botón de ayuda.
     *
     * @param event Evento de mouse
     */
    public void btAyudaClick(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://bit.ly/3wOcIq7"));
        } catch (URISyntaxException ex) {
            // vacio
        } catch (IOException ex) {
            showTempMsg("Error al acceder al navegador");
        }
    }
      
    /**
     * Evento que se lanza cuando se da click en el boton Matriz de adyacencia (se establece en el FXML). 
     */
    public void mostarMatrizAdj() {
        matrizAdjStage.show();
    }

    public void mostrarAbout() { aboutStage.show(); }

    /**
     * Evento que se lanza cuando se da click en el botón lista de adyacencia (se establece en el FXML).
     */
    public void mostarListaAdj() {
        listaAdjStage.show();
    }
    
    /**
     * Evento que se lanza con el click de "limpiar grafo" (establecido en el FXML).
     */
    public void limpiarGrafo() {
        limpiarRecorridos();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(panelPrincipal.getLineaTemporal());
        establaceDdirigido(dirigido);
        actualizarMatrizLista();
    }

    /**
     * Procedimiento para actualizar la Matriz y Lista de Adyacencia. 
     */
    public void actualizarMatrizLista() {
        listaAdjControlador.setGrafo(grafo);
        listaAdjControlador.drawAdjList();

        matrizAdjControlador.setGrafo(grafo);
        matrizAdjControlador.drawMatrix();
    }

    /*
     * Procedimiento para eliminar un vértice en lo visual y en el modelo.
     */
    public void eliminarNodo(Event e, Vertice nodo) {
        int n;
        // Lista de hijos del panel
        ObservableList listaElementosPanel = panelPrincipal.getChildren();
        int idNodo = nodo.getNodoId();
        int tamano = listaElementosPanel.size();
        Arista aristaTemp;
        // Es una lista de aristas que almacenara los objetos arista graficos que se eliminaran de los hijos del panel
        ArrayList<Arista> aristas = new ArrayList<>();
        Vertice nodoTemp;

        for (int i = 0; i < tamano; i++) {
            Object element = listaElementosPanel.get(i);
            if (element instanceof Arista) {
                aristaTemp = (Arista) (element);
                // Verifica si al arista inicia y termina ene el mismo vertice
                if (aristaTemp.getIdNodoInicio() == idNodo
                        && aristaTemp.getIdNodoFinal() == idNodo) {
                    // Verifica que la arista no haya sido agregada a la lista de arista por eliminar
                    // Sino esta en la lista la agrega
                    if (aristas.indexOf(aristaTemp) == -1) {
                        aristas.add(aristaTemp);
                    }
                    // Verifica que la arista este conexa al vertice a eliminar y no se un bucle
                    // Entonces la agrega a la lista
                } else if ((aristaTemp.getIdNodoInicio() == idNodo
                        || aristaTemp.getIdNodoFinal() == idNodo)
                        && aristaTemp.getIdNodoFinal() != aristaTemp.getIdNodoInicio()) {
                    aristas.add(aristaTemp);
                } 
            }
            // Verifica que el elemnto a recorrer sea un vertice
            else if (element instanceof Vertice) {
                nodoTemp = ((Vertice) (element));
                // Verifica si el id del vertice actual es mayor al  vertice que se va a eliminar
                // Le resta 1 a los vertices mayores al id de los vertices mayores al eliminar
                if (nodoTemp.getNodoId() > nodo.getNodoId()) {
                    nodoTemp.setNodoId(nodoTemp.getNodoId() - 1);
                }
            }
        }
        // Recorre la lista de aristas a eliminar y las elimna de los hijos del panel
        for (Arista arista : aristas) {
            listaElementosPanel.remove(arista);
        }
        // Eliminar nodo
        listaElementosPanel.remove(nodo);

        grafo.eliminarVertice(nodo.getNodoId());

        // Actualiza la matriz y lista ady
        actualizarMatrizLista();

        // Limpia los recorridos
        limpiarRecorridos();

    }

    /**
     * Procedimiento que limpia las cajas de texto de los recorridos.
     */
    public void limpiarRecorridos() {
        profundidadID.setText("");
        amplitudID.setText("");
    }
    
 /**
     * Realiza la accion de guardado del archivo PNG del grafo
     */
    public void exportToPng() {

        //Buscando un directorio
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fc.setTitle("Exportar Grafo");
        File file = fc.showSaveDialog(null);

        if (file != null) {
            panelPrincipal.setFocusTo(null);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            WritableImage image = panelPrincipal.snapshot(params, null);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                showTempMsg("Error al acceder al directorio especificado.");
            }
        }

    }

    public void guardarGrafo(){
        //Buscando un directorio
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de Grafo Euler", "*.egraph"));
        fc.setTitle("Guardar Grafo");
        File file = fc.showSaveDialog(null);

        if (file != null) {
            try {
                FileManager.guardarArchivoDeGrafo(panelPrincipal, grafo, file.getAbsolutePath());
            } catch (IOException e) {
                showTempMsg("Error al guardar el grafo.");
                e.printStackTrace();
            }
        }
    }

    public void abrirGrafo(){
        //Buscando un directorio
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de Grafo Euler", "*.egraph"));
        fc.setTitle("Abrir Grafo");
        File file = fc.showOpenDialog(null);

        if (file != null) {
            try {
                this.grafo = FileManager.cargarArchivoGrafo(file.getAbsolutePath(), this);
            } catch (IOException e) {
                showTempMsg("Error al abrir el grafo.");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                showTempMsg("Archivo de grafo corrupto.");
            }
        }
    }

   /**
     * Método implmentado debido a la interfaz Initializable.
     * Para mayor informacion remitase la documentación de JavFX.
     * @param url Locazion para resolver las rutas relativas  
     * @param rb  Se llama para inicializar el controlador después de que el objeto raíz del Grafo se haya procesado por completo.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image image = new Image(getClass().getResource("/Recursos/logoEuler.png").toExternalForm());
        //Precarga la ventana de Matriz de adyacencia
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AdjMatrix.fxml"));
            Parent root = loader.load();
            matrizAdjStage = new Stage();
            Scene escena = new Scene(root);
            matrizAdjStage.setScene(escena);
            matrizAdjStage.getIcons().add(image);
            matrizAdjStage.setTitle("EULER - Simulador de Grafos - Matriz de Adyacencia");
            matrizAdjControlador = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Precarga la ventana de Lista de adyacencia
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AdjList.fxml"));
            Parent root = loader.load();
            listaAdjStage = new Stage();
            Scene escena = new Scene(root);
            listaAdjStage.setScene(escena);
            listaAdjStage.getIcons().add(image);
            listaAdjStage.setTitle("EULER - Simulador de Grafos - Lista de Adyacencia");
            listaAdjControlador = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clickBloqueado = false;

        //Precarga la ventana de About
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/About.fxml"));
            aboutStage = new Stage();
            aboutStage.setWidth(490);
            aboutStage.setHeight(340);
            aboutStage.setScene(new Scene(root));
            aboutStage.getIcons().add(image);
            aboutStage.setTitle("EULER - Simulador de Grafos");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instancia y agrega al componente padre o "Pane"
        this.panelPrincipal = new PanelPrincipal();
        this.panelBorde.setCenter(panelPrincipal);
        this.menuContextual = new ContextMenu();
        // Añade eventHandlers
        panelPrincipal.setOnMouseReleased(this::panelPrincipalClickLevantado);

        // Añade el ToggleSwitch 
        ToggleSwitch switchModoDiseño = new ToggleSwitch();
        switchModoDiseño.switchOnProperty().addListener(event -> {
            establaceDdirigido(switchModoDiseño.switchOnProperty().getValue());
            limpiarGrafo();
        });
        switchModoDiseño.setLayoutX(10);
        switchModoDiseño.setLayoutY(40);
        panelModoDiseño.getChildren().add(switchModoDiseño);

    }

    /**
     * Procedimiento que muestra un mensaje temporal.
     * La animación dle texto tiene una duraciñon de 5 segundos.
     * @param text Tipo de dato String que recibe el texto que mostrar en pantalla
     */
    private void showTempMsg(String text) {
        lbMsg.setText(text);
        final Animation animacion = new Transition() {

            {
                setCycleDuration(Duration.millis(5000));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                // Cuando el intervalo termina, se cambia a blanco
                if (frac == 1) {
                    lbMsg.setText("");
                }
            }
        };
        animacion.play();
    }

    /**
     * Método setter de establaceDdirigido
     * @param dirigido Tipo de dato Boolean que recibe si el grafo es dirigido o no.
     */
    public void establaceDdirigido(boolean dirigido) {
        this.dirigido = dirigido;
        /*
        Dado que no se puede llamar ni sobreesribir el constructor
        Este setter permite indicar el modo de la ventana, y además, 
        Construir el objeto "grafo", antes de que se ejecute el programa
        y con el modo esteblecido.
         */
        // Se alñade el eventListener de la tecla suprimir
        panelPrincipal.getScene().setOnKeyReleased(this::eventoKeyRealessed);

        this.grafo = new GrafoMA(40, dirigido);
    }

    /**
     * Getter del objeto de panel principal
     * @return retorna un objeto de tipo PanelPrincipal
     */
    public PanelPrincipal getPanelPrincipal(){
        return panelPrincipal;
    }

}
