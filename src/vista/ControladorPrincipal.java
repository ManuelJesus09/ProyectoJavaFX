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
 *
 * @author Manuel
 */
public class ControladorPrincipal {

    private Principal clasePrincipal;
    private Stage primerStage;
    protected static LinkedList<Producto> productos;

    @FXML
    private void initialize() {
        productos = new LinkedList<>();
    }

    public void setMain(Principal main, Stage primeraStage) {
        clasePrincipal = main;
        primerStage = primeraStage;
    }

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

            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            controlador.setDialog(dialogo, primerStage);

            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
