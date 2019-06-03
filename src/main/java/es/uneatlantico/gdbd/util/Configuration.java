package es.uneatlantico.gdbd.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to load and retrieve application properties
 *
 */
public class Configuration {
	
	private static final Logger logger = LogManager.getLogger(Configuration.class); 
	private static String configFile = "/config.properties";
	private static Properties properties = null;
	
	public static String getDefaultSQLiteFilename(){
		if (properties == null)
			initializeProperties();
		return properties.getProperty("DEFAULT_SQLITE_FILENAME");
	}
	
	public static String getDefaultReportFilename(){
		if (properties == null)
			initializeProperties();
		return properties.getProperty("DEFAULT_REPORT_NAME");
	}
	
	private static void initializeProperties(){
		try{
			properties = new Properties();
			properties.load(Configuration.class.getResourceAsStream(configFile));
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}		
	}
}
