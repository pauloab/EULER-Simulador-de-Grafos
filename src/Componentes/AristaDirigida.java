package  Componentes;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 * EULER 1.3 Simulador de grafos
 * Componente visial de arista dirigida (Flecha curva con saeta)
 * Clase gráfica encargada de el trazado de aristas entre vértices.
 * Utiliza 2 lineas extra para la saeta.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */

public class AristaDirigida extends Arista{
    
    private Line flecha1,flecha2;
    private QuadCurve curva;
    
    private static final double flechaLongitud = 20;
    private static final double flechaAncho = 7;
    private static final double curvaControl = 20;
    private static final double separacion = 5;
    
    /**
     * Constructor de AristaDirigida.
     * Establece propiedades básicas como colores y grosor de la línea(arista).
     * Crea las lineas de la saeta.
     * Actualiza la posición de la arista cuando se nueva un nodo.
     * Asocia las propiedades del nodo con el actualizador de posicion de arista.
     * Actualizará la posición de la saeta (dos líneas extra) cuando se mueve la linea principal.
     * Asocia las propiedades de la linea principal con el actualizador de posicion de la saeta.
     * Agrega las tres lineas al elemento gráfico .
     * @param nodoInicio Objeto tipo Vertice del inicio del trazado de la arista
     * @param nodoFinal  Objeto tipo Vertice del final del trazado de la arista
     */
    public AristaDirigida(Vertice nodoInicio, Vertice nodoFinal){
        super(nodoInicio,nodoFinal,true);
        // Establece propiedades básicas como colores y grosor de la línea
        this.curva = new QuadCurve();
        curva.setFill(Color.TRANSPARENT);
        curva.setStroke(Color.BLACK);
        curva.setStrokeWidth(3);
        
        // Crea las líneas de la saeta
        this.flecha1 = new Line();
        this.flecha2 = new Line();
        
        // Listener que actualizará la posición de la arista cuando se nueva un nodo
        InvalidationListener actualizadorDeLinea = event-> {
            double startX,startY,endX,endY,
                    factorNodes,ox,oy,dx,dy,radio,
                    factorDireccional,factorOrtogonal, factorSeparacion;
            
            //Toma el elemento gráfico Circle para recoger su radio (igual en todos los nodos)
            Circle startCircle = nodoInicio.getCirculo();
            radio = startCircle.getRadius();
            
            if (nodoInicio.getNodoId() == nodoFinal.getNodoId()) {
                /*
                Facilita la generación de una arista bucle, recalclando unarco más abierto
                */
                //se establecen puntos de inicio y final
                ox = nodoInicio.getLayoutX() + 2*radio;
                oy = nodoInicio.getLayoutY() + 2*radio;
                
                startX = ox;
                startY = nodoInicio.getLayoutY() + radio;
                endX = nodoInicio.getLayoutX() + (radio/2);
                endY = oy;
                
                curva.setControlX(ox);
                curva.setControlY(oy+40);
            } else {
                /*
                Corrige la posición de inicio y final, tomando en cuenta que
                layoutX y layoutY son coordenadas de la esquina del componente, por lo que
                radio da exácamente al borde de la figura, además se corrije la
                separación entre las aristas
                */
                endX = nodoFinal.getLayoutX() + radio + separacion;
                endY = nodoFinal.getLayoutY() + radio + separacion;
                startX = nodoInicio.getLayoutX() + radio + separacion;
                startY = nodoInicio.getLayoutY() + radio + separacion;
                
                // Factor ortogonal entre los nodos que permite corregit el ángulo de la arista
                factorNodes = radio / Math.hypot(startX - endX, startY - endY);
                
                // Con el factor, calcula las coordenadas nuevas
                ox = (startX - endX) * factorNodes;
                oy = (startY - endY) * factorNodes;
                
                // Recorta el extremo final de la linea para tocar el borde del nodo
                endX += ox;
                endY += oy;
                
                // Recorta el extremo inicial de la linea
                startX -= ox;
                startY -= oy;
                
                // Establece las coordenadas de inicio y final de la linea
                setStartX(startX);
                setStartY(startY);
                setEndX(endX);
                setEndY(endY);
                
                // Calcula el factor ortogonal que estabelcerá la amplitud curvatura
                factorOrtogonal = curvaControl / Math.hypot(endX - startX, endY - startY);
                // Calcula el factor direccional que establecerá la posición de la curvatura (mitad de la línea)
                factorDireccional = (Math.hypot(endX - startX, endY - startY) / 2) / (Math.hypot(endX - startX, endY - startY));
                
                // Coordenadas direccionales (mitad de logitud)
                dx = (startX - endX) * factorDireccional;
                dy = (startY - endY) * factorDireccional;
                
                // Coordenadas ortogonales (amplitud de la curva)
                ox = (startX - endX) * factorOrtogonal;
                oy = (startY - endY) * factorOrtogonal;
                
                curva.setControlX(endX + dx - oy);
                curva.setControlY(endY + dy + ox);
                
                // Calcula coordenadas ortogonales basado en la factor de distancia
                factorSeparacion = separacion / Math.hypot(endX - startX, endY - startY);
                
                // Coordenadas ortogonales (distancia entre líneas)
                ox = (startX - endX) * factorSeparacion;
                oy = (startY - endY) * factorSeparacion;
                
                startX -= oy;
                startY += ox;
                endX -= oy;
                endY += ox;
                
            }
            //Establece el inicio y final de la curva
            setStartX(startX);
            setStartY(startY);
            setEndX(endX);
            setEndY(endY);
        };
        
       //Asocia las propiedades del nodo con el actualizador de posicion de arista
        nodoInicio.layoutXProperty().addListener(actualizadorDeLinea);
        nodoInicio.layoutYProperty().addListener(actualizadorDeLinea);
        nodoFinal.layoutXProperty().addListener(actualizadorDeLinea);
        nodoFinal.layoutYProperty().addListener(actualizadorDeLinea);
        actualizadorDeLinea.invalidated(null);
        
        // Actualizará la posición de la saeta (dos líneas extra) cuando se mueve la linea principal
        InvalidationListener updater = o -> {
            double ex,ey,sx,sy,factor,factorO,dx,dy,ox,oy;
            
            ex = getEndX();
            ey = getEndY();
            sx = getControlX();
            sy = getControlY();
            
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
                
                // Calcula el facotr de logitud de la saeta
                factor = flechaLongitud / Math.hypot(sx-ex, sy-ey);
                // Calcula el factor de amplitud de la saeta
                factorO = flechaAncho / Math.hypot(sx-ex, sy-ey);
                
                // parte en direccion a la linea principal
                dx = (sx - ex) * factor;
                dy = (sy - ey) * factor;
                
                // parte ortogonal a la linea principal
                ox = (sx - ex) * factorO;
                oy = (sy - ey) * factorO;
                
                flecha1.setStartX(ex + dx - oy);
                flecha1.setStartY(ey + dy + ox - 2);
                flecha2.setStartX(ex + dx + oy);
                flecha2.setStartY(ey + dy - ox + 2);
            }
        };
        
