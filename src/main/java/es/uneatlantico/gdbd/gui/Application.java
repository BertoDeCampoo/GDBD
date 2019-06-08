package es.uneatlantico.gdbd.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

import es.uneatlantico.gdbd.persistence.SQLiteManager;
import es.uneatlantico.gdbd.util.UITuner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

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
	private JMenuItem mntmExportarAArchivo;
	private JMenuItem mntmAcercaDe;
	private JMenuItem mntmExit;
	private JMenuItem mntmRemoveDatabase;

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
		frmGdbd.getContentPane().setPreferredSize(new Dimension(820, 380));
		frmGdbd.setSize(new Dimension(720, 500));
		frmGdbd.setIconImage(Toolkit.getDefaultToolkit().getImage(Application.class.getResource("/database-search-icon.png")));
		frmGdbd.setMinimumSize(new Dimension(720, 500));
		frmGdbd.setTitle("GDBD - Gestor de Documentación para Bases de Datos");
		frmGdbd.setBounds(100, 100, 480, 360);
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
			mnFile.add(getMntmExit());
		}
		return mnFile;
	}
	private JMenu getMnDocumentation() {
		if (mnDocumentation == null) {
			mnDocumentation = new JMenu("Documentaci\u00F3n");
			mnDocumentation.add(getMntmNewDatabase());
			mnDocumentation.add(getMntmRemoveDatabase());
			mnDocumentation.add(getMntmExportarAArchivo());
		}
		return mnDocumentation;
	}
	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Ayuda");
			mnHelp.add(getMntmAcercaDe());
		}
		return mnHelp;
	}
	private JMenuItem getMntmNewDatabase() {
		if (mntmNewDatabase == null) {
			mntmNewDatabase = new JMenuItem("A\u00F1adir base de datos...");
			mntmNewDatabase.setIcon(new ImageIcon(Application.class.getResource("/com/sun/javafx/scene/web/skin/IncreaseIndent_16x16_JFX.png")));
			mntmNewDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					LoginDialog databaseLogin = new LoginDialog(Application.this, Application.this.sqliteManager);
					databaseLogin.setVisible(true);
				}
			});
		}
		return mntmNewDatabase;
	}
	
	private JMenuItem getMntmExportarAArchivo() {
		if (mntmExportarAArchivo == null) {
			mntmExportarAArchivo = new JMenuItem("Exportar a archivo...");
			mntmExportarAArchivo.setIcon(new ImageIcon(Application.class.getResource("/net/sf/jasperreports/view/images/print.GIF")));
			mntmExportarAArchivo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					ExportReportDialog exportDialog = new ExportReportDialog(Application.this.sqliteManager);
					exportDialog.setVisible(true);
				}
			});
		}
		return mntmExportarAArchivo;
	}
	
	private NavigatorPanel getPnNavigator() {
		if (pnNavigator == null) {
			pnNavigator = new NavigatorPanel(sqliteManager, getPnEditor());
		}
		return pnNavigator;
	}
	private TextEditorPanel getPnEditor() {
		if (pnEditor == null) {
			pnEditor = new TextEditorPanel(sqliteManager);
		}
		return pnEditor;
	}
	private JMenuItem getMntmAcercaDe() {
		if (mntmAcercaDe == null) {
			mntmAcercaDe = new JMenuItem("Acerca de");
			mntmAcercaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Load "About" dialog
					About about = new About();
					about.setVisible(true);
				}
			});
		}
		return mntmAcercaDe;
	}
	
	public void triggerRefreshNavigator()
	{
		this.getPnNavigator().reload();
	}
	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Salir");
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
		return mntmExit;
	}
	private JMenuItem getMntmRemoveDatabase() {
		if (mntmRemoveDatabase == null) {
			mntmRemoveDatabase = new JMenuItem("Eliminar base de datos");
			mntmRemoveDatabase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						int selectedDatabaseID = Application.this.getPnNavigator().getSelectedDatabase();
						if (selectedDatabaseID == 0)
						{
							JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna base de datos", "No se puede eliminar la base de datos", JOptionPane.ERROR_MESSAGE);
							return;
						}
						String selectedDatabaseName = Application.this.sqliteManager.getDatabaseName(selectedDatabaseID);
					
						int response = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la base de datos '" + selectedDatabaseName + "' (De la aplicación)?", 
								"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
						if (response == JOptionPane.YES_OPTION)
							sqliteManager.deleteDatabase(selectedDatabaseID);
						Application.this.triggerRefreshNavigator();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), "No se puede eliminar la base de datos", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			mntmRemoveDatabase.setIcon(new ImageIcon(Application.class.getResource("/com/sun/javafx/scene/web/skin/DecreaseIndent_16x16_JFX.png")));
		}
		return mntmRemoveDatabase;
	}
}
