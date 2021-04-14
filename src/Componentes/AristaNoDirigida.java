
package Componentes;

import javafx.beans.InvalidationListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 * EULER 1.3 Simulador de grafos
 * Clase gráfica encargada de el trazado de aristas entre vértices.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */


public class AristaNoDirigida extends Arista {
    private Line linea;
    private QuadCurve curvaOpcional;
    
    /**
     * Constructor de AristaNoDirigida.
     * Crea la arista.
     * Actualiza la propiedad de posicion en "x" y "y" de la arista.
     * Asocia las propiedades del nodo con el actualizador de posicion de arista.
     * @param inicioNodo Objeto tipo Vertice del inicio del trazado de la arista
     * @param finNodo    Objeto tipo Vertice del final del trazado de la arista
     */
    public AristaNoDirigida(Vertice inicioNodo, Vertice finNodo){
        super(inicioNodo,finNodo,false);
        this.linea = new Line();
        this.linea.setStrokeWidth(3);
        
        this.curvaOpcional = new QuadCurve();
        curvaOpcional.setFill(Color.TRANSPARENT);
        curvaOpcional.setStroke(Color.BLACK);
        this.curvaOpcional.setStrokeWidth(3);
        InvalidationListener lineUpdater = event-> {
            double ox, oy, startX, startY, endX, endY, radio;
            if (inicioNodo.getNodoId() == finNodo.getNodoId()) {          
                /*
                Facilita la generación de una arista bucle, recalclando unarco más abierto
                */
                radio = inicioNodo.getCirculo().getRadius();
                
                //se establecen puntos de inicio y final
                ox = inicioNodo.getLayoutX() + 2*radio;
                oy = inicioNodo.getLayoutY() + 2*radio;
                
                startX = ox;
                startY = inicioNodo.getLayoutY() + radio;
                endX = inicioNodo.getLayoutX() + (radio/2) - 5;
                endY = oy - 10;
                curvaOpcional.setStartX(startX);
                curvaOpcional.setStartY(startY);
                curvaOpcional.setEndX(endX);
                curvaOpcional.setEndY(endY);
                
                curvaOpcional.setControlX(ox);
                curvaOpcional.setControlY(oy+40);
            }else{
                Circle circuloNodo = inicioNodo.getCirculo(); 
            linea.setStartX(inicioNodo.getLayoutX()+circuloNodo.getRadius());
            linea.setStartY(inicioNodo.getLayoutY()+circuloNodo.getRadius());
            linea.setEndX(finNodo.getLayoutX()+circuloNodo.getRadius());
            linea.setEndY(finNodo.getLayoutY()+circuloNodo.getRadius());
            }
        };
        //Asocia las propiedades del nodo con el actualizador de posicion de arista
        inicioNodo.layoutXProperty().addListener(lineUpdater);
        inicioNodo.layoutYProperty().addListener(lineUpdater);
        finNodo.layoutXProperty().addListener(lineUpdater);
        finNodo.layoutYProperty().addListener(lineUpdater);
        lineUpdater.invalidated(null);
        
        if (inicioNodo.getNodoId() != finNodo.getNodoId()) {
            this.getChildren().add(linea);
        }else{
            this.getChildren().add(curvaOpcional);
        }
    }
   
}
