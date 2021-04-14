package tadCola;

public interface Cola<T> {
	boolean colaVacia ();
	void eliminarCola ();
	T primero () throws ColaVacia;
	void encolar (T x);
	T desencolar () throws ColaVacia;
	void quitarPrimero () throws ColaVacia;
	void mostrarEstadoCola ();
	void imprimirCola ();
    int numElemCola ();
    void invertirCola () throws ColaVacia;
}
