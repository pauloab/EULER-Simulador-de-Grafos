
package Componentes;

import javafx.scene.Cursor;
import javafx.scene.Group;

/**
 * EULER 1.3 Simulador de grafos
 * Clase la cual se derivaran los componentes hijos de arista dirigida y no dirigida.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class Arista extends Group {
    private boolean dirigido;
    private Vertice nodoInicio;
    private Vertice nodoFinal;
    
    /**
     * Constructor que recibe los parametros de Arista.
     * @param idNodoInicio Tipo de dato Integer recibe el inicio del vértice
     * @param idodoFinal   Tipo de dato Integer recibe el final del vértice
     * @param dirigido     Tipo de dato Boolean recibe el tipo de grafo dirigido y no dirigido
     */
    public Arista(Vertice idNodoInicio, Vertice idodoFinal, boolean dirigido){
        this.nodoInicio = idNodoInicio;
        this.nodoFinal = idodoFinal;
        this.dirigido = dirigido;
        this.setCursor(Cursor.HAND);
    }
    
    /**
     * Método getter de IdNodoInicio.
     * @return Tipo de dato Integer recibe el inicio del vértice
     */
    public int getIdNodoInicio() {
        return nodoInicio.getNodoId();
    }

    /**
     * Método getter de IdNodoFinal.
     * @return Tipo de dato Integer recibe el final del vértice
     */
    public int getIdNodoFinal() {
        return nodoFinal.getNodoId();
    }

    /**
     * Método getter de isDirigido.
     * @return Tipo de dato Booleano recibe el tipo de grafo dirigido y no dirigido
     */
    public boolean isDirigido() {
        return dirigido;
    }
    
}
