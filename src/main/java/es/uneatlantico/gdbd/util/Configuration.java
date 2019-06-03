package es.uneatlantico.gdbd.util;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to load and retrieve application properties
 * @author Alberto Gutiérrez Arroyo
 */
public class Configuration {
	
	private static final Logger logger = LogManager.getLogger(Configuration.class); 
	private static String configFile = "/config.properties";
	private static Properties properties = null;
	
	/**
	 * Obtains the default name for the SQLite database
	 * @return  the name for the SQLite database
	 */
	public static String getDefaultSQLiteFilename(){
		if (properties == null)
			initializeProperties();
		return properties.getProperty("DEFAULT_SQLITE_FILENAME");
	}
	
	/**
	 * Obtains the default report name (The file the user will get once generated) without the file extension
	 * @return  the default name of the exported report without extension
	 */
	public static String getDefaultReportFilename(){
		if (properties == null)
			initializeProperties();
		return properties.getProperty("DEFAULT_REPORT_NAME");
	}
	
	/**
	 * Obtains the Jasper Reports database report (A JRXML file) file name
	 * @return  the name of the database report file
	 */
	public static String getDatabaseReportFilename() {
		if (properties == null)
			initializeProperties();
		return properties.getProperty("DATABASE_REPORT_FILENAME");
	}
	
	/**
	 * Initializes the properties
	 */
	private static void initializeProperties(){
		try{
			properties = new Properties();
			properties.load(Configuration.class.getResourceAsStream(configFile));
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}		
	}
}
