package es.uneatlantico.gdbd.entities;

public class Column {
	
	private String nombre, tipo_dato;
	int tam_dato;
	
	/**
	 * Constructor of the entity Column which represents a column of a table in the database
	 * @param nombre  name of the column
	 * @param tipo_dato  type of data this column stores
	 * @param tam_dato  size of the data field
	 */
	public Column(String nombre, String tipo_dato, int tam_dato)
	{
		this.nombre = nombre;
		this.tipo_dato = tipo_dato;
		this.tam_dato = tam_dato;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo_dato() {
		return tipo_dato;
	}
	
	public int getTam_dato() {
		return tam_dato;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Column [");
		builder.append(nombre);
		builder.append(", ");
		builder.append(tipo_dato);
		builder.append("(");
		builder.append(tam_dato);
		builder.append(")]");
		return builder.toString();
	}

}
