package util.uneatlantico.es;

import java.net.URISyntaxException;

public class PathResolver {
	
	/**
	 * Gets the directory in which the program is running (Example: if the .jar is on C://Users/gdbd.jar it will return C:/Users/
	 * @return  the current directory
	 */
	public static String getCurrentDirectory()
	{
		try {
			return (PathResolver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		} catch (URISyntaxException e) {
			System.err.println("Impossible to obtain directory path");
		}
		return "";
	}

}
