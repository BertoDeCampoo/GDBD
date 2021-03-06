package es.uneatlantico.gdbd.util;

public class PathResolver {
	
	/**
	 * Gets the directory in which the program is running (Example: if the .jar is on C://Users/gdbd.jar it will return C:/Users/
	 * @return  the current directory
	 */
	public static String getCurrentDirectory()
	{
		return System.getProperty("user.dir") + System.getProperty("file.separator");
	}

}
