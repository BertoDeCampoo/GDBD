package entities.uneatlantico.es;

import java.util.List;

public class Table {
	
	private String nombre;
	private List<Column> columns;
	
	/**
	 * Constructor for the Table entity which represents a table inside a database
	 * @param nombre  name of the table
	 * @param columns  list of columns on the list
	 */
	public Table(String nombre, List<Column> columns)
	{
		this.nombre = nombre;
		this.columns = columns;
	}
	
	public String getNombre() {
		return nombre;
	}

	public List<Column> getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [");
		builder.append(nombre);
		builder.append("\n\t\tColumns:");
		for (Column column : getColumns())
		{
			builder.append("\n\t\t");
			builder.append(column.toString());
		}
		builder.append("]");
		return builder.toString();
	}

}
