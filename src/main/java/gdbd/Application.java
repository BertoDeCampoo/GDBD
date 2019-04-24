package gdbd;

import java.awt.EventQueue;
import gdbd.ConnectMSSQLServer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application {

	private JFrame frame;
	private JPanel panel;
	private JButton btnConnect;

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
}
