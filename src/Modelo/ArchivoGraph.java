/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import grafos.GrafoMA;

import java.io.Serializable;
import java.util.ArrayList;

public class ArchivoGraph implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    private ArrayList<Coordenada> cordenadas;
    private GrafoMA grafo;
    private String fileName;
    
    public ArchivoGraph(String fileName, GrafoMA grafo){
        this.fileName = fileName;
        this.grafo = grafo;
        this.cordenadas = new ArrayList<>();
    }
    
    public ArchivoGraph(){
        this.cordenadas = new ArrayList<>();
    }
    
    public void estabelcerCoordenadas(int index, double x, double y){
        cordenadas.add(index, new Coordenada(x, y));
    }
    
    public GrafoMA obtenerGrafo(){
        return grafo;
    }
    
    public Coordenada obtenerCoordenada(int index){
        return cordenadas.get(index);
    }

    public String getFileName() {
        return fileName;
    }
    
   
}

