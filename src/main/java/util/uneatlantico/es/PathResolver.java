package util.uneatlantico.es;

public class PathResolver {
	
	/**
	 * Gets the directory in which the program is running (Example: if the .jar is on C://Users/gdbd.jar it will return C:/Users/
	 * @return  the current directory
	 */
	public static String getCurrentDirectory()
	{
		return System.getProperty("user.dir") + System.getProperty("file.separator");
//	return (PathResolver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
	}

}
