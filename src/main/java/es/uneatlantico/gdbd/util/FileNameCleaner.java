package es.uneatlantico.gdbd.util;

public class FileNameCleaner {
	
	/**
	 * It will replace every character on the string different from an alphanumeric or a "-" with a "_" character
	 * @param string  string to clean
	 * @return  the cleaned string
	 */
	public static String cleanString(String string)
	{
		return string.replaceAll("[^a-zA-Z0-9\\-]", "_");
	}

}
