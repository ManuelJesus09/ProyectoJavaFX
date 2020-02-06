package adicional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableView;

/**
 *  Clase que interactua con la base de datos, introduciendo y recogiendo datos
 * @author Manuel Jesus Sanchez Vega
 */
public class DAORma {

    /**
     * Metodo que crea un numero de referencia para la solicitud aleatoriamente,
     * y teniendo en cuenta que no exista en la base de datos
     * @return devuelve el numero de referencia nuevo
     */
    public static int crearReferencia() {

        int referencia = -1;
        boolean rmaExiste;

        try (Statement rmaExistente = ConexionBD.getConexion().createStatement()) {

            //Crea una referencia nueva, sigue dando vueltas en el bucle si la referencia existe en la base de datos
            do {
                referencia = (int) (100000 * Math.random());
                ResultSet resulRma = rmaExistente.executeQuery("SELECT * FROM rma WHERE id=" + referencia);
                rmaExiste = resulRma.next();
            } while (rmaExiste);

            ConexionBD.hacerCommit();
            ConexionBD.cerrarConexion();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return referencia;
    }

    /**
     * Metodo que rellena la tabla que se le pasa por parametros con los datos
     * de la tabla rma de la base de datos
     * @param tabla tabla a rellenar con los datos de la bd
     */
    public static void rellenarTablaRma(TableView<RMA> tabla) {
        try (Statement sentencia = ConexionBD.getConexion().createStatement()) {

            ResultSet resul = sentencia.executeQuery("SELECT * FROM rma");

            while (resul.next()) {

                int referencia = resul.getInt("id");
                String fecha = resul.getString("fecha");
                RMA temporal = new RMA(referencia, fecha);

                //Añade la columna a la tabla
                tabla.getItems().add(temporal);
            }
            ConexionBD.cerrarConexion();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que rellena la tabla que se le pasa por parametros con los datos de los productos
     * @param tabla tabla a rellenar
     * @param numeroRma numero de rma que contiene los productos a añadir en la tabla
     */
    public static void rellenarTablaProductosRMA(TableView<Producto> tabla, String numeroRma) {

        try (Statement sentencia = ConexionBD.getConexion().createStatement()) {
            ResultSet resul = sentencia.executeQuery("SELECT p.nombre, pr.descripcion FROM producto p INNER JOIN problema pr ON p.id=pr.id_producto WHERE pr.id_rma=" + numeroRma);

            while (resul.next()) {

                String nombreProd = resul.getString("nombre");
                String poema = resul.getString("descripcion");

                //Crea el producto con los datos recogidos
                Producto temporal = new Producto();
                temporal.setNombre(nombreProd);
                temporal.setProblema(poema);
                
                //Annade la fila a la tabla
                tabla.getItems().add(temporal);
            }

            ConexionBD.cerrarConexion();
        } catch (SQLException | ClassNotFoundException | RMAException e) {
            e.printStackTrace();
        }
    }
}
