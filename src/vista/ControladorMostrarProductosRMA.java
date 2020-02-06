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

/**
 * Clase que inicia los parametros de la ventana que muestra los productos rma
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorMostrarProductosRMA implements Initializable {

    @FXML
    private TableView<Producto> tabla;
    @FXML
    private TableColumn<Producto, String> columnaNombreProducto;
    @FXML
    private TableColumn<Producto, String> columnaProblema;
    private String rmaSeleccionado;

    /**
     * 
     * Metodo que inicia los nombres de columna de la tabla a mostrar
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa las columnas de la tabla
        columnaNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaProblema.setCellValueFactory(new PropertyValueFactory<>("problema"));

    }

    /**
     * Metodo que inicia la tabla, y llama a un metodo que rellena esa tabla
     * @param rma 
     */
    void iniciarTabla(String rma) {
        rmaSeleccionado = rma;
        DAORma.rellenarTablaProductosRMA(tabla, rmaSeleccionado);
    }
}
