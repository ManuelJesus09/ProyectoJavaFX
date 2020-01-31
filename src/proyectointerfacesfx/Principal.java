package proyectointerfacesfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vista.ControladorPrincipal;

/**
 *
 * @author Manuel
 */
public class Principal extends Application {
    
    private Stage primaryStage;
    private AnchorPane ventanaPrincipal;
    
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
            controlador.setMain(this, primaryStage);
            
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
