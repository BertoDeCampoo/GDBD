package es.uneatlantico.es.gdbd.reports;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 * Class which implements the IReport interface and can generate Rich Text Format (RTF) files
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class RTFReport implements IReport {

	private static final Logger logger = LogManager.getLogger(RTFReport.class); 
	
	@Override
	public String export(String reportFile, String exportPath, Connection dataSourceConnection) throws Exception {
		exportPath += ".rtf";
		logger.log(Level.INFO, "Generando informe RTF en " + exportPath + "...");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(RTFReport.this.getClass().getResource(reportFile));
		
		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
	
   		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConnection);
   		
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(exportPath));
		
		exporter.exportReport();
		logger.log(Level.INFO, "Informe RTF generado con éxito", exportPath);
		return exportPath;
	}

}
