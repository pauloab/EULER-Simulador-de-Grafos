
package Componentes;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * EULER 1.3 Simulador de grafos
 * Componente visual que representa el nodo de la lista de adyacencia (rectangulo dividido con flecha a la derecha).
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class ListaNodo extends Group {
    private StackPane panelPila;
    private Rectangle rectanguloIz;
    private Rectangle rectanguloDe;
    private Text texto;
    private Flecha flecha;
    private double ancho;
    private double altura;
    private GNDNull tierraVirtual;
    
    /**
     * Constante que define el ancho del componente.
     */
    public static final double anchoPorDefecto = 100;

    /**
     * Constante que define el altura del componente.
     */
    public static final double alturaPorDefecto = 50;

    /**
     * Constante que define la longitud del componente.
     */
    public static final double lognitudFlechaPorDefecto = 75;
    
    /**
     * Constructor ListaNodo.
     * Crea un rectangulo donde se almacena el vertice del grafo, apilando todos los nodos de forma ascendiente.
     * Carga dimensiones en "x" y "y" por defecto para el rectuangulo.
     * @param texto Tipo de dato String que recibe la referencia del vértice
     * @param x Tipo de dato Double que recibe la posición por defecto del rectuangulo de la lista de adyacencia
     * @param y Tipo de dato Double que recibe la posición por defecto del rectuangulo de la lista de adyacencia
     */
    public ListaNodo(String texto, double x, double y){
        super();
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.ancho = anchoPorDefecto;
        this.altura = alturaPorDefecto;
        
        this.rectanguloIz = new Rectangle(ancho/2, altura);
        this.rectanguloIz.setFill(Color.GREY);
        this.rectanguloIz.setStroke(Color.BLACK);
        
        this.rectanguloDe = new Rectangle((ancho/2),0, ancho/2, altura);
        this.rectanguloDe.setFill(Color.TRANSPARENT);
        this.rectanguloDe.setStroke(Color.BLACK);
        
        this.texto = new Text(texto);
        this.panelPila = new StackPane(rectanguloIz,this.texto);
        
        double startX = rectanguloDe.getX()+(ancho/4);
        double startY = rectanguloDe.getY()+(altura/2);
        
        this.flecha = new Flecha(startX, startY, startX+lognitudFlechaPorDefecto, startY);
        this.flecha.setVisible(false);
        
        this.tierraVirtual = new GNDNull(startX, startY);
        
        this.getChildren().addAll(panelPila,rectanguloDe,flecha,tierraVirtual);
    }
    
    
    
    /**
     * Constructor ListaNodo.
     * Carga los nodos adyacentes al vértice.
     * @param texto Tipo de dato String que recibe la referencia del vértice
     * @param nodo  Objeto de tipo ListaNodo que recibe el nodo adyacente al vértice
     */
    public ListaNodo(String texto, ListaNodo nodo ){
        this(texto, nodo.getLayoutEndX() , nodo.getLayoutEndY());
        nodo.setArrowVisible(true);
        nodo.setVisibleGND(false);
    }

    /**
     * Método getter de Ancho.
     * @return El ancho del componente conteniendo la flecha
     */
    public double getAncho() {
        return ancho+(flecha.getEndX()-flecha.getStartX()-(ancho/4));
    }

    /**
     * Método getter de Altura.
     * @return Altura
     */
    public double getAltura() {
        return altura;
    }
    
    /**
     * Método getter de LayoutEndX.
     * @return La coordenada "x" respecto al Layout dónde termina el componente
     */
    public double getLayoutEndX(){
        return this.getLayoutX()+this.getAncho();
    }
    
    /**
     * Método getter de LayoutEndY.
     * @return La coordenada "Y" respecto al Layout de la parte supeior del componente
     */
    public double getLayoutEndY(){
        return this.getLayoutY();
    }
    
    /**
     * Método setter de ArrowVisible.
     * @param o Tipo de dato Booleano que recibe si la flecha sea visible cuando tenga nodos adyacentes al vértice
     */
    protected void setArrowVisible(boolean o){
        this.flecha.setVisible(o);
    }
    
     /**
     * Método setter de VisibleGND.
     * @param o Tipo de dato Booleano que recibe si la flecha de referencia a nulo sea visible
     */
    protected void setVisibleGND(boolean o){
        this.tierraVirtual.setVisible(o);
    }
}
