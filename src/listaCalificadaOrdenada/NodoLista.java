package listaCalificadaOrdenada;

public class NodoLista<T> {
	T clave;
	NodoLista<T> sig;
	
	NodoLista(T x, NodoLista<T> n) {
		clave = x;
		sig = n;
	}

	public T getClave() {
		return clave;
	}

	public NodoLista<T> getSig() {
		return sig;
	}	
}

