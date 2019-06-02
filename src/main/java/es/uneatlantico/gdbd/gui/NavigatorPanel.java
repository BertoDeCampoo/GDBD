package es.uneatlantico.gdbd.gui;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import es.uneatlantico.gdbd.persistence.SQLiteManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class NavigatorPanel extends JPanel {
	
	private static final long serialVersionUID = -7988855004314830937L;

	private SQLiteManager sqliteManager;
	private TextEditorPanel textEditorPanel;
	
	// Swing components
	private JPanel pnServers, pnServer, pnDatabases, pnTables, pnColumns;
	private DefaultComboBoxModel<String> cModelServers;
	private JComboBox<String> cbServers;
	private JLabel lblServer;
	private JButton btnRefreshservers;
	private JSplitPane spnBrowser, spnDatabasesTables;
	private JScrollPane scrlPnDatabases, scrlPnTables, scrlPnColumns;
	private JTable tbDatabases, tbTables, tbColumns;
	private DefaultTableModel tableModelDatabases, tableModelTables, tableModelColumns;
	
	/**
	 * Create the panel.
	 */
	public NavigatorPanel(SQLiteManager sqliteManager, TextEditorPanel textEditorPanel) {
		this.sqliteManager = sqliteManager;
		this.textEditorPanel = textEditorPanel;
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
		setLayout(new BorderLayout(0, 0));
		this.add(getPnServers(), BorderLayout.NORTH);
		
		add(getSpnBrowser(), BorderLayout.CENTER);
		this.loadServers();
	}
	
	private JPanel getPnServers() {
		if (pnServers == null) {
			pnServers = new JPanel();
			pnServers.setLayout(new GridLayout(2, 1, 0, 0));
			pnServers.add(getLblServer());
			pnServers.add(getPnServer());
		}
		return pnServers;
	}
	private JLabel getLblServer() {
		if (lblServer == null) {
			lblServer = new JLabel("Lista de servidores: ");
			lblServer.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblServer.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return lblServer;
	}
	private JComboBox<String> getCbServers() {
		if (cbServers == null) {
			cModelServers = new DefaultComboBoxModel<String>();
			cbServers = new JComboBox<String>(cModelServers);
			cbServers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadDatabases(getCbServers().getSelectedItem().toString());
				}
			});
		}
		return cbServers;
	}
	
	private JButton getBtnRefreshservers() {
		if (btnRefreshservers == null) {
			btnRefreshservers = new JButton("");
			btnRefreshservers.setIcon(new ImageIcon(NavigatorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
			btnRefreshservers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadServers();
				}
			});
		}
		return btnRefreshservers;
	}
	
	private JPanel getPnServer() {
		if (pnServer == null) {
			pnServer = new JPanel();
			pnServer.setLayout(new BoxLayout(pnServer, BoxLayout.X_AXIS));
			pnServer.add(getCbServers());
			pnServer.add(getBtnRefreshservers());
		}
		return pnServer;
	}
	private JSplitPane getSpnBrowser() {
		if (spnBrowser == null) {
			spnBrowser = new JSplitPane();
			spnBrowser.setDividerLocation(300);
			spnBrowser.setOrientation(JSplitPane.VERTICAL_SPLIT);
			spnBrowser.setOneTouchExpandable(true);
			spnBrowser.setLeftComponent(getSpnDatabasesTables());
			spnBrowser.setRightComponent(getPnColumns());
		}
		return spnBrowser;
	}
	private JSplitPane getSpnDatabasesTables() {
		if (spnDatabasesTables == null) {
			spnDatabasesTables = new JSplitPane();
			spnDatabasesTables.setDividerLocation(150);
			spnDatabasesTables.setOneTouchExpandable(true);
			spnDatabasesTables.setOrientation(JSplitPane.VERTICAL_SPLIT);
			spnDatabasesTables.setLeftComponent(getPnDatabases());
			spnDatabasesTables.setRightComponent(getPnTables());
		}
		return spnDatabasesTables;
	}
	private JPanel getPnDatabases() {
		if (pnDatabases == null) {
			pnDatabases = new JPanel();
			pnDatabases.setLayout(new BorderLayout(0, 0));
			pnDatabases.add(getScrlPnDatabases());
		}
		return pnDatabases;
	}
	private JScrollPane getScrlPnDatabases() {
		if (scrlPnDatabases == null) {
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
			tbDatabases.getTableHeader().setReorderingAllowed(false);
			tbDatabases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbDatabases.setShowVerticalLines(false);
			tbDatabases.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbDatabases.setRowHeight(30);
			tbDatabases.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tbDatabases.setCellSelectionEnabled(true);
			tbDatabases.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Obtains the ID of the database selected from the table
					int databaseID = (int) tableModelDatabases.getValueAt(tbDatabases.convertRowIndexToModel(tbDatabases.getSelectedRow()), 0);
					
					NavigatorPanel.this.loadTables(databaseID);
					System.err.println("ID de la BBDD seleccionada: " + databaseID);
					textEditorPanel.editDatabase(databaseID);
				}
			});
		}
		return tbDatabases;
	}
	private JPanel getPnTables() {
		if (pnTables == null) {
			pnTables = new JPanel();
			pnTables.setLayout(new BorderLayout(0, 0));
			pnTables.add(getScrlPnTables());
		}
		return pnTables;
	}
	private JScrollPane getScrlPnTables() {
		if (scrlPnTables == null) {
			scrlPnTables = new JScrollPane(getTbTables());
		}
		return scrlPnTables;
	}
	public JTable getTbTables()
	{
		if(tbTables == null)
		{
			tbTables = new JTable(tableModelTables);
			tbTables.getTableHeader().setReorderingAllowed(false);
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
					int tableID = (int) tableModelTables.getValueAt(tbTables.convertRowIndexToModel(tbTables.getSelectedRow()), 0);
					System.out.println("id del modelo: " + tableID);
					
					// Clear columns
			 		DefaultTableModel dm = (DefaultTableModel)getTbColumns().getModel();
			 		dm.getDataVector().removeAllElements();
			 		dm.fireTableDataChanged();
					NavigatorPanel.this.loadColumns(tableID);
					System.err.println("ID de la tabla seleccionada: " + tableID);
					textEditorPanel.editTable(tableID);
				}
			});
		}
		return tbTables;
	}
	private JPanel getPnColumns() {
		if (pnColumns == null) {
			pnColumns = new JPanel();
			pnColumns.setLayout(new BorderLayout(0, 0));
			pnColumns.add(getScrlPnColumns());
		}
		return pnColumns;
	}
	private JScrollPane getScrlPnColumns() {
		if (scrlPnColumns == null) {
			scrlPnColumns = new JScrollPane(getTbColumns());
		}
		return scrlPnColumns;
	}
	private JTable getTbColumns() {
		if (tbColumns == null) {
			tbColumns = new JTable();
			tbColumns.getTableHeader().setReorderingAllowed(false);
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
					int rowID = (int) tableModelColumns.getValueAt(tbColumns.convertRowIndexToModel(tbColumns.getSelectedRow()), 0);
					
					System.err.println("ID de la columna seleccionada: " + rowID);
					textEditorPanel.editColumn(rowID);
				}
			});
		}
		return tbColumns;
	}
	
	///////////////////// LOGICA

	private void loadServers()
	{
		new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	// Loads the servers on the combo box of servers
            	NavigatorPanel.this.getBtnRefreshservers().setEnabled(false);
            	cModelServers = new DefaultComboBoxModel<String>(sqliteManager.getServers().toArray(new String[0]));
            	cbServers.setModel(cModelServers);
    			cbServers.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXX");
            	
            	NavigatorPanel.this.getBtnRefreshservers().setEnabled(true);
            	NavigatorPanel.this.loadDatabases(getCbServers().getSelectedItem().toString());
                return null;
            }
        }.execute();
	}
	
	private void loadDatabases(String server)
	{
		new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	tableModelDatabases = sqliteManager.getDatabases(server);
				tbDatabases.setModel(tableModelDatabases);
				tbDatabases.removeColumn(tbDatabases.getColumnModel().getColumn(0));
            	
            	return null;
            }
        }.execute();
        // Clear tables 
 		DefaultTableModel dm = (DefaultTableModel)getTbTables().getModel();
 		dm.getDataVector().removeAllElements();
 		dm.fireTableDataChanged();
 		// Clear columns
 		dm = (DefaultTableModel)getTbColumns().getModel();
 		dm.getDataVector().removeAllElements();
 		dm.fireTableDataChanged();
	}
	
	private void loadTables(int databaseID) 
	{
		new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	// Clear columns
		 		DefaultTableModel dm = (DefaultTableModel)getTbColumns().getModel();
		 		dm.getDataVector().removeAllElements();
		 		dm.fireTableDataChanged();
            	tableModelTables = sqliteManager.getTables(databaseID);
            	tbTables.setModel(tableModelTables);
        		tbTables.removeColumn(tbTables.getColumnModel().getColumn(0));
            	
            	return null;
            }
        }.execute();
	}
	
	private void loadColumns(int tableID)
	{
		new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	tableModelColumns = sqliteManager.getColumns(tableID);
            	tbColumns.setModel(tableModelColumns);
    			tbColumns.removeColumn(tbColumns.getColumnModel().getColumn(0));
            	
            	return null;
            }
        }.execute();
	}
}
