package es.uneatlantico.es.gdbd.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PDFReport implements IReport {

	@Override
	public void export(String reportFile, String exportPath, Connection dataSourceConnection) throws JRException {
		//jasperReport = JasperCompileManager.compileReport("D:/Escritorio/JReports/MyReports/ReportTFG.jrxml");
		File file = new File(getClass().getClassLoader().getResource(reportFile).getFile());
		
		String filePath;
		try {
			filePath = file.getCanonicalPath();
		} catch (IOException e) {
			return;
		}
		System.out.println(filePath);
		JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
		

		// Parameters for report
		Map<String, Object> parameters = new HashMap<String, Object>();
	
   		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConnection);
   	 
   	    
        // Make sure the output directory exists.
        File outDir = new File(exportPath);
        if (!outDir.exists())
        	outDir.mkdirs();
  
        // Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "/InformeBBDD.pdf");
		
	}

}
