package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
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
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DatabaseNavigatorPanel extends JPanel {
	private static final Logger logger = LogManager.getLogger(MySQLDatabase.class); 

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388230314818313907L;
    private JTable tbDatabases, tbTables;
    private DefaultTableModel tableModelDatabases, tableModelTables;
    private JScrollPane scrlPnDatabases, scrlPnTables;
    private JPanel pnServers;
    private JLabel lblServer;
    private JComboBox<String> cbServers;
    private JButton btnRefreshservers;
    private JPanel pnServer;
    private SQLiteManager sqliteManager;
    private DefaultComboBoxModel<String> cModel;
    private JPanel pnDatabases;
    private JPanel pnTables;
    private JSplitPane spnDatabasesTables;
    

    public DatabaseNavigatorPanel(SQLiteManager sqlite) throws HeadlessException {
    	this.sqliteManager = sqlite;
        setLayout(new BorderLayout(0, 0));
        initGUI();
    }
    
    private void initGUI() {
    	// Initializes the table model to make the database list not editable
    	tableModelDatabases = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
            {
              return false;//This causes all cells to be not editable
            }
          };

      	add(getSplitPaneDatabaseTables(), BorderLayout.CENTER);
    	this.loadServers();    	
	}
    
    public void loadServers()
    {	
    	new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	// Loads the servers on the combo box of servers
            	DatabaseNavigatorPanel.this.getBtnRefreshservers().setEnabled(false);
            	cModel = new DefaultComboBoxModel<String>(sqliteManager.getServers().toArray(new String[0]));
            	cbServers.setModel(cModel);
            	DatabaseNavigatorPanel.this.getBtnRefreshservers().setEnabled(true);
                DatabaseNavigatorPanel.this.loadDatabases();
                return null;
            }
        }.execute();
    }
    
    public JScrollPane getScrlPnDatabases()
    {
    	if (scrlPnDatabases == null)
    	{
    		scrlPnDatabases = new JScrollPane(getTbDatabases());
    		scrlPnDatabases.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	}
    	return scrlPnDatabases;
    }
	
	public JTable getTbDatabases()
	{
		if(tbDatabases == null)
		{
			tbDatabases = new JTable(tableModelDatabases);
			tbDatabases.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Obtains the ID of the database selected from the table
					int row = tbDatabases.convertRowIndexToModel(tbDatabases.getSelectedRow());
					int databaseID = ((int)tableModelDatabases.getValueAt(row, 0));
					
					DatabaseNavigatorPanel.this.loadTables(databaseID);
					System.err.println(databaseID);
				}
			});
			tbDatabases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbDatabases.setShowVerticalLines(false);
			tbDatabases.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbDatabases.setRowHeight(30);
			tbDatabases.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbDatabases.setCellSelectionEnabled(true);
		}
		return tbDatabases;
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
					tableModelDatabases.setDataVector(data, columnNames);
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
			btnRefreshservers.setIcon(new ImageIcon(DatabaseNavigatorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
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
	private JPanel getPnDatabases() {
		if (pnDatabases == null) {
			pnDatabases = new JPanel();
			pnDatabases.setLayout(new BorderLayout(0, 0));
			pnDatabases.add(getPnServers(), BorderLayout.NORTH);
			pnDatabases.add(getScrlPnDatabases(), BorderLayout.CENTER);
		}
		return pnDatabases;
	}
	private JPanel getPnTables() {
		if (pnTables == null) {
			pnTables = new JPanel();
			pnTables.setLayout(new BorderLayout(0, 0));
			pnTables.add(getScrlPnTables(), BorderLayout.CENTER);
		}
		return pnTables;
	}
	
	public JTable getTbTables()
	{
		if(tbTables == null)
		{
			tbTables = new JTable(tableModelTables);
			tbTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbTables.setShowVerticalLines(false);
			tbTables.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbTables.setRowHeight(30);
			tbTables.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbTables.setCellSelectionEnabled(true);
		}
		return tbTables;
	}
	
	public JScrollPane getScrlPnTables()
    {
    	if (scrlPnTables == null)
    	{
    		scrlPnTables = new JScrollPane(getTbTables());
    		scrlPnTables.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	}
    	return scrlPnTables;
    }
	
	protected class TableLoaderWorker extends SwingWorker<Void, Void>
	{
		private int databaseID;
		
		public TableLoaderWorker(int databaseID)
		{
			this.databaseID = databaseID;
		}
		@Override
		protected Void doInBackground() throws Exception {
			logger.log(Level.DEBUG, "Cargando tablas");
			Connection connection;

			try {
				connection = sqliteManager.getConnection();
				Statement stmt = connection.createStatement();
				String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la tabla' FROM TABLAS WHERE ID_BBDD = " + this.databaseID;
				logger.log(Level.DEBUG, sqlQuery);

				ResultSet rs = stmt.executeQuery(sqlQuery);

				// It creates and displays the table
				tbTables.setModel(es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs));
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getLocalizedMessage());
			}
			logger.log(Level.DEBUG, "Tabla de bases de datos cargada (DatabaseListPanel)");
			return null;
		}
//		@Override
//		protected Void doInBackground() throws Exception {
//			logger.log(Level.DEBUG, "Cargando tablas");
//			Connection connection;
//
//			try {
//				connection = sqliteManager.getConnection();
//
//				Statement stmt = connection.createStatement();
//				String sqlQuery = "SELECT ID, NOMBRE FROM TABLAS WHERE ID_BBDD =" + this.databaseID;
//				logger.log(Level.DEBUG, sqlQuery);
//
//				ResultSet rs = stmt.executeQuery(sqlQuery);
//				ResultSetMetaData metaData = rs.getMetaData();
//
//				// Names of columns
//				Vector<String> columnNames = new Vector<String>();
//				int columnCount = metaData.getColumnCount();
//				for (int i = 1; i <= columnCount; i++) {
//					System.err.println(metaData.getColumnName(i));
//					columnNames.add(metaData.getColumnName(i));
//				}
//
//				// Data of the table
//				Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//				while (rs.next()) {
//					
//					Vector<Object> vector = new Vector<Object>();
//					for (int i = 1; i <= columnCount; i++) {
//						System.err.println(rs.getObject(i));
//						vector.add(rs.getObject(i));
//					}
//					data.add(vector);
//				}
//				tableModelTables.setDataVector(data, columnNames);
//			} catch (Exception e) {
//				logger.log(Level.ERROR, e.getLocalizedMessage());
//			}
//			logger.log(Level.DEBUG, "Tabla de bases de datos cargada (DatabaseListPanel)");
//			return null;
//		}
	}
	
	private void loadTables(int databaseID) {
		new TableLoaderWorker(databaseID).execute();    	
    }
	
	private JSplitPane getSplitPaneDatabaseTables() {
		if (spnDatabasesTables == null) {
			spnDatabasesTables = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getPnDatabases(), getPnTables());
			spnDatabasesTables.setDividerLocation(150);
		}
		return spnDatabasesTables;
	}
}
