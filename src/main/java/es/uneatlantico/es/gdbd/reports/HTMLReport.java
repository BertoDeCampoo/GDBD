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
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;

/**
 * Class which implements the IReport interface and can generate HyperText Markup Language (HTML) files
 * @author Alberto Gutiérrez Arroyo
 */
public class HTMLReport implements IReport {
	
	private static final Logger logger = LogManager.getLogger(HTMLReport.class);

	@Override
	public String export(String reportFile, String exportPath, Connection dataSourceConnection) throws Exception {
		exportPath += ".html";
		logger.log(Level.INFO, "Generando informe HTML en " + exportPath + "...");
		HtmlExporter htmlExporter = new HtmlExporter();
		
		File file = new File(getClass().getClassLoader().getResource(reportFile).getFile());
		String filePath = file.getCanonicalPath();
		JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
		
		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConnection);
		SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		
		//File returnFile = new File("C:/jasperoutput/InformeBBDD.html");
		SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(new File(exportPath));

	    SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
	    reportExportConfiguration.setSizeUnit(HtmlSizeUnitEnum.POINT);
	    htmlExporter.setConfiguration(reportExportConfiguration);
    	htmlExporter.setExporterInput(exporterInput);
  		htmlExporter.setExporterOutput(exporterOutput);
  		htmlExporter.exportReport();
  		logger.log(Level.INFO, "Informe HTML generado con éxito", exportPath);
  		return exportPath;
	}

}
