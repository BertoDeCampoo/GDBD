package es.uneatlantico.gdbd.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import es.uneatlantico.gdbd.persistence.SQLiteManager;
import es.uneatlantico.gdbd.util.UITuner;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Application {

	private SQLiteManager sqliteManager;
	private JFrame frmGdbd;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnDocumentation;
	private JMenu mnHelp;
	private JMenuItem mntmNewDatabase;
	private NavigatorPanel pnNavigator;
	private TextEditorPanel pnEditor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UITuner.fixBrokenIcons();
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
		frmGdbd.setIconImage(Toolkit.getDefaultToolkit().getImage(Application.class.getResource("/database-search-icon.png")));
		frmGdbd.setMinimumSize(new Dimension(480, 280));
		frmGdbd.setTitle("GDBD - Gestor de Documentación para Bases de Datos");
		frmGdbd.setBounds(100, 100, 786, 925);
		frmGdbd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGdbd.setJMenuBar(getMenuBar());
		frmGdbd.getContentPane().setLayout(new BorderLayout(0, 0));
		
		frmGdbd.getContentPane().add(getPnNavigator(), BorderLayout.WEST);
		frmGdbd.getContentPane().add(getPnEditor(), BorderLayout.CENTER);
		frmGdbd.pack();
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
					LoginDialog databaseLogin = new LoginDialog(frmGdbd, Application.this.sqliteManager);
					databaseLogin.setVisible(true);
				}
			});
		}
		return mntmNewDatabase;
	}
	
	private NavigatorPanel getPnNavigator() {
		if (pnNavigator == null) {
			pnNavigator = new NavigatorPanel(sqliteManager);
//			pnNavigator.setDividerLocation(150);
		}
		return pnNavigator;
	}
	private TextEditorPanel getPnEditor() {
		if (pnEditor == null) {
			pnEditor = new TextEditorPanel();
		}
		return pnEditor;
	}
}
