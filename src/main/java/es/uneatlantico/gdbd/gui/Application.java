package es.uneatlantico.gdbd.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.persistence.SQLiteManager;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Application {

	private SQLiteManager sqliteManager;
	private JFrame frmGdbd;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnDocumentation;
	private JMenu mnHelp;
	private JMenuItem mntmNewDatabase;
	private JPanel pnDatabases;
	

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
		sqliteManager = new SQLiteManager(SQLiteManager.Default_Filename);
		sqliteManager.initializeDatabase();
		frmGdbd = new JFrame();
		frmGdbd.setMinimumSize(new Dimension(480, 280));
		frmGdbd.setTitle("GDBD");
		frmGdbd.setBounds(100, 100, 695, 389);
		frmGdbd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGdbd.setJMenuBar(getMenuBar());
		frmGdbd.getContentPane().add(getPnDatabases(), BorderLayout.WEST);
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
	private JPanel getPnDatabases() {
		if (pnDatabases == null) {
			pnDatabases = new DatabaseListPanel(sqliteManager);
		}
		return pnDatabases;
	}
}
