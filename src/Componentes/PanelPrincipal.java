package Componentes;

import javafx.collections.ObservableList;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * EULER 1.3 Simulador de grafos
 * Clase gráfica base que contiene y comunica nodos y aristas.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */

public class PanelPrincipal extends Pane {

    private Vertice conexionNodo;
    private Line lineaTemporal; 
    
    /**
     * Constructor que grafica una arista entre los vertices
     */
    public PanelPrincipal() {
        this.lineaTemporal = new Line();
        
        getChildren().add(lineaTemporal);
        
    }
    
    /**
     * Establece un parámetro personalizado isFocussed dentro de un
 objeto tipo Vertice dentro de la clase PanelPrincipal.
     * Determinará que elemento está en foco (Seleccionado) por el usuario.
     * @param nodo Vertice al que se le dará el foco
     */
    public void setFocusTo(Vertice nodo){
        if (nodo != null) {
            nodo.setEstaEnfocado(true);
        }
        ObservableList children = this.getChildren();
        for (Object nodeX : children) {
            if (nodeX instanceof Vertice) {
                if (nodeX != nodo) {
                    ((Vertice) (nodeX)).setEstaEnfocado(false);
                }
            }
        }
    }

     /**
      * Método getter de ConexionNodo.
     *  Anilla el nodo de conexion y retorna su valor previo.
     * @return Objeto Vertice que toma el vértice referencia, el vértice origen donde se trazo la arista
     */
    public Vertice getConexionNodo() {
        Vertice node = conexionNodo;
        conexionNodo = null;
        return node;
    }

    /**
     * Método setter de ConexionNodo.
     * @param conexionNodo Objeto Vertice que estable al conexion entre los vértices
     */
    public void setConexionNodo(Vertice conexionNodo) {
        this.conexionNodo = conexionNodo;
    }
    
    /**
     * Método getter de LineaTemporal.
     * @return Objeto LineaTemporal
     */
    public Line getLineaTemporal() {
        return lineaTemporal;
    }
    
    /**
     * Método getter de NodoPorId.
     * @param id Tipo de dato Integer que recibe la id del vértice
     * @return El Objeto Vertice
     */
    public Vertice getNodoPorId(int id){
        Vertice obj;
        for (Object node : this.getChildren()) {
            if (node instanceof Vertice) {
                obj = (Vertice) (node);
                if (obj.getNodoId() == id) {
                    return obj;
                }
            }
        }
        return null;
    }
    
      /**
     * Método getter de FocusedNode.
     * @return El Objeto Vertice
     */
    public Vertice getFocusedNode(){
        Vertice obj;
        for (Object node : this.getChildren()) {
            if (node instanceof Vertice) {
                obj = (Vertice) (node);
                if (obj.getIsFocused()) {
                    return obj;
                }
            }
        }
        return null;
    }
}
