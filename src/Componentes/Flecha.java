package  Componentes;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * EULER 1.3 Simulador de grafos
 * Clase gráfica encargada de el trazado de aristas entre vértices.
 * Utiliza 2 lineas extra para la saeta.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */


public class Flecha extends Group{
    
    private Line linea, flecha1,flecha2;
    private Circle circulo;
    
    private static final double longitudFlecha = 20;
    private static final double anchoFlecha = 7;
    
    /**
     * Constructor de Flecha
     * Actualizará la posición de la saeta (dos líneas extra) cuando se mueve la linea principal
     * Asocia las propiedades de la linea principal con el actualizador de posicion de la saeta
     * Agrega las tres lineas al elemento gráfico
     * @param inicioX Tipo de dato Double que recibe el la posición de origen de la fecha en "x"
     * @param inicioY Tipo de dato Double que recibe el la posición de origen de la fecha en "y"
     * @param finX    Tipo de dato Double que recibe el la posición de fin de la fecha en "x"
     * @param finY    Tipo de dato Double que recibe el la posición de fin de la fecha en "y"
     */
    public Flecha(double inicioX, double inicioY, double finX, double finY  ){
        super();
        this.linea = new Line(inicioX,inicioY,finX,finY);
        this.flecha1 = new Line();
        this.flecha2 = new Line();
        this.circulo = new Circle();
        this.circulo.setRadius(5);
        
        // Actualizará la posición de la saeta (dos líneas extra) cuando se mueve la linea principal
        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            flecha1.setEndX(ex);
            flecha1.setEndY(ey);
            flecha2.setEndX(ex);
            flecha2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // Flechas de longitud 0
                flecha1.setStartX(ex);
                flecha1.setStartY(ey);
                flecha2.setStartX(ex);
                flecha2.setStartY(ey);
            } else {
                double factor = longitudFlecha / Math.hypot(sx-ex, sy-ey);
                double factorO = anchoFlecha / Math.hypot(sx-ex, sy-ey);

                // parte en direccion a la linea principal
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // parte ortogonal a la linea principal
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                flecha1.setStartX(ex + dx - oy);
                flecha1.setStartY(ey + dy + ox);
                flecha2.setStartX(ex + dx + oy);
                flecha2.setStartY(ey + dy - ox);
                
                this.circulo.setCenterX(inicioX);
                this.circulo.setCenterY(inicioY);
            }
        };
        
        // Asocia las propiedades de la linea principal con el actualizador de posicion de la saeta
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
        
        // Agrega las tres lineas al elemento gráfico 
        this.getChildren().addAll(linea,flecha1,flecha2,circulo);
    }
    
    
    // start/end properties

    /**
     * Método setter de StarX.
     * @param value Tipo de dato Double que recibe el valor de posición
     */

    public final void setStartX(double value) {
        linea.setStartX(value);
    }

    /**
     * Método getter de StartX.
     * @return Objeto de tipo Line de StartX
     */
    public final double getStartX() {
        return linea.getStartX();
    }

    /**
     * Método getter de startXProperty.
     * @return Objeto de tipo Line de startXProperty
     */
    public final DoubleProperty startXProperty() {
        return linea.startXProperty();
    }

    /**
     * Método setter de StartY.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setStartY(double value) {
        linea.setStartY(value);
    }

    /**
     * Método getter de StartY.
     * @return Objeto de tipo Line de StartY
     */
    public final double getStartY() {
        return linea.getStartY();
    }

    /**
     * Método getter de startYProperty.
     * @return Objeto de tipo Line de startYProperty
     */
    public final DoubleProperty startYProperty() {
        return linea.startYProperty();
    }

    /**
     * Método setter de EndX.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setEndX(double value) {
        linea.setEndX(value);
    }

    /**
     * Método getter de EndX.
     * @return Objeto de tipo Line de EndX
     */
    public final double getEndX() {
        return linea.getEndX();
    }

    /**
     * Método getter de endXProperty.
     * @return Objeto de tipo Line de endXProperty
     */
    public final DoubleProperty endXProperty() {
        return linea.endXProperty();
    }

    /**
     * Método setter de EndY.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setEndY(double value) {
        linea.setEndY(value);
    }

    /**
     * Método getter de EndY.
     * @return Objeto de tipo Line de EndY
     */
    public final double getEndY() {
        return linea.getEndY();
    }

    /**
     * Método getter de  endYProperty.
     * @return Objeto de tipo Line de endYProperty
     */
    public final DoubleProperty endYProperty() {
        return linea.endYProperty();
    }
    
}