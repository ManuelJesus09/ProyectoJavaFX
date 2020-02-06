package adicional;

import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase que simula un objeto Producto, cada producto tiene un id unico, y
 * tambien contiene los datos del problema que tiene, su nombre y la referencia
 * de la factura
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class Producto {

    private final SimpleStringProperty nombre;
    private final SimpleStringProperty problema;
    private final SimpleIntegerProperty ean;
    private final SimpleIntegerProperty numeroFactura;

    /**
     * Inicia las variables
     */
    public Producto() {

        this.nombre = new SimpleStringProperty();
        this.problema = new SimpleStringProperty();
        this.ean = new SimpleIntegerProperty();
        this.numeroFactura = new SimpleIntegerProperty();
    }

    /**
     * Modifica la variable nombre
     * @param nombre nombre nuevo
     * @throws RMAException lanza excepcion si el nombre esta vacio
     */
    public void setNombre(String nombre) throws RMAException {
        if (nombre.equals("")) {
            throw new RMAException("Debes introducir un nombre.");
        }
        this.nombre.set(nombre);
    }

    /**
     * Metodo que devuelve los datos de un producto
     * @return los datos de dicho producto
     */
    @Override
    public String toString() {
        return "Producto con " + "nombre: " + nombre + ", problema: " + problema + ", ean: " + ean + ", numero de factura de compra: " + numeroFactura + '.';
    }

    /**
     * Metodo que cambia la variable problema
     * @param problema problema nuevo
     * @throws RMAException lanza excepcion si el problema esta vacio
     */
    public void setProblema(String problema) throws RMAException {
        if (problema.equals("")) {
            throw new RMAException("Debes introducir un problema.");
        }
        this.problema.set(problema);
    }

    /**
     * Metodo que cambia el valor de la variable ean
     * @param ean  ean nuevo
     * @throws RMAException lanza excepcion si el ean es -1
     */
    public void setEan(int ean) throws RMAException {
        //Se compara con -1 ya que la referencia 0 puede existir, y si el usuario no ha introducido nada, el valor por defecto es -1
        if (ean == -1) {
            throw new RMAException("Debes introducir un codigo del producto(EAN).");
        }
        this.ean.set(ean);
    }

    /**
     * Metodo que cambia el valor de la variable numeroFactura
     * @param numeroFactura numero de factura nuevo
     * @throws RMAException lanza excepcion si el numero de factura es -1
     */
    public void setNumeroFactura(int numeroFactura) throws RMAException {
        if (numeroFactura == -1 || numeroFactura < 0) {
            throw new RMAException("Debes introducir un numero de factura correcto.");
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

    /**
     * Compara dos productos por el codigo ean
     * @param obj producto con el que se compara
     * @return 
     */
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
