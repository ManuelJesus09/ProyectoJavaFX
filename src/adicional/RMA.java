/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adicional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Manuel
 */
public class RMA {

    private final SimpleStringProperty fecha;
    private final SimpleIntegerProperty referencia;

    public RMA(int ref, String fecha) {
        this.fecha = new SimpleStringProperty(fecha);
        this.referencia = new SimpleIntegerProperty(ref);
    }

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
