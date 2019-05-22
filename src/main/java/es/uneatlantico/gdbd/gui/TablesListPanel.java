package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;

import es.uneatlantico.gdbd.persistence.SQLiteManager;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class TablesListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3707291062089751084L;
	/**
	 * Identifier of the database. From the database identified with this ID the list of tables will be filled
	 */
	private int databaseID;
	private SQLiteManager sqliteManager;
	private JScrollPane scrlPnTables;
	private JTable tbTables;
	private JLabel lblTables;

	/**
	 * Create the panel.
	 */
	public TablesListPanel(SQLiteManager sqliteManager) {
		this.databaseID = databaseID;
		this.sqliteManager = sqliteManager;

		initGUI();
	}
	private void initGUI() {
		setLayout(new BorderLayout(0, 0));
		add(getScrlPnTables(), BorderLayout.CENTER);
		add(getLblTables(), BorderLayout.NORTH);
	}

	private JScrollPane getScrlPnTables() {
		if (scrlPnTables == null) {
			scrlPnTables = new JScrollPane(getTbTables());
		}
		return scrlPnTables;
	}
	private JTable getTbTables() {
		if (tbTables == null) {
			tbTables = new JTable();
		}
		return tbTables;
	}
	private JLabel getLblTables() {
		if (lblTables == null) {
			lblTables = new JLabel("Tablas en la base de datos:");
		}
		return lblTables;
	}
}
