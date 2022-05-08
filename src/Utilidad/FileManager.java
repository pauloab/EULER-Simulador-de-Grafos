/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Componentes.AristaDirigida;
import Componentes.AristaNoDirigida;
import Componentes.Nodo;
import Componentes.PanelPrincipal;
import Controlador.ControladorPrincipal;
import Modelo.Coordenada;
import Modelo.Grafo;
import Modelo.ArchivoGraph;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class FileManager {
    public static void guardarArchivoDeGrafo(PanelPrincipal panel, Grafo grafo , String ruta) throws IOException{
        Escritura<ArchivoGraph> escritura = new Escritura(ruta);
        ArchivoGraph file = new ArchivoGraph(ruta, grafo);
        Node obj;
        Nodo node;
        ObservableList<Node> nodes = panel.getChildren();
        for (int i = 0; i <nodes.size() ; i++) {
            obj = nodes.get(i);
            if (obj instanceof Nodo) {
                node = (Nodo)(obj);
                file.estabelcerCoordenadas(node.getNodoId(), node.getLayoutX() , node.getLayoutY());
            }
        }
        escritura.abrir();
        escritura.escribir(file);
        escritura.cerrar();
        
    }
    
    public static Grafo cargarArchivoGrafo(String ruta, ControladorPrincipal controlador) throws IOException, ClassNotFoundException{
        ArchivoGraph archivo;
        PanelPrincipal panel = controlador.getPanelPrincipal();
        Coordenada coordenada;
        Grafo grafo;
        Lectura<ArchivoGraph> lectura = new Lectura<>(ruta);
        
        lectura.abrir();
        archivo = lectura.leer();
        lectura.cerrar();
        grafo = archivo.obtenerGrafo();
        
        for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
            coordenada = archivo.obtenerCoordenada(i);
            Nodo nodo = new Nodo(coordenada.getX(), coordenada.getY(), "" + i);
            nodo.setNodoId(i);
            
            //Añade event listeners
            nodo.setOnMouseClicked(controlador::eventoNodoClick);
            nodo.setOnMouseDragged(controlador::mouseArrastrado);
            nodo.setOnMouseDragReleased(controlador::liberarArrastrado);
            nodo.setOnMousePressed(controlador::mousePresionado);
            nodo.setOnMouseReleased(controlador::clickLiberado);
            nodo.setOnDragDetected(event -> nodo.startFullDrag());

            panel.getChildren().add(nodo);
        }
        
        for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
            if (grafo.esNoDirigido()) {
                for (int j = i + 1; j < grafo.obtenerNumVertices(); j++) {
                    if (grafo.existeArista(i, j)) {
                        AristaNoDirigida a= new AristaNoDirigida(panel.getNodoPorId(i), panel.getNodoPorId(j)); 
                        
                        panel.getChildren().add(a);
                        a.toBack();
                    }
                }
            } else {
                for (int j = 0; j < grafo.obtenerNumVertices(); j++) {
                    if (grafo.existeArista(i, j)) {
                        AristaDirigida a= new AristaDirigida(panel.getNodoPorId(i), panel.getNodoPorId(j)); 
                        
                        panel.getChildren().add(a);
                        a.toBack();
                    }
                }
            }
        }
        return grafo;
    }

   
}
