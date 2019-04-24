package gdbd;

import java.awt.EventQueue;
import gdbd.ConnectMSSQLServer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dataaccess.uneatlantico.es.MSSQLServerDatabase;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Application {

	private JFrame frame;
	private JPanel panel;
	private JButton btnConnect;
	private JButton btnConnectInterface;

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
		}
		return panel;
	}
	private JButton getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new JButton("Connect");
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
			btnConnectInterface = new JButton("Conexion con interfaz");
			btnConnectInterface.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<String> tablas, columnas;
					
					MSSQLServerDatabase db = new MSSQLServerDatabase();
					
					db.getConnection("jdbc:sqlserver://DESKTOP-M9PG788\\SQLEXPRESS", "sa", "1234");
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
				}
			});
		}
		return btnConnectInterface;
	}
}
