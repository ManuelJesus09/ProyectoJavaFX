/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adicional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableView;

/**
 *
 * @author Manuel
 */
public class DAORma {

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

    public static void rellenarTablaProductosRMA(TableView<Producto> tabla, String numeroRma) {

        try (Statement sentencia = ConexionBD.getConexion().createStatement()) {
            ResultSet resul = sentencia.executeQuery("SELECT p.nombre, pr.descripcion FROM producto p INNER JOIN problema pr ON p.id=pr.id_producto WHERE pr.id_rma=" + numeroRma);

            while (resul.next()) {

                String nombreProd = resul.getString("nombre");
                String poema = resul.getString("descripcion");

                Producto temporal = new Producto();
                temporal.setNombre(nombreProd);
                temporal.setProblema(poema);
                //Add fila
                tabla.getItems().add(temporal);
            }

            ConexionBD.cerrarConexion();
        } catch (SQLException | ClassNotFoundException | RMAException e) {
            e.printStackTrace();
        }
    }
}
