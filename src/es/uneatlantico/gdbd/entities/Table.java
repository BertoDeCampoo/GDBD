package es.uneatlantico.gdbd.entities;

import java.util.*;

public class Table {

	private String nombre;
	private Collection<Column> columns;

	public String getNombre() {
		return this.nombre;
	}

	public Collection<Column> getColumns() {
		return this.columns;
	}

	/**
	 * Constructor for the Table entity which represents a table inside a database
	 * @param nombre name of the table
	 * @param columns list of columns on the list
	 */
	public Table(String nombre, java.util.List<Column> columns) {
		// TODO - implement Table.Table
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		// TODO - implement Table.toString
		throw new UnsupportedOperationException();
	}

}