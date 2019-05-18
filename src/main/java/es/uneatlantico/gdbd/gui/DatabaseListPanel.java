package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

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

public class DatabaseListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388230314818313907L;
	private JLabel lblListOfDatabases;
	private JButton btnLoadData;
    private JTable table;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JPanel panel;
    private JScrollPane scrollPane;
    private void initGUI() {
		add(getLblListOfDatabases(), BorderLayout.NORTH);
        add(getScrollPane(), BorderLayout.CENTER);
        add(getPnSouth(), BorderLayout.SOUTH);    
	}

    public DatabaseListPanel() throws HeadlessException {
        setLayout(new BorderLayout(0, 0));
        initGUI();
    }
    
    public JScrollPane getScrollPane()
    {
    	if (scrollPane == null)
    	{
    		scrollPane = new JScrollPane(getTable());
    	}
    	return scrollPane;
    }
	
	public JTable getTable()
	{
		if(table == null)
		{
			table = new JTable(tableModel);
		}
		return table;
	}
	
	private JPanel getPnSouth()
	{
		if(panel == null)
		{
			panel = new JPanel();
			panel.add(getBtnLoadData());
		}
		return panel;
	}
	
	private JButton getBtnLoadData()
	{
		if (btnLoadData == null) {
			btnLoadData = new JButton("Load data");
			btnLoadData.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new SwingWorker<Void, Void>() {
	                    @Override
	                    protected Void doInBackground() throws Exception {
	                        loadData();
	                        return null;
	                    }
	                }.execute();
	            }
	        });
		}
		return btnLoadData;
	}

	private JLabel getLblListOfDatabases() {
		if (lblListOfDatabases == null) {
			lblListOfDatabases = new JLabel("List of databases:");
		}
		return lblListOfDatabases;
	}

    private void loadData() {
    	System.out.println("START loadData method");
       // LOG.info("START loadData method");

        btnLoadData.setEnabled(false);
        SQLiteManager mgr = new SQLiteManager(SQLiteManager.Default_Filename);
        Connection connection;
        
        try {
        	connection = mgr.getConnection();
        
    		Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from 'BBDD'");
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
        	System.err.println(e.getLocalizedMessage());
            //LOG.log(Level.SEVERE, "Exception in Load Data", e);
        }
        btnLoadData.setEnabled(true);
        System.out.println("Fin de la carga de datos");
        //LOG.info("END loadData method");
    }
}
