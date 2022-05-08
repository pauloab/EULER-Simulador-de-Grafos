package tadCola;


class NodoCola<T>
{
	// Atributos accesibles desde otras rutinas del paquete
	T dato;
	NodoCola<T> siguiente;
		
	// Constructores

	NodoCola (T elemento, NodoCola<T> n)
	{ 
		dato = elemento; 
		siguiente = n; 
	}
}
