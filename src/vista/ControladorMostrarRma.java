package vista;

import adicional.DAORma;
import adicional.Producto;
import adicional.RMA;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectointerfacesfx.Principal;

/**
 * Clase que controla el comportamiento de la pantalla que muestra las solicitudes enviadas
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorMostrarRma implements Initializable {

    @FXML
    private TableView<RMA> tabla;
    @FXML
    private TableColumn<RMA, Integer> columnaReferencia;
    @FXML
    private TableColumn<RMA, String> columnaFecha;
    private Stage primer;

    /**
     * Metodo que inicializa las columnas de la tabla a mostrar y le
     * añade a la tabla un controlador para captar el doble clic
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa las columnas de la tabla
        columnaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        DAORma.rellenarTablaRma(tabla);
        tabla.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        verDetalles();
                    }
                }
            }
        });

    }

    /**
     * Metodo que asigna la primeraStage al controlador, para
     * poder interactuar con ella en otros metodos
     * @param primerStage vista con la que se va a interactuar
     */
    void setDialog(Stage primerStage) {
        primer = primerStage;
    }

    /**
     * Metodo que abre una nueva ventana que muestra los detalles de una solicitud en concreto,
     */
    public void verDetalles() {
        try {
            //Carga la vista 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("/vista/MostrarProductosRMA.fxml"));
            TableView<Producto> vistaMostrarDatos = (TableView<Producto>) loader.load();

            //Crea un dialogo para mostrar la vista
            Stage dialogo = new Stage();
            dialogo.setTitle("Datos solicitud");
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(primer);
            Scene escena = new Scene(vistaMostrarDatos);
            dialogo.setScene(escena);

            //Annadir controlador y datos
            ControladorMostrarProductosRMA controlador2 = loader.getController();
            String seleccionRma = tabla.getSelectionModel().getSelectedItem().getReferencia();
            controlador2.iniciarTabla(seleccionRma);

            //Modifica el dialogo para que no se pueda cambiar el tamaño y lo muestra
            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
