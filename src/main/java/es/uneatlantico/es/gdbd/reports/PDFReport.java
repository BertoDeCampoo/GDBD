package es.uneatlantico.es.gdbd.reports;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Class which implements the IReport interface and can generate Portable Document Format (PDF) files
 * @author Alberto Gutiérrez Arroyo
 */
public class PDFReport implements IReport {

	private static final Logger logger = LogManager.getLogger(PDFReport.class); 
	
	@Override
	public String export(String reportFile, String exportPath, Connection dataSourceConnection) throws Exception {
		exportPath += ".pdf";
		logger.log(Level.INFO, "Generando informe PDF en " + exportPath + "...");
		File file = new File(getClass().getClassLoader().getResource(reportFile).getFile());
		
		String filePath = file.getCanonicalPath();
		
		JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
		
		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
	
   		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConnection);
        
        // Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
		logger.log(Level.INFO, "Informe PDF generado con éxito", exportPath);
		return exportPath;
	}

}
