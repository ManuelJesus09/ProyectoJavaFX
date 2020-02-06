package adicional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase singleton para conectar la aplicacion a la base de datos
 * @author Manuel Jesus Sanchez Vega
 */
public class ConexionBD {

    private static final String URL_BD = "jdbc:sqlite:bd/rmas.db";
    private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
    private static Connection conexion;

    /**
     * Constructor, crea la conexion y le desactiva el autocommit
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private ConexionBD() throws SQLException, ClassNotFoundException {

        Class.forName(DRIVER_SQLITE);
        conexion = DriverManager.getConnection(URL_BD);
        conexion.setAutoCommit(false);

    }

    /**
     * Metodo que devuelve una conexion, siempre que sea null(Singleton)
     * @return el objeto ConexionBD actual, o si no existe uno nuevo
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection getConexion() throws SQLException, ClassNotFoundException {
        //Si la conexion es null, o se ha cerrado, la vuelve a crear
        if (conexion == null) {
            new ConexionBD();
        }
        return conexion;
    }

    /**
     * Metodo que cierra la conexion de la base de datos, y la pone a null para cuando
     * se vuelva a llamar, que se cree una nueva
     * @throws SQLException 
     */
    public static void cerrarConexion() throws SQLException {

        //Si la conexion no esta abierta no entra
        if (conexion != null) {
            conexion.close();
            conexion = null;
        }

    }

    /**
     * Metodo que hace commit, para guardar cambios en la base de datos
     * @throws SQLException 
     */
    public static void hacerCommit() throws SQLException {

        if (conexion != null) {

            conexion.commit();
        }

    }

    /**
     * Metodo que hace rollback a la bd, por si algo ha salido mal y no queremos guardar cambios
     * @throws SQLException 
     */
    public static void hacerRollback() throws SQLException {

        if (conexion != null) {

            conexion.rollback();

        }

    }
}
