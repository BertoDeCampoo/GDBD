package es.uneatlantico.es.gdbd.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RTFReport implements IReport {

	@Override
	public void export(String reportFile, String exportPath, Connection dataSourceConnection) throws JRException {
		throw new NotImplementedException();
//		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
//	    File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".rtf");
//	    JRRtfExporter exporter = new JRRtfExporter();
//	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//	    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
//	    exporter.exportReport();
	}

}
