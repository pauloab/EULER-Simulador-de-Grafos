package Utilidad;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Lectura <T> {
	private FileInputStream archivo;
	private ObjectInputStream lectura;
	private String nombreArchivo;
	public Lectura(String nombreArchivo) {
		super();
		this.nombreArchivo = nombreArchivo;
	}
	
	public void abrir() throws IOException {
		archivo = new FileInputStream(nombreArchivo);
		lectura = new ObjectInputStream(archivo);
	}
	
	@SuppressWarnings("unchecked")
	public T leer () throws IOException, ClassNotFoundException {
		try {
			return (T) lectura.readObject();
		} catch (EOFException eof) {
			return null;
		}
	}
	
	public void cerrar() throws IOException {
		if (lectura != null) {
			lectura.close();
		}
	}
	
}
