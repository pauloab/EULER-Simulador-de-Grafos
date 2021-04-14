
package Controlador;

import grafos.GrafoMA;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * EULER 1.3 Simulador de grafos
 * Controlador de Matriz de Adyacencia 
 * Obtiene el grafo y dibuja su matriz de adyacencia
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class ControladorDeMatrizAdj implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Group matrizScroll;
    
    private GrafoMA grafo;
    
    
    /**
     * Método implmentado debido a la interfaz Initializable.
     * Para mayor informacion remitase la documentación de JavFX.
     * @param url Locazion para resolver las rutas relativas  
     * @param rb  Se llama para inicializar el controlador después de que el objeto raíz del Grafo se haya procesado por completo.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
    /**
     * Método setter de Grafo
     * @param grafo Objeto de GrafoLA
     */
    public void setGrafo (GrafoMA grafo){
        this.grafo = grafo;
    }

    

    /**
     * Dibuja la matriz de adjacencia en el GridPane
     */
    public void drawMatrix(){ 
        matrizScroll.getChildren().clear();
        int n = grafo.obtenerNumVertices();
        Label etiqueta;
        double x=10,y=40,x1=40,y1=10;
        
        // Establece en negrita los nombres de las filas y columnas
        for (int i = 0; i < n; i++) {
            etiqueta = new Label("" + i);
            etiqueta.getStyleClass().add("headerLabel");
            etiqueta.setLayoutX(x1);
            etiqueta.setLayoutY(y1);
            matrizScroll.getChildren().add(etiqueta);
            
            etiqueta = new Label("" + i);
            etiqueta.getStyleClass().add("headerLabel");
            etiqueta.setLayoutX(x);
            etiqueta.setLayoutY(y);
            matrizScroll.getChildren().add(etiqueta);
            
            x1+=40;
            y+=40;
        }
        
        x=40;
        y=40;
        
        // Rellena la matriz con 1 o 0, 1 si hay arista, 0 si no
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                etiqueta = new Label("" + (grafo.existeArista(j, i) ? 1 : 0 ));
                etiqueta.getStyleClass().add("valueLabel");
                etiqueta.setLayoutX(x);
                etiqueta.setLayoutY(y);
                x+=40;
                matrizScroll.getChildren().add(etiqueta);
            }
            y+=40;
            x=40;
        }
        
    }
    
}
