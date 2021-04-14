
package Componentes;

import static Componentes.ListaNodo.alturaPorDefecto;
import static Componentes.ListaNodo.lognitudFlechaPorDefecto;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * EULER 1.3 Simulador de grafos
 * Componente visual que representa el final de la lista.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */

public class GNDNull extends Group {
    private Line lineaHorizontalR;
    private Line lineaAbajo;
    private Line lineaSubHorizontal1;
    private Line lineaSubHorizontal2;
    private Line lineaSubHorizontal3;
    
    /**
     * Constructor GNDNull.
     * Construye las lineas para realizar el componente visual de referencia del final de la lista.
     * @param x Posición en x 
     * @param y Posición en y
     */
    public GNDNull (double x, double y){
        // lineaHorizontalR Linea Horizontal de referencia para representar el final de la lista
        // lineaAbajo Linea contigua a Linea Horizontal
        // lineaSubHorizontal1
        // lineaSubHorizontal2
        // lineaSubHorizontal3
        this.lineaHorizontalR = new Line(x, y, x+lognitudFlechaPorDefecto, y);
        this.lineaAbajo = new Line(x+lognitudFlechaPorDefecto, y, x+lognitudFlechaPorDefecto, y+alturaPorDefecto/6);
        this.lineaSubHorizontal1 = new Line(x+lognitudFlechaPorDefecto-15, y+alturaPorDefecto/6, x+lognitudFlechaPorDefecto+15, y+alturaPorDefecto/6);
        this.lineaSubHorizontal2 = new Line(x+lognitudFlechaPorDefecto-10, y+alturaPorDefecto/6+5, x+lognitudFlechaPorDefecto+10, y+alturaPorDefecto/6+5);
        this.lineaSubHorizontal3 = new Line(x+lognitudFlechaPorDefecto-5, y+alturaPorDefecto/6+10, x+lognitudFlechaPorDefecto+5, y+alturaPorDefecto/6+10);
        
        this.getChildren().addAll(lineaHorizontalR,lineaAbajo,lineaSubHorizontal1,lineaSubHorizontal2,lineaSubHorizontal3);
    }
    
    
}
