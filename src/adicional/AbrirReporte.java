package adicional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Manuel Jesus Sanchez Vega
 */
public class AbrirReporte extends JFrame {

    /**
     * Metodo que abre el un reporte previamente hecho en JasperSoft Studio
     */
    public static void abrirRmaCount() throws ClassNotFoundException, SQLException, JRException, IOException {
        AbrirReporte ventana = new AbrirReporte();
        Connection conexion = ConexionBD.getConexion();

        String rutaArchivo = "src/reporte/ReporteRMACOUNT.jrxml";

        // Compila el archivo jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaArchivo);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, conexion);

        JRViewer viewer = new JRViewer(print);

        //Exportar a pdf
        
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("rmaCount.pdf"));

        exporter.exportReport();

        viewer.setOpaque(true);
        viewer.setVisible(true);
        ventana.add(viewer);
        
        ventana.setSize(900, 700);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ConexionBD.cerrarConexion();
    }
    
    /**
     * Metodo que abre un reporte previamente creado en jaspersoft studio
     */
    public static void abrirRma() throws ClassNotFoundException, SQLException, JRException, IOException {
        AbrirReporte ventana = new AbrirReporte();
        Connection conexion = ConexionBD.getConexion();

        String rutaArchivo = "src/reporte/ReporteRMA.jrxml";

        // Compila el archivo jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaArchivo);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, conexion);

        JRViewer viewer = new JRViewer(print);

        //Exportar a pdf
        
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("rma.pdf"));

        exporter.exportReport();

        viewer.setOpaque(true);
        viewer.setVisible(true);
        ventana.add(viewer);
        
        ventana.setSize(700, 500);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ConexionBD.cerrarConexion();
    }
}
