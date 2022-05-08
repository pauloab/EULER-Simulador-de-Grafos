package grafos;


import java.io.*;
import java.util.*;


public class GrafoMA implements Grafo {
	private String nombre;
	int maxNodos;             // Tamaño máximo de la tabla.
	int numVertices;          // Número de vértices.
	boolean matrizAdy [][];    // Matriz de adyacencias del grafo.
	boolean dirigido; 		  // Indica si es dirigido o no.
	   
  
	/**	
	 * CONSTRUCTORES:
	 * public Grafo() -- constructor por defecto, construye un grafo vacio
	 * public Grafo (int n) -- construye una matriz de nxn con los valores a false.
	 * public Grafo (BufferedReader buffer) --  Constructor con BufferedReader (a mí no se me ha ocurrido).
	 *  		Primero le pide al usuario el número de vértices del grafo (n).
	 *  		Luego crea una matriz de nxn.
	 *  		A continuación, le pide n veces al usuario que introduzca una línea con n tokens,
	 *  		que serán los valores de las celdas de la matriz. 
	 *  		StringTokenizer identifica los valores introducidos por el usuario.
**/
	
	
	public GrafoMA() {        
	 	   maxNodos = numVertices = 0;
	   }
	
	public GrafoMA(boolean d) {        
	 	   maxNodos = numVertices = 0;
	 	   dirigido = d;
	   }

	public GrafoMA (int n, boolean d) { //construye una matriz de nxn sin arcos
		dirigido = d;   
		maxNodos = n;
		numVertices = 0;
		matrizAdy = new boolean[n][n];
		for (int i=0; i<numVertices; i++)
	       {
	        for (int j=0; j<numVertices; j++) 
	       	 matrizAdy[i][j] = false;
	       }	   
	    }
	   
	public GrafoMA (BufferedReader buffer, boolean d)
	{

	try {
		dirigido = d;
		System.out.println("\nNúmero de nodos del grafo: ");
		String line = buffer.readLine();
	    StringTokenizer token = new StringTokenizer(line);
	    if (token.countTokens() != 1)
	       throw new Error("\nError: introduzca un único valor.");
	    int n = Integer.parseInt(token.nextToken());
	    System.out.print("\nEl número de vértices es " + n + "\n");
	    if ( n>0 ) matrizAdy = new boolean[n][n];
	    numVertices = maxNodos = n;
	    for (int i=0; i<n; i++)
	    {
	    	int l =i+1;
	    	System.out.print("\nIntroduzca los valores para la fila " + l +"\n");
	    	line = buffer.readLine();
	        token = new StringTokenizer(line);
	        if (token.countTokens() != n)
	           throw new Error("\nError de formato en la matriz de adyacencias.");
	        for (int j=0; j<n; j++) {
	        	int entry = Integer.parseInt(token.nextToken());
	            matrizAdy[i][j] = (entry!=0) ? true : false;
	             // si no es dirigido, intento hacer la matriz simétrica  
	              if (!dirigido) { matrizAdy[j][i] = matrizAdy[i][j]; } // Achtung!! 
	        }
	      }
	} catch (IOException x) { throw new Error("\nbad input stream!!"); }
	} // fin de GrafoMA (BufferedReader buffer)

 	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// MÉTODO COPIARGRAFO
	
	public GrafoMA CopiarGrafo (GrafoMA grafoOrigen) {
		GrafoMA g = new GrafoMA();
		int n = grafoOrigen.orden();
		if ( n>0 ) { 
		   g.matrizAdy = new boolean[n][n];
		   }
		g.maxNodos = g.numVertices = n;
		for (int i=0; i<n; i++)
		   for (int j=0; j<n; j++) 
			   {g.matrizAdy[i][j] = grafoOrigen.matrizAdy[i][j];}
		return g;
	   }
	
	
	// ------------------------------------
	
	// MÉTODOS PARA INSERTAR Y ELIMINAR VERTICES
	
	// ------------------------------------
	
	/**
	 * insertaVertice(int n): se simplifica el método de manera que no deja insertar vértices 
	 * si se supera el límite de nodos del grafo maxNodos
	 *
	 */
	
