package vista;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import adicional.DAORma;
import adicional.Producto;
import java.util.LinkedList;
import javafx.scene.layout.VBox;
import proyectointerfacesfx.Principal;

/**
 * Clase que maneja el comportamiento de la vista principal
 * Contiene la variable productos, una lista donde trabaja con 
 * los productos en memoria, para despues insertarlos en la bd
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorPrincipal {

    private Stage primerStage;
    protected static LinkedList<Producto> productos;

    public void setPrimeraStage(Stage primeraStage) {
        primerStage = primeraStage;
    }

    /**
     * Metodo que carga una ventana nueva para crear una solicitud RMA
     */
    @FXML
    private void crearSolicitud() {
        try {
            //Carga la vista de crear solicitud rma
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("/vista/CrearSolicitud.fxml"));
            BorderPane vistaCrear = (BorderPane) loader.load();

            //Crea un dialogo para mostrar la vista
            Stage dialogo = new Stage();
            dialogo.setTitle("Solicitud RMA");
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(primerStage);
            Scene escena = new Scene(vistaCrear);
            dialogo.setScene(escena);

            //Annadir controlador y datos
            ControladorCrearSolicitud controlador = loader.getController();
            controlador.setDialog(dialogo, primerStage);

            //Carga el numero de Referencia
            int numReferencia = DAORma.crearReferencia();

            //Modifica el dialogo para que no se pueda cambiar el tamaño y lo muestra
            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que abre una ventana para mostrar las solicitudes enviadas
     */
    @FXML
    private void mostrarSolicitudes() {
        try {
            //Carga la vista 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("/vista/MostrarRma.fxml"));
            VBox vistaMostrar = (VBox) loader.load();

            //Crea un dialogo para mostrar la vista
            Stage dialogo = new Stage();
            dialogo.setTitle("Solicitudes enviadas");
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(primerStage);
            Scene escena = new Scene(vistaMostrar);
            dialogo.setScene(escena);

            //Annadir controlador y datos
            ControladorMostrarRma controlador = loader.getController();
            controlador.setDialog( primerStage);

            //Modifica el dialogo para que no se pueda cambiar el tamaño y lo muestra
            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
