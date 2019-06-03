package es.uneatlantico.es.gdbd.reports;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Class which implements the IReport interface and can generate Office Open XML Document (DOCX) files
 * @author Alberto Gutiérrez Arroyo
 */
public class DOCXReport implements IReport {
	
	private static final Logger logger = LogManager.getLogger(DOCXReport.class);

	@Override
	public String export(String reportFile, String exportPath, Connection dataSourceConnection) throws Exception {
		exportPath += ".docx";
		logger.log(Level.INFO, "Generando informe DOCX en " + exportPath + "...");
		File file = new File(getClass().getClassLoader().getResource(reportFile).getFile());
		
		String filePath = file.getCanonicalPath();
		
		JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
		
		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
	
   		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConnection);
   		
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportPath));

		exporter.exportReport();
		logger.log(Level.INFO, "Informe DOCX generado con éxito", exportPath);
		return exportPath;
	}

}
