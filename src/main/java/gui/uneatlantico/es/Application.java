package gui.uneatlantico.es;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import entities.uneatlantico.es.Database;
import persistence.uneatlantico.es.SQLiteManager;

public class Application {

	private SQLiteManager sqliteManager;
	private JFrame frmGdbd;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnDocumentation;
	private JMenu mnHelp;
	private JMenuItem mntmNewDatabase;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmGdbd.setVisible(true);
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
		frmGdbd = new JFrame();
		frmGdbd.setTitle("GDBD");
		frmGdbd.setBounds(100, 100, 450, 300);
		frmGdbd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGdbd.setJMenuBar(getMenuBar());
		sqliteManager = new SQLiteManager("sqlite.db");
		sqliteManager.initializeDatabase();
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
			menuBar.add(getMnDocumentation());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}
	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("Archivo");
		}
		return mnFile;
	}
	private JMenu getMnDocumentation() {
		if (mnDocumentation == null) {
			mnDocumentation = new JMenu("Documentaci\u00F3n");
			mnDocumentation.add(getMntmNewDatabase());
		}
		return mnDocumentation;
	}
	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Ayuda");
		}
		return mnHelp;
	}
	private JMenuItem getMntmNewDatabase() {
		if (mntmNewDatabase == null) {
			mntmNewDatabase = new JMenuItem("A\u00F1adir base de datos");
			mntmNewDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					LoginDialog databaseLogin = new LoginDialog(frmGdbd);
					//dg.setModal(true);
					databaseLogin.setVisible(true);
					
					String dbName = databaseLogin.getLogonDatabase().getSelectedDatabase();
					Database database = databaseLogin.getLogonDatabase().getDatabaseInformation(dbName);
					
					sqliteManager.addNewDatabase(database);
					
					System.out.println(database);
				}
			});
		}
		return mntmNewDatabase;
	}
}
