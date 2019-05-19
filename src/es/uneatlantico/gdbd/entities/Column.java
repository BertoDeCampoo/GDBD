package es.uneatlantico.gdbd.entities;

public class Column {

	private String nombre;
	private String tipo_dato;
	int tam_dato;

	public String getNombre() {
		return this.nombre;
	}

	public String getTipo_dato() {
		return this.tipo_dato;
	}

	public int getTam_dato() {
		return this.tam_dato;
	}

	/**
	 * Constructor of the entity Column which represents a column of a table in the database
	 * @param nombre name of the column
	 * @param tipo_dato type of data this column stores
	 * @param tam_dato size of the data field
	 */
	public Column(String nombre, String tipo_dato, int tam_dato) {
		// TODO - implement Column.Column
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		// TODO - implement Column.toString
		throw new UnsupportedOperationException();
	}

}