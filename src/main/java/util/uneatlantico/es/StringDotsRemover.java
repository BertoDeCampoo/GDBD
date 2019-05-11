package util.uneatlantico.es;

public class StringDotsRemover {
	
	/**
	 * Gets the directory in which the program is running (Example: if the .jar is on C://Users/gdbd.jar it will return C:/Users/
	 * @return  the current directory
	 */
	public static String cleanString(String str)
	{
		if(str.contains("."))
		{
			System.out.println("string contains dot");
			return str.replace(".", "");
		}
		return str;
	}

}
