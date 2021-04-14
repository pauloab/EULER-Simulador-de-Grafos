
package Controlador;

import Componentes.ListaNodo;
import grafos.GrafoMA;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * EULER 1.3 Simulador de grafos
 * Controlador de Lista de Adyacencia 
 * Obtiene el grafo y dibuja su lista de adyacencia
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class ControladorDeListaAdj implements Initializable {

    @FXML  
    private Group group;
    
    private GrafoMA grafo;
    
    /**
     * Método implmentado debido a la interfaz Initializable.
     * Para mayor informacion remitase la documentación de JavFX.
     * @param url Locazion para resolver las rutas relativas  
     * @param rb  Se llama para inicializar el controlador después de que el objeto raíz del Grafo se haya procesado por completo.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Método setter de Grafo
     * @param grafo Objeto de GrafoLA
     */
    public void setGrafo(GrafoMA grafo){
        this.grafo = grafo;
    }
    
    

    /**
     * Dibuja la lista de adjacencia.
     */
    public void drawAdjList(){
        group.getChildren().clear();
        // Establece las coordenadas del primer rectángulo
        double x = 50, y = 50;
        ListaNodo nodo;
        int n = grafo.obtenerNumVertices();
        
        for (int i = 0; i < n; i++) {
            // Se generan los rectángulos verticalmente
            nodo = new ListaNodo(""+i, x, y);
            group.getChildren().add(nodo);
            for (int j = 0; j < n; j++) {
                // Se verifica los nodos conexos a ese nodo insertado en vertical
                // y se añaden de manera horizontal y con la flecha
                if (grafo.existeArista(i, j)) {
                   nodo = new ListaNodo(""+j,nodo);
                   group.getChildren().add(nodo);
                }
            }
            y+=nodo.getAltura();
        }
    }
    
}