	public void insertaVertice(int n)
	{
	   if ( n > maxNodos - numVertices )
		   System.out.println("Error, se supera el número de nodos máximo del grafo");
	   else {
		   for (int i=0; i<numVertices+n; i++) {
			   for (int j=numVertices; j<numVertices+n; j++) //simplemento añado el valor false a las celdas 
	        	 matrizAdy[i][j] = matrizAdy[j][i] = false;
	        }
	   }
	   numVertices += n;
	}

  public void eliminarVertice(int v)
	{
	numVertices--;
	int i; 
	for (i=0; i<v; i++)  {
	   for (int j=v; j<numVertices; j++)  {
	     matrizAdy[i][j] = matrizAdy[i][j+1];
	     }
	   }
	   for (i=v; i<numVertices; i++)
	   {
	     int j; 
	     for (j=0; j<v; j++){
	    	 matrizAdy[i][j] = matrizAdy[i+1][j];
	    	 }
	     for (j=v; j<numVertices; j++) {
		   matrizAdy[i][j] = matrizAdy[i+1][j+1];
		   }
	     }
	}

	// ------------------------------------
	
	// MÉTODOS PARA INSERTAR Y ELIMINAR ARISTAS
	
	// ------------------------------------

	public void insertaArista(int i, int j)
	{
	   matrizAdy[i][j] = true;
	   if (!dirigido) {matrizAdy[j][i] = matrizAdy[i][j]; }
	}

	public void eliminaArista(int i, int j)
	{
	   matrizAdy[i][j] = false;
	   if (!dirigido) {matrizAdy[j][i] = false; }

	}

	// --------------------------------

	// Métodos de acceso

	// --------------------------------

	public boolean esVacio(GrafoMA g)
	{
	   return (numVertices == 0);
	}
	
	public boolean existeArista(int i, int j)
	{
	   return matrizAdy[i][j];
	}

	public int gradoIn(int i)   
	{							// 
	   int gIn = 0;
	   for (int j=0; j<numVertices; j++)  //recorro por filas manteniendo la posición de la columna fija en [i]  
	      if (matrizAdy[j][i]) gIn++;
	   return gIn;
	}

	public int gradoOut(int i)
	{
	   int gOut = 0;
	   for (int j= 0; j<numVertices; j++)
		   if (matrizAdy[i][j]) gOut++; //recorro por columnas, manteniendo la posición de la fila fija en [i]
	   return gOut;
	}

	public int incidencia (int i) {
		if (!dirigido)  
		return gradoIn(i);
		else return gradoIn(i)+gradoOut(i); 
	}
	
	public int orden() { 
		return numVertices; 
		}

	public int tamanno()       // Número de aristas (las aristas de un grafo no dirido se cuentan dos veces
	{
	   int tm = 0;
	   // boolean undirected = true;
	   
	   for (int i=0; i<numVertices; i++)
	     for (int j=0; j<numVertices; j++)
	        if (matrizAdy[i][j]) tm++;
	   if (dirigido) return tm;
	   else return tm/2;
	}

	public boolean esNoDirigido () {
		boolean dir = true;
		for (int i=0; i<numVertices; i++)
		     for (int j=0; j<numVertices; j++){
		    	 if (matrizAdy[i][j] != matrizAdy[j][i]) 
		    		 	dir = false;
		    	 }
		return dir;
	} 
	
	// --------------------------------
	// poner y obtener
	// --------------------------------
	
	   public void ponerMaxNodos(int n) {
			this.maxNodos = n;
		   }
	   
	   public int obtenerMaxNodos (){
		   return this.maxNodos;
	   }
	   
	   public void ponerDirigido(boolean d) {
			this.dirigido = d;
		   }
	   
	   public boolean obtenerDirigido (){
		   return this.dirigido;
	   }
	   
	   public int obtenerNumVertices (){
		   return this.numVertices;
	   }
	
	// --------------------------------
	// método que imprime la tabla de adyacencias
	// --------------------------------
	   
	   public void imprimirGrafo () {
		   System.out.println("La matriz contiene " + numVertices + " v�rtices: \n");
		   for (int i=0; i<numVertices; i++) {
			   for (int j=0; j<numVertices; j++) {
				   if (matrizAdy[i][j]) System.out.print("1 ");
				   else System.out.print("0 ");
			       }
			   System.out.println();
			   }
		   };
		   
}// end class  
	  




