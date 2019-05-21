package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.uneatlantico.gdbd.dataaccess.MySQLDatabase;
import es.uneatlantico.gdbd.persistence.SQLiteManager;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridLayout;
import javax.swing.ImageIcon;

public class DatabaseListPanel extends JPanel {
	private static final Logger logger = LogManager.getLogger(MySQLDatabase.class); 

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388230314818313907L;
    private JTable tbDatabases;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JPanel panel;
    private JScrollPane scrlPnDatabases;
    private JPanel pnServers;
    private JLabel lblServer;
    private JComboBox<String> cbServers;
    private JButton btnRefreshservers;
    private JPanel pnServer;
    private SQLiteManager sqliteManager;
    private DefaultComboBoxModel<String> cModel;
    

    public DatabaseListPanel(SQLiteManager sqlite) throws HeadlessException {
    	this.sqliteManager = sqlite;
        setLayout(new BorderLayout(0, 0));
        initGUI();
    }
    
    private void initGUI() {
        add(getScrlPnDatabases(), BorderLayout.CENTER);
        add(getPnSouth(), BorderLayout.SOUTH);    
    	add(getPnServers(), BorderLayout.NORTH);
    	this.loadServers();    	
	}
    
    public void loadServers()
    {	
    	new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	// Loads the servers on the combo box of servers
            	DatabaseListPanel.this.getBtnRefreshservers().setEnabled(false);
            	cModel = new DefaultComboBoxModel<String>(sqliteManager.getServers().toArray(new String[0]));
            	cbServers.setModel(cModel);
            	DatabaseListPanel.this.getBtnRefreshservers().setEnabled(true);
                DatabaseListPanel.this.loadDatabases();
                return null;
            }
        }.execute();
    }
    
    public JScrollPane getScrlPnDatabases()
    {
    	if (scrlPnDatabases == null)
    	{
    		scrlPnDatabases = new JScrollPane(getTbDatabases());
    	}
    	return scrlPnDatabases;
    }
	
	public JTable getTbDatabases()
	{
		if(tbDatabases == null)
		{
			tbDatabases = new JTable(tableModel);
			tbDatabases.setEnabled(false);
		}
		return tbDatabases;
	}
	
	private JPanel getPnSouth()
	{
		if(panel == null)
		{
			panel = new JPanel();
		}
		return panel;
	}

	private void loadDatabases() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				logger.log(Level.DEBUG, "Cargando tabla de bases de datos (DatabaseListPanel)");
				Connection connection;

				try {
					connection = sqliteManager.getConnection();

					Statement stmt = connection.createStatement();
					String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la base de datos' FROM 'BBDD' WHERE SERVIDOR = '" + getCbServers().getSelectedItem().toString() + "'";
					logger.log(Level.DEBUG, sqlQuery);

					ResultSet rs = stmt.executeQuery(sqlQuery);
					ResultSetMetaData metaData = rs.getMetaData();

					// Names of columns
					Vector<String> columnNames = new Vector<String>();
					int columnCount = metaData.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						columnNames.add(metaData.getColumnName(i));
					}

					// Data of the table
					Vector<Vector<Object>> data = new Vector<Vector<Object>>();
					while (rs.next()) {
						Vector<Object> vector = new Vector<Object>();
						for (int i = 1; i <= columnCount; i++) {
							vector.add(rs.getObject(i));
						}
						data.add(vector);
					}
					tableModel.setDataVector(data, columnNames);
				} catch (Exception e) {
					logger.log(Level.ERROR, e.getLocalizedMessage());
				}
				logger.log(Level.DEBUG, "Tabla de bases de datos cargada (DatabaseListPanel)");
				return null;
			}
		}.execute();
        
    	
    }
	private JPanel getPnServers() {
		if (pnServers == null) {
			pnServers = new JPanel();
			pnServers.setLayout(new BoxLayout(pnServers, BoxLayout.X_AXIS));
			pnServers.add(getPnServer());
			pnServers.add(getBtnRefreshservers());
		}
		return pnServers;
	}
	private JLabel getLblServer() {
		if (lblServer == null) {
			lblServer = new JLabel("Seleccione un servidor:");
		}
		return lblServer;
	}
	private JComboBox<String> getCbServers() {
		if (cbServers == null) {
			cModel = new DefaultComboBoxModel<String>();
			cbServers = new JComboBox<String>(cModel);
			cbServers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					loadDatabases();
				}
			});
		}
		return cbServers;
	}
	private JButton getBtnRefreshservers() {
		if (btnRefreshservers == null) {
			btnRefreshservers = new JButton("");
			btnRefreshservers.setIcon(new ImageIcon(DatabaseListPanel.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
			btnRefreshservers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					loadServers();
				}
			});
		}
		return btnRefreshservers;
	}
	private JPanel getPnServer() {
		if (pnServer == null) {
			pnServer = new JPanel();
			pnServer.setLayout(new GridLayout(0, 2, 0, 0));
			pnServer.add(getLblServer());
			pnServer.add(getCbServers());
		}
		return pnServer;
	}
}
