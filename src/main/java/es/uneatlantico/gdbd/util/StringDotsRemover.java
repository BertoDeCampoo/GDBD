package es.uneatlantico.gdbd.util;

/**
 * Utility class used to remove "." from Strings
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class StringDotsRemover {
	
	/**
	 * Gets the directory in which the program is running (Example: if the .jar is on C://Users/gdbd.jar it will return C:/Users/
	 * @param str  the string to clean
	 * @return  the current directory
	 */
	public static String cleanString(String str)
	{
		if(str.contains("."))
		{
			return str.replace(".", "");
		}
		return str;
	}

}
