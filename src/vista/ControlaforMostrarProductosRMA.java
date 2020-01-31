package vista;

import adicional.DAORma;
import adicional.Producto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Manuel
 */
public class ControlaforMostrarProductosRMA implements Initializable {

    @FXML
    private TableView<Producto> tabla;
    @FXML
    private TableColumn<Producto, String> columnaNombreProducto;
    @FXML
    private TableColumn<Producto, String> columnaProblema;
    private String rmaSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa las columnas de la tabla
        columnaNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaProblema.setCellValueFactory(new PropertyValueFactory<>("problema"));

    }

    void iniciarTabla(String rma) {
        rmaSeleccionado = rma;
        DAORma.rellenarTablaProductosRMA(tabla, rmaSeleccionado);
    }
}
