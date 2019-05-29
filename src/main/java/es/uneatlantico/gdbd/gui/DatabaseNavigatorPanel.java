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
import java.sql.Statement;
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
    private DefaultTableModel tableModelDatabases, tableModelTables, tableModelColumns;
    private JScrollPane scrlPnDatabases, scrlPnTables, scrlPnColumns;
    private JPanel pnServers;
    private JLabel lblServer;
    private JComboBox<String> cbServers;
    private JButton btnRefreshservers;
    private JPanel pnServer;
    private SQLiteManager sqliteManager;
    private DefaultComboBoxModel<String> cModel;
    private JPanel pnDatabases;
    private JPanel pnTables;
    private JSplitPane spnDatabasesTables, spnBrowser;
    private JPanel pnColumns;
    private JTable tbColumns;
    

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
       // Initializes the table model to make the database list not editable
      	tableModelTables = new DefaultTableModel(){
  			private static final long serialVersionUID = 1L;

  			public boolean isCellEditable(int row, int column)
              {
                return false;//This causes all cells to be not editable
              }
            };
         // Initializes the table model to make the database list not editable
        	tableModelColumns = new DefaultTableModel(){
    			private static final long serialVersionUID = 1L;

    			public boolean isCellEditable(int row, int column)
                {
                  return false;//This causes all cells to be not editable
                }
              };

          add(getSplitPaneBrowser(), BorderLayout.CENTER);
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
					//int databaseID = ((int)tableModelDatabases.getValueAt(row, 0));
					int databaseID = (int) tableModelDatabases.getValueAt(tbDatabases.convertRowIndexToModel(tbDatabases.getSelectedRow()), 0);
					
					DatabaseNavigatorPanel.this.loadTables(databaseID);
					System.err.println("ID de la BBDD seleccionada: " + databaseID);
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
		new DatabaseLoaderWorker().execute();   
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
			tbTables.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Obtains the ID of the database selected from the table
					int row = tbTables.convertRowIndexToModel(tbTables.getSelectedRow());
					//int tableID = ((int)tableModelTables.getValueAt(row, 0));
					int tableID = (int) tableModelTables.getValueAt(tbTables.convertRowIndexToModel(tbTables.getSelectedRow()), 0);
					System.out.println("id del modelo: " + tableID);
					
					DatabaseNavigatorPanel.this.loadColumns(tableID);
					System.err.println("ID de la tabla seleccionada: " + tableID);
				}
			});
		}
		return tbTables;
	}

	protected class ColumnLoaderWorker extends SwingWorker<Void, Void>
	{
		private int tableID;
		
		public ColumnLoaderWorker(int tableID)
		{
			this.tableID = tableID;
		}
		@Override
		protected Void doInBackground() throws Exception {
			logger.log(Level.DEBUG, "Cargando columnas");
			Connection connection;

			try {
				connection = sqliteManager.getConnection();
				Statement stmt = connection.createStatement();
				String sqlQuery = "SELECT ID, NOMBRE AS 'Nombre de la columna' FROM COLUMNAS WHERE ID_TABLA = " + this.tableID;
				logger.log(Level.DEBUG, sqlQuery);

				ResultSet rs = stmt.executeQuery(sqlQuery);

				// It creates and displays the table
				tableModelColumns = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
				tbColumns.setModel(tableModelColumns);
				tbColumns.removeColumn(tbColumns.getColumnModel().getColumn(0));
				rs.close();
				stmt.close();
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getLocalizedMessage());
			}
			logger.log(Level.DEBUG, "Columna de bases de datos cargada");
			return null;
		}
	}
	
	private void loadColumns(int tableID) {
		new ColumnLoaderWorker(tableID).execute();
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
	
	
	
	private void loadTables(int databaseID) {
		new TableLoaderWorker(databaseID).execute();    	
    }
	
	private JSplitPane getSplitPaneBrowser() {
		if (spnBrowser == null) {
			spnBrowser = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getSplitPaneDatabaseTables(), getPnColumns());
			spnBrowser.setDividerLocation(360);
		}
		return spnBrowser;
	}
	
	private JSplitPane getSplitPaneDatabaseTables() {
		if (spnDatabasesTables == null) {
			spnDatabasesTables = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getPnDatabases(), getPnTables());
			spnDatabasesTables.setDividerLocation(150);
		}
		return spnDatabasesTables;
	}
	
	private JScrollPane getScrlPnColumns()
    {
    	if (scrlPnColumns == null)
    	{
    		scrlPnColumns = new JScrollPane(getTbColumns());
    		scrlPnColumns.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	}
    	return scrlPnColumns;
    }
	
	private JPanel getPnColumns() {
		if (pnColumns == null) {
			pnColumns = new JPanel();
			pnColumns.setLayout(new BorderLayout(0, 0));
			pnColumns.add(getScrlPnColumns());
		}
		return pnColumns;
	}
	private JTable getTbColumns() {
		if (tbColumns == null) {
			tbColumns = new JTable();
			tbColumns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbColumns.setShowVerticalLines(false);
			tbColumns.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbColumns.setRowHeight(30);
			tbColumns.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbColumns.setCellSelectionEnabled(true);
			tbColumns.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Obtains the ID of the column selected from the table
					int row = tbColumns.convertRowIndexToModel(tbColumns.getSelectedRow());
					//int columnID = ((int)tableModelColumns.getValueAt(row, 0));
					int rowID = (int) tableModelColumns.getValueAt(tbColumns.convertRowIndexToModel(tbColumns.getSelectedRow()), 0);
					
					System.err.println("ID de la columna seleccionada: " + rowID);
				}
			});
		}
		return tbColumns;
	}
	
	protected class DatabaseLoaderWorker extends SwingWorker<Void, Void>
	{		
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
				
				// It creates and displays the table
				tableModelDatabases = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
				tbDatabases.setModel(tableModelDatabases);
				tbDatabases.removeColumn(tbDatabases.getColumnModel().getColumn(0));
				rs.close();
				stmt.close();
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getLocalizedMessage());
			}
			logger.log(Level.DEBUG, "Tabla de bases de datos cargada (DatabaseListPanel)");
			return null;
		}
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
				tableModelTables = es.uneatlantico.gdbd.util.BuildTableModel.buildTableModel(rs);
				tbTables.setModel(tableModelTables);
				tbTables.removeColumn(tbTables.getColumnModel().getColumn(0));
				rs.close();
				stmt.close();
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getLocalizedMessage());
			}
			logger.log(Level.DEBUG, "Tabla de bases de datos cargada (DatabaseListPanel)");
			return null;
		}
		
	}
}
