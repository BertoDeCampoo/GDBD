package entities.uneatlantico.es;

import java.util.List;

public class Database {
	
	private String nombre, servidor, tipo;
	private List<Table> tables;
	
	/**
	 * Constructor for the database entity which represents a database
	 * @param nombre  name of the database
	 * @param servidor  server which contains the database
	 */
	public Database (String nombre, String servidor, String tipo, List<Table> tables)
	{
		this.nombre = nombre;
		this.servidor = servidor;
		this.tipo = tipo;
		this.tables = tables;
	}

	public String getNombre() {
		return nombre;
	}

	public String getServidor() {
		return servidor;
	}
	
	public String getTipo() {
		return tipo;
	}

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
