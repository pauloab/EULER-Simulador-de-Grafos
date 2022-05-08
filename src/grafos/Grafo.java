package grafos;

public interface Grafo {
	
	public String getNombre();
	public void setNombre(String nombre);
	
	public int obtenerNumVertices ();
	
//  --------------------
//  ARISTAS
//  --------------------
	
	public void insertaArista(int i, int j);
	public void eliminaArista(int i, int j);
	public boolean existeArista(int i, int j);
	
//  --------------------
//  INSERTAR VERTICES
//  --------------------
	
	public void insertaVertice(int n);

	
//  ----------------------------------------
//  GRADO IN, OUT E INCIDENCIA
//  ----------------------------------------

	public int gradoIn(int v) ;
	public int gradoOut (int i);
	public int incidencia (int i);

	//  ----------------------------------------
//  ORDEN Y TAMAÑO DEL GRAFO
//  ----------------------------------------
		
	public int orden();
	public int tamanno();
	  
     
// ----------------------------------------
// COMPRUEBA SI ES ""NO"" DIRIGIDO
// ----------------------------------------
   
	public boolean esNoDirigido ();
	
	
// ----------------------------------------
// IMPRIME LAS LISTAS DE ADYACENCIAS
// ----------------------------------------
   
	public void imprimirGrafo ();
}
