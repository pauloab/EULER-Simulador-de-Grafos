package Utilidad;

import tadCola.TadCola;
import tadCola.Cola;
import tadCola.ColaVacia;
import grafos.Grafo;

/**
 * EULER 1.3 Simulador de grafos
 *
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class Recorridos {

    /**
     *
     * @param g
     * @param v
     * @param visitados
     * @return
     */
    public static String recorrerProfundidad(Grafo g, int v, boolean[] visitados) {
        //se marca el vértice v como visitado
        visitados[v] = true;
        //el tratamiento del vértice consiste únicamente en imprimirlo en pantalla
        String acumulado = "";
        acumulado += v + " ";
        //se examinan los vértices adyacentes a v para continuar el recorrido
        for (int i = 0; i < g.obtenerNumVertices(); i++) {
            if ((v != i) && (!visitados[i]) && (g.existeArista(v, i))) {
                acumulado += recorrerProfundidad(g, i, visitados);
            }
        }
        return acumulado;
    }

    // procedimiento no recursivo

    /**
     *
     * @param g
     * @param nodoInicio
     * @return
     */
    public static String profundidad(Grafo g, int nodoInicio) {
        boolean visitados[] = new boolean[g.obtenerNumVertices()];
        //inicializo el vector: pongo todos los campos a false
        String acumulado = "";
        for (int i = 0; i < g.obtenerNumVertices(); i++) {
            visitados[i] = false;
        }

        acumulado += recorrerProfundidad(g, nodoInicio, visitados);
        //Se relanza el recorrido en cada vértice no visitado hasta que estánn todos visitados
        for (int i = 0; i < g.obtenerNumVertices(); i++) {
            if (!visitados[i]) {
                acumulado += recorrerProfundidad(g, i, visitados);
            }
        }
        return acumulado;
    }

    /**
     *
     * RECORRIDO EN AMPLITUD
     *
     * @param g
     * @param nodoInicio
     * @return 
     */

    public static String amplitud(Grafo g, int nodoInicio) {
        //se requiere una cola/lista donde guardar
        TadCola cola = new TadCola();
        boolean visitados[] = new boolean[g.obtenerNumVertices()];
        
        String recorrido = "";
        //Se inicializa el array visitados[] a false
        for (int i = 0; i < g.obtenerNumVertices(); i++) {
            visitados[i] = false;
        }
        
        cola.encolar(nodoInicio);
        visitados[nodoInicio] = true;
        recorrido += amplitud(cola, visitados, g);
        
        //El recorrido en amplitud se inicia en cada vértice no visitado 
        for (int i = 0; i < g.obtenerNumVertices(); i++) {
            //se pone en la cola el vértide de partida y se marca como visitado
            if (!visitados[i]) {
                cola.encolar(i);
                visitados[i] = true;
                recorrido += amplitud(cola, visitados, g);
                
            }
        }
        return recorrido;
    }

    private static String amplitud(Cola<Integer> cola, boolean[] visitados, Grafo g) {
        String recorrido = "";
        int v; //vértice actual
        while (!cola.colaVacia()) {
            // desencolo, trato el vértice (en este caso, solo es mostrar pantalla)
            try {
                v = cola.desencolar();
                recorrido += v + " ";
                // y encolo los nodos adyacentes a v.
                for (int j = 0; j < g.obtenerNumVertices(); j++) {
                    if ((v != j) && ((g.existeArista(v, j)) && (!visitados[j]))) {
                        cola.encolar(j);
                        visitados[j] = true;
                    }
                }
            } catch (ColaVacia e) {
                
            }
        }
        return recorrido;
    }

} // fin de la clase

