package entities.uneatlantico.es;

import java.util.List;

public class Database {
	
	private String nombre, servidor;
	private List<Table> tables;
	
	/**
	 * Constructor for the database entity which represents a database
	 * @param nombre  name of the database
	 * @param servidor  server which contains the database
	 */
	public Database (String nombre, String servidor, List<Table> tables)
	{
		this.nombre = nombre;
		this.servidor = servidor;
		this.tables = tables;
	}

	public String getNombre() {
		return nombre;
	}

	public String getServidor() {
		return servidor;
	}

	public List<Table> getTables() {
		return tables;
	}

}
