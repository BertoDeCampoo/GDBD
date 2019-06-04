package es.uneatlantico.gdbd.reports;

import java.sql.Connection;

/**
 * Contains the specification every report must implement in order to be compatible with the application
 * @author Alberto Gutiérrez Arroyo
 */
public interface IReport {
	
	/**
	 * Exports the report with the given attributes
	 * @param reportFile  contains the path to the report file (Usually a .jrxml file)
	 * @param exportPath  the path in which to export the generated file
	 * @param dataSourceConnection  the connection to the datasource. For example, our SQLite Database, but it could be any database
	 * @return  the path of the generated report
	 * @throws Exception  if any error happens during the processing
	 * 
	 */
	public String export(String reportFile, String exportPath, Connection dataSourceConnection) throws Exception;
}
