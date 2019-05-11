package gdbd;

import java.awt.EventQueue;
import gdbd.ConnectMSSQLServer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dataaccess.uneatlantico.es.MSSQLServerDatabase;
import dataaccess.uneatlantico.es.MySQLDatabase;

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
	private JButton btnConnectInterface;
	private JButton btnConexinConMysql;

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
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnConnect());
			panel.add(getBtnConnectInterface());
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
}
