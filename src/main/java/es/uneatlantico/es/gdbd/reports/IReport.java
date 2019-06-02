package es.uneatlantico.es.gdbd.reports;

import java.sql.Connection;

import net.sf.jasperreports.engine.JRException;

public interface IReport {
	
	public void export(String reportFile, String exportPath, Connection dataSourceConnection) throws JRException;
}
