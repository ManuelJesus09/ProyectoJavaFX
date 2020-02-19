package vista;

import adicional.DAORma;
import adicional.Producto;
import adicional.AbrirReporte;
import adicional.RMA;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import proyectointerfacesfx.Principal;

/**
 * Clase que controla el comportamiento de la pantalla que muestra las
 * solicitudes enviadas
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorMostrarRma implements Initializable {

    @FXML
    private TableView<RMA> tabla;
    @FXML
    private TableColumn<RMA, Integer> columnaReferencia;
    @FXML
    private TableColumn<RMA, String> columnaFecha;
    private Stage primer, dialogo;

    /**
     * Metodo que inicializa las columnas de la tabla a mostrar y le añade a la
     * tabla un controlador para captar el doble clic
     *
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
     * Metodo que asigna la primeraStage al controlador, para poder interactuar
     * con ella en otros metodos
     *
     * @param primerStage vista con la que se va a interactuar
     */
    void setDialogs(Stage primerStage, Stage dialogo) {
        primer = primerStage;
        this.dialogo = dialogo;

    }

    /**
     * Metodo que cierra la ventana actual
     */
    @FXML
    private void volver() {
        dialogo.close();
    }

    /**
     * Llama al metodo abrir reporte de la clase auxiliar
     */
    @FXML
    private void abrirReporteRmaCount() throws ClassNotFoundException, SQLException, JRException, IOException {
        AbrirReporte.abrirRmaCount();
    }

    /**
     * Llama al metodo abrir reporte de la clase auxiliar
     */
    @FXML
    private void abrirReporteRma() throws ClassNotFoundException, SQLException, JRException, IOException {
        AbrirReporte.abrirRma();
    }

    /**
     * Metodo que abre una nueva ventana que muestra los detalles de una
     * solicitud en concreto,
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
        } catch(NullPointerException a){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No hay selección");
            alert.setHeaderText("Ninguna solicitud seleccionada");
            alert.setContentText("Por favor seleccione una solicitud de la tabla.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();
        }
    }

}
