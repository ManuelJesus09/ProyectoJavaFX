package vista;

import adicional.Producto;
import adicional.RMAException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Clase que controla el comportamiento de la ventana que crea un producto
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorCrearProducto {

    private Stage dialogo;
    private TableView<Producto> tabla;

    @FXML
    private TextField TFNombre;
    @FXML
    private TextField TFReferencia;
    @FXML
    private TextField TFNumeroFactura;
    @FXML
    private TextArea TAProblema;

    /**
     * Metodo que asigna valor a las variables con las que se va a interactuar
     * @param dialogo
     * @param tab 
     */
    void setProperties(Stage dialogo, TableView<Producto> tab) {

        this.dialogo = dialogo;
        this.tabla = tab;
    }

    /**
     * Metodo que vacia los textField de la vista
     */
    @FXML
    private void limpiarDatos() {

        TFNombre.setText("");
        TFReferencia.setText("");
        TFNumeroFactura.setText("");
        TAProblema.setText("");

    }

    /**
     * Metodo que añade un producto a la tabla y a la lista de productos,
     * siempre que no este repetido y que los datos del producto sean validos
     */
    @FXML
    private void addProducto() {
        try {

            //Creo el producto, se crea vacio y luego se editan los campos, ya que hay que inicializar los atributos de la clase Producto
            Producto temporal = new Producto();
            temporal.setNombre(TFNombre.getText());
            temporal.setEan(Integer.parseInt(TFReferencia.getText()));
            temporal.setNumeroFactura(Integer.parseInt(TFNumeroFactura.getText()));
            temporal.setProblema(TAProblema.getText().replaceAll("\n", " "));

            if (ControladorPrincipal.productos.contains(temporal)) {
                throw new RMAException("Ese producto ya lo has introducido.");
            } else {

                ControladorPrincipal.productos.add(temporal);
                tabla.getItems().add(temporal);

                //Muestra el mensaje
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Producto introducido");
                //Modificamos el estilo
                alert.getDialogPane().getStylesheets().add(
                        getClass().getResource("sky.css").toExternalForm());
                alert.showAndWait();

                dialogo.close();
                limpiarDatos();
            }

        } catch (RMAException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debes rellenar todos los campos.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();
        } catch (NumberFormatException a) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Los campos numero de referencia y factura deben ser numéricos.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();
        }
    }

}
