package es.uneatlantico.gdbd.entities;

import java.util.List;

public class Database {
	
	private String nombre, servidor, tipo;
	private List<Table> tables;
	
	/**
	 * Constructor for the database entity which represents a database
	 * @param nombre  name of the database
	 * @param servidor  server which contains the database
	 * @param tipo  the type of this database (DBMS)
	 * @param tables  the list of tables the database will contain
	 */
	public Database (String nombre, String servidor, String tipo, List<Table> tables)
	{
		this.nombre = nombre;
		this.servidor = servidor;
		this.tipo = tipo;
		this.tables = tables;
	}

	/**
	 * Obtains the name of the database
	 * @return  the name of the database
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Changes the name of the database (Use with caution or you might get databases whose names you are not aware of)
	 * @param nombre  the new name for the database
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * Obtains the name of the server in which the database is hosted
	 * @return  the name of the server
	 */
	public String getServidor() {
		return servidor;
	}
	
	/**
	 * Obtains the DBMS type. For example <code>MySQL</code>, <code>Postgre</code>,...
	 * @return  the DBMS type of this database
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Obtains a list with the tables on this database
	 * @return  the list of tables
	 */
	public List<Table> getTables() {
		return tables;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Database [");
		builder.append(nombre);
		builder.append("@");
		builder.append(servidor);
		builder.append(" (");
		builder.append(tipo);
		builder.append(")\n\tTables:");
		for (Table table : getTables())
		{
			builder.append("\n\t");
			builder.append(table.toString());
		}
		builder.append("]");
		return builder.toString();
	}

}