        // Asocia las propiedades de la linea principal con el actualizador de posicion de la saeta
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
        
        //Agrega las tres lineas al elemento gráfico
        this.getChildren().addAll(curva,flecha1,flecha2);
    }
    
    
    // start/end propiedades

    /**
     * Método setter de StartX.
     * @param value Tipo de dato Double que recibe el valor de posición
     */

    public final void setStartX(double value) {
        curva.setStartX(value);
    }

    /**
     * Método getter de StartX.
     * @return Objeto de QuadCurve de StartX
     */
    public final double getStartX() {
        return curva.getStartX();
    }

    /**
     * Método getter de startXProperty.
     * @return Objeto de QuadCurve de startXProperty
     */
    public final DoubleProperty startXProperty() {
        return curva.startXProperty();
    }

    /**
     * Método setter de StartY.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setStartY(double value) {
        curva.setStartY(value);
    }

    /**
     * Método getter de StartY.
     * @return Objeto de QuadCurve de StartY
     */
    public final double getStartY() {
        return curva.getStartY();
    }

    /**
     * Método getter de startYProperty.
     * @return Objeto de QuadCurve de startYProperty
     */
    public final DoubleProperty startYProperty() {
        return curva.startYProperty();
    }

    /**
     * Método setter de EndX.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setEndX(double value) {
        curva.setEndX(value);
    }

    /**
     * Método getter de EndX.
     * @return Objeto de QuadCurve de EndX
     */
    public final double getEndX() {
        return curva.getEndX();
    }

    /**
     * Método getter de endXProperty.
     * @return Objeto de QuadCurve de endXProperty
     */
    public final DoubleProperty endXProperty() {
        return curva.endXProperty();
    }

    /**
     * Método setter de EndY.
     * @param value Tipo de dato Double que recibe el valor de posición
     */
    public final void setEndY(double value) {
        curva.setEndY(value);
    }

    /**
     * Método getter de EndY.
     * @return Objeto de QuadCurve de EndY
     */
    public final double getEndY() {
        return curva.getEndY();
    }
    
    /**
     * Método getter de ControlX.
     * @return Objeto de QuadCurve de ControlX
     */
    public final double getControlX(){
        return curva.getControlX();
    }
    
    /**
     * Método getter de ControlY.
     * @return Objeto de QuadCurve de ControlY
     */
    public final double getControlY(){
        return curva.getControlY();
    }
   
    /**
     * Método getter de endYProperty.
     * @return Objeto de QuadCurve de endYProperty
     */
    public final DoubleProperty endYProperty() {
        return curva.endYProperty();
    }
    
}