package tadCola;

public class TadCola<T> implements Cola<T> {
	private String nombre;
	private NodoCola<T> principio;
	private NodoCola<T> fin;
	
	public TadCola () {
		principio = null;
		fin = null;
	}
	
	public TadCola(String nombre) {
		this();
		this.nombre = nombre;
	}

	public boolean colaVacia () {
		return principio==null;	
	}
	public void eliminarCola () {
		principio = null;
		fin = null;
	}
	public T primero () throws ColaVacia {
    	if (colaVacia ())
    		throw new ColaVacia ("La cola está vacía");
    	return principio.dato;
	}
	public void encolar (T x) {
        NodoCola<T> aux;
        aux = new NodoCola<>(x,null);
        if (principio == null) {
        	principio = aux;
        	fin = aux;
        }
        else {
        	fin.siguiente = aux;
        	fin = aux;
        }
	}
	public T desencolar () throws ColaVacia {
        T resultado;
        if (colaVacia ()) {  
          throw new ColaVacia ("Desencolar: La cola está vacía");
        }
        resultado = principio.dato;
        principio = principio.siguiente;
        if (principio == null)
        	fin = null;
        return resultado;
		
	}
	
	public void quitarPrimero () throws ColaVacia {
	    if (colaVacia ()) {  
	    	throw new ColaVacia ("Quitar primero: La cola está vacía");
	    }
	    principio = principio.siguiente;
	    if (principio == null)
	    	fin = null;
	}
	
	public void mostrarEstadoCola () {
		System.out.println("Estado de la cola " + this.nombre +":");
		System.out.println("Número de elementos: "+this.numElemCola());
		if(!colaVacia()) {
	        System.out.println("Primer elemento - Clave: "+principio.dato);
	        System.out.println("Último elemento - Clave: "+fin.dato);
		}
    }
	
	public void imprimirCola () {
        NodoCola<T> aux;
        System.out.print ("Estado de la cola " + this.nombre + ": ");
        aux = principio;
        while (aux != null) {
          System.out.print (aux.dato + " ");
          aux = aux.siguiente;
        }
        System.out.println ();
      }
	
	public int numElemCola () {
        NodoCola<T> aux;	
        int resul;
        
          aux = principio;
          resul = 0;
          while (aux != null) {
        	++resul;
        	aux = aux.siguiente;
          }
          return resul;
        }
	
	public void invertirCola () throws ColaVacia {
		T elem;
		if (!colaVacia()) {
			elem = desencolar();	
			invertirCola();
			encolar(elem);
		}
	}
}
