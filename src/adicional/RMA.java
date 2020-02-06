package adicional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase que identifica a una solicitud de garantia (RMA), cada solicitud
 * contiene una referencia única, y la fecha en la que se hace la solicitud
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class RMA {

    private final SimpleStringProperty fecha;
    private final SimpleIntegerProperty referencia;

    /*
    Inicializa las variables
     */
    public RMA(int ref, String fecha) {
        this.fecha = new SimpleStringProperty(fecha);
        this.referencia = new SimpleIntegerProperty(ref);
    }

    /**
     * Metodo que devuelve los datos de una solicitud
     */
    @Override
    public String toString() {
        return "RMA{" + "fecha=" + fecha + ", referencia=" + referencia + '}';
    }

    public String getFecha() {
        return fecha.getValue();
    }

    public String getReferencia() {
        return String.valueOf(referencia.getValue());
    }

}
