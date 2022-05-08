package Componentes;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.util.Duration;

/**
 * EULER 1.3 Simulador de grafos
 * Componente visual de un vértice (círculo con un texto como identificador).
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */

public class Vertice extends StackPane {
    private Text etiquetaNombre;
    private Circle circulo;
    private boolean estaEnfocado;
    private int nodoId;
    
    private static final double radioPorDefecto = 30;
    /**
     * Constructor que recibe los parámetros de un Vértice.
     * @param centroX Tipo de dato Double que recibe coordenada X
     * @param centroY Tipo de dato Double que recibe coordenada Y
     * @param radio Tipo de dato Double que recibe Radio del elemento
     * @param etiquetaNombre Texto del nodo
     */
    public Vertice(double centroX, double centroY, double radio, String etiquetaNombre) {

        // Inicia las instancias de los atributos de la clase
        super();
        circulo = new Circle(centroX, centroY, radio);
        this.etiquetaNombre = new Text(etiquetaNombre);
        this.estaEnfocado = false;
        
        // Establece las propiedades gráficas del círculo
        circulo.setFill(Color.WHITE);
        circulo.setStroke(Color.BLACK);

        // Esteblece las propiedades gráficas de la clase
        setCursor(Cursor.OPEN_HAND);
        getChildren().addAll(circulo,this.etiquetaNombre);
        setLayoutX(centroX-20);
        setLayoutY(centroY-20);

    }
    
    /**
     * Constructor de vértice.
     * Establece las propiedades gráficas del círculo.
     * @param centroX coordenada X
     * @param centroY coordenada Y
     * @param etiquetaNombre Texto del nodo
     */
    public Vertice(double centroX, double centroY, String etiquetaNombre) {

        // Inicia las instancias de los atributos de la clase
        super();
        circulo = new Circle(centroX, centroY, radioPorDefecto);
        this.etiquetaNombre = new Text(etiquetaNombre);
        this.estaEnfocado = false;
        
        // Establece las propiedades gráficas del círculo
        circulo.setFill(Color.WHITE);
        circulo.setStroke(Color.BLACK);

        // Esteblece las propiedades gráficas de cla clase
        setCursor(Cursor.OPEN_HAND);
        getChildren().addAll(circulo,this.etiquetaNombre);
        setLayoutX(centroX-20);
        setLayoutY(centroY-20);
        
    }
    
    /**
     * Método getter de IsFocused.
     * Determina si el vértice esta seleccionado por el usuario
     * @return Tipo de dato Boolean que determina si esta selecionado el vértice o no
     */
    public boolean getIsFocused() {
        return estaEnfocado;
    }

    /**
     * Método setter de EstaEnfocado.
     * Además de establecer la propiedad isFocussed, 
     * remarca gráficamente el borde del elemento para indicar 
     * que está seleccionado.
     * @param estaEnfocado 
     */
    public void setEstaEnfocado(boolean estaEnfocado) {
        if (estaEnfocado) {
            this.setBorder(new Border(new BorderStroke(Color.BLUE,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        }else{
            this.setBorder(new Border(new BorderStroke(Color.TRANSPARENT,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.EMPTY)));
        }   
        this.estaEnfocado = estaEnfocado;
    }

    /**
     * Método getter de Circulo.
     * @return Tipo de dato circulo
     */
    public Circle getCirculo() {
        return circulo;
    }

    /**
     * Método getter NodoId.
     * @return Tipo de dato Integer que es la id del vertice
     */
    public int getNodoId() {
        return nodoId;
    }

    /**
     * Método setter de NodoId.
     * Adicionalmnete actualiza el texto del vértice con le nuevo id
     * @param nodoId Tipo de dato Integer
     */
    public void setNodoId(int nodoId) {
        etiquetaNombre.setText(""+nodoId);
        this.nodoId = nodoId;
    }

    /**
     * Método para realizar la animación de los recorridos en profundidad y amplitud de los grafos.
     * @param interval Tipo de dato Double, el tiempo de retardo de la animación
     */
    public void startColorAnimation(double interval){
        // Se crea primero la animacion final para volver el color gri a blanco
        // con un intervalo de un segundo.
        final Animation finalAnimation = new Transition() {

            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.EASE_OUT);
            }
            
            @Override
            protected void interpolate(double frac) {
                // Cuando el intervalo termina, se cambia a blanco
                if (frac == 1) {
                    circulo.setFill(Color.WHITE);
                }
            }
        };
        
        // Se crea la animación que correrá primero, que colorea el nodo de gris
        // su duración está definida por el retardo que se pasa en el parámetro interval
        final Animation colorAnimation = new Transition() {

            {
                setCycleDuration(Duration.millis(interval));
                setInterpolator(Interpolator.EASE_OUT);
            }
            
            @Override
            protected void interpolate(double frac) {
                // cuando el intervalo de retardo termina, se colorea de gris
                // luego se llama a la animación final, que tendrá un retardo de un segundo
                if (frac == 1) {
                    circulo.setFill(Color.YELLOW);
                    finalAnimation.play();
                }
            }
        };
        colorAnimation.play();
    }
}
