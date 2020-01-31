package adicional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class ConexionBD {

    private static final String URL_BD = "jdbc:sqlite:bd/rmas.db";
    private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
    private static Connection conexion;

    private ConexionBD() throws SQLException, ClassNotFoundException {

        Class.forName(DRIVER_SQLITE);
        conexion = DriverManager.getConnection(URL_BD);
        conexion.setAutoCommit(false);

    }

    public static Connection getConexion() throws SQLException, ClassNotFoundException {
        //Si la conexion es null, o se ha cerrado, la vuelve a crear
        if (conexion == null) {
            new ConexionBD();
        }

        return conexion;
    }

    public static void cerrarConexion() throws SQLException {

        //Si la conexion no esta abierta no entra
        if (conexion != null) {
            conexion.close();
            conexion = null;
        }

    }

    public static void hacerCommit() throws SQLException {

        if (conexion != null) {

            conexion.commit();
        }

    }

    public static void hacerRollback() throws SQLException {

        if (conexion != null) {

            conexion.rollback();

        }

    }
}
