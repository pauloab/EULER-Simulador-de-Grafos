package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Escritura <T> {
	private FileOutputStream archivo;
	private ObjectOutputStream escritura;
	private String nombreArchvo;
	
	public Escritura(String nombreArchvo) {
		super();
		this.nombreArchvo = nombreArchvo;
	}
	
	public void abrir() throws IOException{
		archivo = new FileOutputStream(nombreArchvo,false);
		escritura = new ObjectOutputStream(archivo);
	}
	
	public void cerrar() throws IOException{
		archivo.close();
	}
	
	public void escribir(T objeto) throws IOException{
		if (escritura != null) {
			escritura.writeObject(objeto);
		}
	}
	
	
	
}
