package es.uneatlantico.gdbd.entities;

import java.util.*;

public class Database {

	private String nombre;
	private String servidor;
	private String tipo;
	private Collection<Table> tables;

	public String getNombre() {
		return this.nombre;
	}

	public String getServidor() {
		return this.servidor;
	}

	public String getTipo() {
		return this.tipo;
	}

	public Collection<Table> getTables() {
		return this.tables;
	}

	/**
	 * Constructor for the database entity which represents a database
	 * @param nombre name of the database
	 * @param servidor server which contains the database
	 * @param tipo
	 * @param tables
	 */
	public Database(String nombre, String servidor, String tipo, java.util.List<Table> tables) {
		// TODO - implement Database.Database
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		// TODO - implement Database.toString
		throw new UnsupportedOperationException();
	}

}