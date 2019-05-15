package gdbd;

import java.awt.EventQueue;
import persistence.uneatlantico.es.SQLiteManager;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dataaccess.uneatlantico.es.MSSQLServerDatabase;
import dataaccess.uneatlantico.es.MySQLDatabase;
import entities.uneatlantico.es.Database;
import entities.uneatlantico.es.Table;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class TestApplication {

	private JFrame frame;
	private JPanel panel;
	private JButton btnConexinConMysql;
	private JPanel panel_1;
	private JButton btnCreateSqliteDatabase;
	private JButton btnObtenerTablasnueva;
	private JButton btnMostrarultimoindicebbdd;
	private JButton btnListserverdbs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestApplication window = new TestApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(getPanel(), BorderLayout.SOUTH);
		frame.getContentPane().add(getPanel_1(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnConexinConMysql());
		}
		return panel;
	}
	
	private JButton getBtnConexinConMysql() {
		if (btnConexinConMysql == null) {
			btnConexinConMysql = new JButton("Conexi\u00F3n con MySQL");
			btnConexinConMysql.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					List<String> tablas, columnas;
					char[] pass = new char[4];
					
					pass[0] = '1';
					pass[1] = '2';
					pass[2] = '3';
					pass[3] = '4';
					
					//MySQLDatabase db = new MySQLDatabase("jdbc:mysql://localhost:3306/personal_mysql", "root", pass);
					MySQLDatabase db = new MySQLDatabase("localhost", "personal_mysql", "root", pass);
					
					try {
						db.getConnection();
						tablas = db.getTableNames();
						for(int i=0; i<tablas.size();i++)
						{
//							System.out.print(tablas.get(i) + ": ");
							columnas = db.getTableNames();
							for (String columna : columnas) {
								System.out.print(columna + ", ");
							}
							System.out.println();
						}
						JOptionPane.showMessageDialog(frame, "Se ha realizado la conexión", "Conexión establecida", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), "Error de conexión a la base de datos", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
		}
		return btnConexinConMysql;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getBtnCreateSqliteDatabase());
			panel_1.add(getBtnObtenerTablasnueva());
			panel_1.add(getBtnMostrarultimoindicebbdd());
			panel_1.add(getBtnListserverdbs());
		}
		return panel_1;
	}
	private JButton getBtnCreateSqliteDatabase() {
		if (btnCreateSqliteDatabase == null) {
			btnCreateSqliteDatabase = new JButton("Create SQLite Database");
			btnCreateSqliteDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SQLiteManager mgr = new SQLiteManager("sqlite");
					mgr.initializeDatabase();
				}
			});
		}
		return btnCreateSqliteDatabase;
	}
	private JButton getBtnObtenerTablasnueva() {
		if (btnObtenerTablasnueva == null) {
			btnObtenerTablasnueva = new JButton("Obtener tablas (Nueva)");
			btnObtenerTablasnueva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					List<Table> tablas;
					char[] pass = new char[4];
					
					pass[0] = '1';
					pass[1] = '2';
					pass[2] = '3';
					pass[3] = '4';
					
					MSSQLServerDatabase db = new MSSQLServerDatabase("DESKTOP-M9PG788\\SQLEXPRESS", "sa", pass);
					
					tablas = db.getTables("DesarrolloBD");
					for (Table tabla : tablas)
					{
						System.out.println(tabla);
					}
				}
			});
		}
		return btnObtenerTablasnueva;
	}
	private JButton getBtnMostrarultimoindicebbdd() {
		if (btnMostrarultimoindicebbdd == null) {
			btnMostrarultimoindicebbdd = new JButton("Guardar bbdd en SQLite");
			btnMostrarultimoindicebbdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					char[] pass = new char[4];
					
					pass[0] = '1';
					pass[1] = '2';
					pass[2] = '3';
					pass[3] = '4';
					
					MSSQLServerDatabase db = new MSSQLServerDatabase("DESKTOP-M9PG788\\SQLEXPRESS", "sa", pass);
					Database database = db.getDatabaseInformation("DesarrolloBD");
					
					SQLiteManager mgr = new SQLiteManager("sqlite");
					mgr.addNewDatabase(database);
				}
			});
		}
		return btnMostrarultimoindicebbdd;
	}
	
	private JButton getBtnListserverdbs() {
		if (btnListserverdbs == null) {
			btnListserverdbs = new JButton("ListServerDBs");
			btnListserverdbs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					char[] pass = new char[4];
					
					pass[0] = '1';
					pass[1] = '2';
					pass[2] = '3';
					pass[3] = '4';
					
					MSSQLServerDatabase db = new MSSQLServerDatabase("DESKTOP-M9PG788\\SQLEXPRESS", "sa", pass);
					db.getTables("DesarrolloBD");
				}
			});
		}
		return btnListserverdbs;
	}
}
