package adicional;

import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class Producto {

    private final SimpleStringProperty nombre;
    private final SimpleStringProperty problema;
    private final SimpleIntegerProperty ean;
    private final SimpleIntegerProperty numeroFactura;

    public Producto() {

        this.nombre = new SimpleStringProperty();
        this.problema = new SimpleStringProperty();
        this.ean = new SimpleIntegerProperty();
        this.numeroFactura = new SimpleIntegerProperty();
    }

    public void setNombre(String nombre) throws RMAException {
        if (nombre.equals("")) {
            throw new RMAException("Debes introducir un nombre.");
        }
        this.nombre.set(nombre);
    }

    @Override
    public String toString() {
        return "Producto con " + "nombre: " + nombre + ", problema: " + problema + ", ean: " + ean + ", numero de factura de compra: " + numeroFactura + '.';
    }

    public void setProblema(String problema) throws RMAException {
        if (problema.equals("")) {
            throw new RMAException("Debes introducir un problema.");
        }
        this.problema.set(problema);
    }

    public void setEan(int ean) throws RMAException {
        //Se compara con -1 ya que la referencia 0 puede existir, y si el usuario no ha introducido nada, el valor por defecto es -1
        if (ean == -1) {
            throw new RMAException("Debes introducir un codigo del producto(EAN).");
        }
        this.ean.set(ean);
    }

    public void setNumeroFactura(int numeroFactura) throws RMAException {
        if (numeroFactura == -1) {
            throw new RMAException("Debes introducir un numero de Factura.");
        }
        this.numeroFactura.set(numeroFactura);
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getProblema() {
        return problema.get();
    }

    public int getEan() {
        return ean.get();
    }

    public int getNumeroFactura() {
        return numeroFactura.get();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        return true;
    }

}
