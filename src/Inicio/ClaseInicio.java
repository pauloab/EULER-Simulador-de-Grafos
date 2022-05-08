
package Inicio;

import Controlador.ControladorPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * EULER 1.3 Simulador de grafos
 * Clase que carga la ventana de Inicio y arracan la aplicación.
 * @author Paulo Aguilar, Javier Matamoros, Freddy Lamar, Geovany Vega
 */
public class ClaseInicio extends Application {
    
    /**
     * Arranca el programa.
     * @param stage Se refiera a la escena, para mayor información remitase a la documentación de JavaFX 
     * @throws Exception Se lanza cuando ocurra un eeror al cargar los recursos del programa
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Main.fxml"));
        Image image = new Image(getClass().getResource("/Recursos/logoEuler.png").toExternalForm());
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ControladorPrincipal controller  = loader.getController();
        controller.establaceDdirigido(false);
        
        stage.getIcons().add(image);
        stage.setTitle("EULER - Simulador de Grafos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
