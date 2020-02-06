package proyectointerfacesfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vista.ControladorPrincipal;

/**
 * Clase que inicia la aplicacion para gestionar las solicitudes RMA
 * @author Manuel Jesus Sanchez Vega
 */
public class Principal extends Application {
    
    private Stage primaryStage;
    private AnchorPane ventanaPrincipal;
    
    /**
     * Metodo que pone un titulo a la ventana principal e llama al metodo iniciarLayout
     * @param primaryStage , la ventana principal a la que se le cambia el nombre
     */
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Gestión de RMA");
        
        iniciarLayout();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Metodo que inicializa los componentes necesarios para lanzar la aplicacion
     * Inicia la ventana principal y le asigna un controlador
     */
    private void iniciarLayout() {
        
        try {
            //Carga el fxml principal
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class
                    .getResource("/vista/Principal.fxml"));
            ventanaPrincipal = (AnchorPane) loader.load();

            //Muestra la escena que contiene la ventana principal
            Scene escena = new Scene(ventanaPrincipal);
            primaryStage.setScene(escena);

            //Asigna el controlador
            ControladorPrincipal controlador = loader.getController();
            controlador.setPrimeraStage(primaryStage);
            
            //Modifica la vista para que no se pueda cambiar el tamaño y la muestra
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
