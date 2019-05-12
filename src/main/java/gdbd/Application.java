package gdbd;

import java.awt.EventQueue;
import gdbd.ConnectMSSQLServer;
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

public class Application {

	private JFrame frame;
	private JPanel panel;
	private JButton btnConnect;
	private JButton btnConexinConMysql;
	private JPanel panel_1;
	private JButton btnCreateSqliteDatabase;
	private JButton btnObtenerTablasnueva;
	private JButton btnMostrarultimoindicebbdd;
	private JButton btnGetnextfreeid;
	private JButton btnListserverdbs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
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
	public Application() {
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
			panel.add(getBtnConnect());
			//panel.add(getBtnConnectInterface());
			panel.add(getBtnConexinConMysql());
		}
		return panel;
	}
	private JButton getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new JButton("Connect");
			btnConnect.setEnabled(false);
			btnConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ConnectMSSQLServer connServer = new ConnectMSSQLServer();
				      connServer.dbConnect("jdbc:sqlserver://DESKTOP-M9PG788\\SQLEXPRESS", "sa",
				               "1234");
				}
			});
		}
		return btnConnect;
	}
	/*
	private JButton getBtnConnectInterface() {
		if (btnConnectInterface == null) {
			btnConnectInterface = new JButton("Conexion con SQL Server");
			btnConnectInterface.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> tablas, columnas;
					char[] pass = new char[4];
					
					pass[0] = '1';
					pass[1] = '2';
					pass[2] = '3';
					pass[3] = '4';
					
					MSSQLServerDatabase db = new MSSQLServerDatabase("DESKTOP-M9PG788", "SQLEXPRESS", "sa", pass);
					
					try {
						db.getConnection();
						tablas = db.getTableNames();
						for(int i=0; i<tablas.size();i++)
						{
							System.out.print(tablas.get(i) + ": ");
							columnas = db.getTableColumnNames(tablas.get(i));
							for (String columna : columnas) {
								System.out.print(columna + ", ");
							}
							System.out.println();
						}
							JOptionPane.showMessageDialog(frame, "Se ha realizado la conexión", "Conexión establecida", JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), "Error de conexión a la base de datos", JOptionPane.ERROR_MESSAGE);
						}
					
				}
			});
		}
		return btnConnectInterface;
	}
	*/
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
			panel_1.add(getBtnGetnextfreeid());
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
	private JButton getBtnGetnextfreeid() {
		if (btnGetnextfreeid == null) {
			btnGetnextfreeid = new JButton("GetNextFreeID");
			btnGetnextfreeid.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SQLiteManager mgr = new SQLiteManager("sqlite");
					int next;
					next = mgr.getNextAvailableID("BBDD");
					System.out.println("Siguiente ID libre en BBDD : " + next);
					next = mgr.getNextAvailableID("TABLAS");
					System.out.println("Siguiente ID libre en TABLAS : " + next);
					next = mgr.getNextAvailableID("COLUMNAS");
					System.out.println("Siguiente ID libre en COLUMNAS : " + next);
				}
			});
		}
		return btnGetnextfreeid;
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
					List<String> list = db.getDatabases();
					db.getTables("DesarrolloBD");
				}
			});
		}
		return btnListserverdbs;
	}
}
