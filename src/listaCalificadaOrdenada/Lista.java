package listaCalificadaOrdenada;

public class Lista<T extends Comparable<T>> {
	NodoLista<T> inicio;
	String nombre;

	public NodoLista<T> getInicio() {
		return inicio;
	}

	public String getNombre() {
		return nombre;
	}

	public Lista(String nombreLista) {
		inicio = null;
		nombre = nombreLista;
	}
	
	private T leer(NodoLista<T> nodoLista, int n, int cont) {
		T resul = null;
		if(nodoLista != null)
			if(cont == n)
				resul = nodoLista.clave;
			else
				resul = leer(nodoLista.sig, n, cont+1);			
		return resul;
	}
	
	public T leer(int n) {
		return leer(inicio, n, 0);
	}
	
	private boolean busqueda(NodoLista<T> nodoLista, T x) {
		boolean resul = false;
		if (nodoLista != null)
			if (nodoLista.clave.compareTo(x) == 0)
				resul = true;
			else if (nodoLista.clave.compareTo(x) < 0)
				resul = busqueda(nodoLista.sig, x);
		return resul;
	}

	public boolean busqueda(T x) {
		return busqueda(inicio, x);
	}

	private NodoLista<T> insertar(NodoLista<T> nodoLista, T dato) {
		NodoLista<T> resul = nodoLista;
		if (nodoLista != null)
			if (nodoLista.clave.compareTo(dato) < 0)
				nodoLista.sig = insertar(nodoLista.sig, dato);
			else if (nodoLista.clave.compareTo(dato) > 0)
				resul = new NodoLista<T>(dato, nodoLista);
			else
				System.out.println("la clave ya existe en la lista");
		else
			resul = new NodoLista<T>(dato, nodoLista);
		return resul;
	}

	public void insertar(T dato) {
		inicio = insertar(inicio, dato);
	}

	private NodoLista<T> eliminar(NodoLista<T> nodoLista, T dato) {
		NodoLista<T> resul = nodoLista;
		if (nodoLista != null)
			if (nodoLista.clave.compareTo(dato) < 0)
				nodoLista.sig = eliminar(nodoLista.sig, dato);
			else if (nodoLista.clave.compareTo(dato) > 0)
				System.out.println("la clave no existe");
			else
				resul = nodoLista.sig;
		else
			System.out.println("la clave no existe");
		return resul;
	}

	public void eliminar(T dato) {
		inicio = eliminar(inicio, dato);
	}

	public void imprimirLista() {
		NodoLista<T> aux;

		aux = inicio;
		System.out.print(nombre + ": \n");
		while (aux != null) {
			System.out.print(aux.clave.toString() + "\n");
			aux = aux.sig;
		}
		System.out.println("FIN");
	}
}
