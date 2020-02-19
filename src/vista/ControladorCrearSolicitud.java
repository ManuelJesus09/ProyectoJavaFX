package vista;

import adicional.ConexionBD;
import adicional.DAORma;
import adicional.Producto;
import adicional.RMAException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectointerfacesfx.Principal;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Clase que controla el comportamiento de la ventana para crear una solicitud
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class ControladorCrearSolicitud {

    private Stage dialogo, primerStage;
    private AnchorPane panel;

    @FXML
    private TextField TFReferencia;
    @FXML
    private TextField TFFecha;
    @FXML
    private TableView<Producto> tabla;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, String> columnaProblema;
    @FXML
    private TableColumn<Producto, Integer> columnaEAN;
    @FXML
    private TableColumn<Producto, Integer> columnaNumeroFactura;

    /**
     * Metodo que inicializa las columnas de la tabla y rellena los campos fecha
     * y referencia
     *
     * @throws RMAException lanza excepcion si los datos no estan correctamente
     */
    @FXML
    private void initialize() throws RMAException {
        // Inicializa las columnas, de forma que se pueda añadir objetos
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaProblema.setCellValueFactory(new PropertyValueFactory<>("problema"));
        columnaEAN.setCellValueFactory(new PropertyValueFactory<>("ean"));
        columnaNumeroFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));

        //Inicializa los campos
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        TFFecha.setText(format.format(new Date()));

        //Asigna una referencia unica
        TFReferencia.setText(String.valueOf(DAORma.crearReferencia()));
    }

    /**
     * Metodo que abre una nueva ventana para poder crear un producto y añadirlo
     * a la solicitud
     */
    @FXML
    public void crearProducto() {

        try {
            //Carga la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("/vista/CrearProducto.fxml"));
            panel = (AnchorPane) loader.load();

            //Crea el dialog con la vista
            Stage dialogo = new Stage();
            dialogo.setTitle("Crear Producto");
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(primerStage);
            Scene escena = new Scene(panel);
            dialogo.setScene(escena);

            //Annadir controlador y datos
            ControladorCrearProducto controlador = loader.getController();
            controlador.setProperties(dialogo, tabla);

            //Modifica el dialogo para que no se pueda modificar el tamaño, y lo muestra
            dialogo.setResizable(false);
            dialogo.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que cierra la ventana actual
     */
    @FXML
    private void volver() {
        dialogo.close();
    }

    /**
     * Metodo que elimina el producto de la tabla que actualmente esta
     * seleccionado
     */
    @FXML
    private void eliminar() {

        //Recoge el numero de la fila seleccionada
        int seleccion = tabla.getSelectionModel().getSelectedIndex();
        if (seleccion >= 0) {

            //Elimina el producto de la lista
            eliminarProductoDeLista();

            //Elimina el producto de la tabla
            tabla.getItems().remove(seleccion);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No hay selección");
            alert.setHeaderText("Ningun producto seleccionado");
            alert.setContentText("Por favor seleccione un producto en la tabla.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Metodo que comprueba que la solicitud tiene productos, y si es asi, llama
     * al metodo para insertar los datos en la bd
     */
    @FXML
    private void enviarSolicitud() {
        //Si no hay productos, muestra el error correspondiente
        if (ControladorPrincipal.productos.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No hay productos");
            alert.setHeaderText(null);
            alert.setContentText("No se puede enviar una solicitud sin productos.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();

        } else {
            //Inserta en la base de datos
            insertarDatos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("Solicitud enviada.");
            //Modificamos el estilo
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("sky.css").toExternalForm());
            alert.showAndWait();

            dialogo.close();
        }
    }

    /**
     * Metodo que inserta los datos de la solicitud en la base de datos
     */
    private void insertarDatos() {

        try (Statement insertarRma = ConexionBD.getConexion().createStatement()) {

            //Inserta el rma
            insertarRma.executeUpdate("INSERT INTO rma (id, fecha) VALUES('" + TFReferencia.getText() + "', '" + TFFecha.getText() + "')");

            for (Producto prod : ControladorPrincipal.productos) {

                insertarRma.executeUpdate("INSERT INTO producto (id, nombre, numero_factura) VALUES('" + prod.getEan() + "', '" + prod.getNombre() + "', '" + prod.getNumeroFactura() + "')");
                insertarRma.executeUpdate("INSERT INTO problema (descripcion, id_rma, id_producto) VALUES('" + prod.getProblema() + "', '" + TFReferencia.getText() + "', '" + prod.getEan() + "')");

            }

            ConexionBD.hacerCommit();
            ConexionBD.cerrarConexion();

        } catch (SQLException | ClassNotFoundException ex) {
            //Si lanza excepcion, no se hace ningun insert
            try {
                ConexionBD.hacerRollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Metodo que asigna valor a las variables con la que se va a interactuar
     *
     * @param dial
     * @param prin
     */
    public void setDialog(Stage dial, Stage prin) {
        dialogo = dial;
        primerStage = prin;
    }

    /**
     * Metodo que borra el producto de la lista que esta en el controlador
     * principal
     */
    private void eliminarProductoDeLista() {

        int eanSeleccionado = tabla.getSelectionModel().getSelectedItem().getEan();

        Iterator iterador = ControladorPrincipal.productos.iterator();

        while (iterador.hasNext()) {
            Producto pro = (Producto) iterador.next();
            if (pro.getEan() == eanSeleccionado) {
                iterador.remove();
            }
        }
    }
}
