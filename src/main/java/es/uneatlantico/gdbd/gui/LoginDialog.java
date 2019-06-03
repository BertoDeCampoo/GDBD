package es.uneatlantico.gdbd.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import es.uneatlantico.gdbd.dataaccess.DBMS;
import es.uneatlantico.gdbd.dataaccess.FactoryDatabaseAccess;
import es.uneatlantico.gdbd.dataaccess.IDatabase;
import es.uneatlantico.gdbd.entities.Database;
import es.uneatlantico.gdbd.persistence.SQLiteManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 403555622808592584L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtServer;
	private JTextField txtUser;
	private JPasswordField passwordField;
	private JButton okButton;
	private JComboBox<DBMS> cbDriver;
	private JTextField txtPort;
	private JComboBox<String> cbDatabases;
	private JLabel lblTestResults;
	private JButton btnTest;
	private Application parentApplication;
	
	private IDatabase selectedDatabase;
	private SQLiteManager sqliteManager;
	private Connection connection;
	
	private ConnectionTask task;

	/**
	 * Create the dialog.
	 */
	public LoginDialog(Application owner, SQLiteManager sqliteManager) {
		this.parentApplication = owner;
		this.sqliteManager = sqliteManager;
		initGUI();
	}
	private void initGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginDialog.class.getResource("/database-add-icon.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("A\u00F1adir base de datos");
		setBounds(100, 100, 450, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel pnInformation = new JPanel();
			contentPanel.add(pnInformation, BorderLayout.NORTH);
			pnInformation.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblInformation = new JLabel("Asistente de conexi\u00F3n a base de datos");
				lblInformation.setFont(new Font("Tahoma", Font.BOLD, 13));
				pnInformation.add(lblInformation);
			}
			{
				JLabel lblEspecifiqueLosParmetros = new JLabel("Especifique los par\u00E1metros de conexi\u00F3n");
				pnInformation.add(lblEspecifiqueLosParmetros);
			}
		}
		{
			JPanel pnConfiguration = new JPanel();
			pnConfiguration.setBorder(new EmptyBorder(10, 0, 0, 0));
			contentPanel.add(pnConfiguration, BorderLayout.CENTER);
			GridBagLayout gbl_pnConfiguration = new GridBagLayout();
			gbl_pnConfiguration.columnWidths = new int[]{98, 128, 0};
			gbl_pnConfiguration.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_pnConfiguration.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gbl_pnConfiguration.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			pnConfiguration.setLayout(gbl_pnConfiguration);
			{
				DefaultComboBoxModel<DBMS> cModel = new DefaultComboBoxModel<DBMS>(
				          DBMS.values());
				{
					JLabel lblDriver = new JLabel("Driver:");
					GridBagConstraints gbc_lblDriver = new GridBagConstraints();
					gbc_lblDriver.fill = GridBagConstraints.BOTH;
					gbc_lblDriver.insets = new Insets(0, 0, 5, 5);
					gbc_lblDriver.gridx = 0;
					gbc_lblDriver.gridy = 0;
					pnConfiguration.add(lblDriver, gbc_lblDriver);
				}
				cbDriver = new JComboBox<DBMS>(cModel);
				cbDriver.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						okButton.setEnabled(false);
					}
				});
				cbDriver.setToolTipText("Tipo de driver a utilizar");
				GridBagConstraints gbc_cbDriver = new GridBagConstraints();
				gbc_cbDriver.insets = new Insets(0, 0, 5, 0);
				gbc_cbDriver.fill = GridBagConstraints.HORIZONTAL;
				gbc_cbDriver.gridx = 1;
				gbc_cbDriver.gridy = 0;
				pnConfiguration.add(cbDriver, gbc_cbDriver);
			}
			{
				JLabel lblServer = new JLabel("Servidor:");
				GridBagConstraints gbc_lblServer = new GridBagConstraints();
				gbc_lblServer.fill = GridBagConstraints.BOTH;
				gbc_lblServer.insets = new Insets(0, 0, 5, 5);
				gbc_lblServer.gridx = 0;
				gbc_lblServer.gridy = 1;
				pnConfiguration.add(lblServer, gbc_lblServer);
			}
			{
				txtServer = new JTextField();
				txtServer.setToolTipText("Direcci\u00F3n del servidor de base de datos");
				GridBagConstraints gbc_txtServer = new GridBagConstraints();
				gbc_txtServer.insets = new Insets(0, 0, 5, 0);
				gbc_txtServer.fill = GridBagConstraints.BOTH;
				gbc_txtServer.gridx = 1;
				gbc_txtServer.gridy = 1;
				pnConfiguration.add(txtServer, gbc_txtServer);
				txtServer.setColumns(10);
			}
			{
				JLabel lblPort = new JLabel("Puerto:");
				GridBagConstraints gbc_lblPort = new GridBagConstraints();
				gbc_lblPort.fill = GridBagConstraints.BOTH;
				gbc_lblPort.insets = new Insets(0, 0, 5, 5);
				gbc_lblPort.gridx = 0;
				gbc_lblPort.gridy = 2;
				pnConfiguration.add(lblPort, gbc_lblPort);
			}
			{
				txtPort = new JTextField();
				txtPort.setToolTipText("Puerto a utilizar para realizar la conexi\u00F3n (Por defecto, el puerto predeterminado)");
				GridBagConstraints gbc_txtPort = new GridBagConstraints();
				gbc_txtPort.insets = new Insets(0, 0, 5, 0);
				gbc_txtPort.fill = GridBagConstraints.BOTH;
				gbc_txtPort.gridx = 1;
				gbc_txtPort.gridy = 2;
				pnConfiguration.add(txtPort, gbc_txtPort);
				txtPort.setColumns(10);
			}
			{
				JLabel lblUser = new JLabel("Usuario:");
				GridBagConstraints gbc_lblUser = new GridBagConstraints();
				gbc_lblUser.fill = GridBagConstraints.BOTH;
				gbc_lblUser.insets = new Insets(0, 0, 5, 5);
				gbc_lblUser.gridx = 0;
				gbc_lblUser.gridy = 3;
				pnConfiguration.add(lblUser, gbc_lblUser);
			}
			{
				txtUser = new JTextField();
				txtUser.setToolTipText("Usuario de la base de datos");
				GridBagConstraints gbc_txtUser = new GridBagConstraints();
				gbc_txtUser.insets = new Insets(0, 0, 5, 0);
				gbc_txtUser.fill = GridBagConstraints.BOTH;
				gbc_txtUser.gridx = 1;
				gbc_txtUser.gridy = 3;
				pnConfiguration.add(txtUser, gbc_txtUser);
				txtUser.setColumns(10);
			}
			{
				JLabel lblPassword = new JLabel("Contrase\u00F1a:");
				GridBagConstraints gbc_lblPassword = new GridBagConstraints();
				gbc_lblPassword.fill = GridBagConstraints.BOTH;
				gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblPassword.gridx = 0;
				gbc_lblPassword.gridy = 4;
				pnConfiguration.add(lblPassword, gbc_lblPassword);
			}
			{
				passwordField = new JPasswordField();
				passwordField.setToolTipText("Contrase\u00F1a del usuario de la base de datos");
				GridBagConstraints gbc_passwordField = new GridBagConstraints();
				gbc_passwordField.insets = new Insets(0, 0, 5, 0);
				gbc_passwordField.fill = GridBagConstraints.BOTH;
				gbc_passwordField.gridx = 1;
				gbc_passwordField.gridy = 4;
				pnConfiguration.add(passwordField, gbc_passwordField);
			}
			{
				btnTest = new JButton("Probar conexi\u00F3n");
				btnTest.setToolTipText("Realizar prueba de conexi\u00F3n con los par\u00E1metros especificados");
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						IDatabase db = connectToServer();
						LoginDialog.this.connection = null;
						new SwingWorker<Void, Void>() {
				            @Override
				            protected Void doInBackground() throws Exception {
				            	btnTest.setEnabled(false);
				            	btnTest.setText("Conectando...");
				            	try {
				            		LoginDialog.this.connection = db.getConnection();
				            	}
				            	catch (SQLException e) {
									StringBuilder sb = new StringBuilder();
									// Change Width of the panel with CSS as the JLabel supports HTML formatting
									// In case the error message is too big
									sb.append("<html><body><p style='width: 300px;'>");
									sb.append(e.getLocalizedMessage());
									
									JOptionPane.showMessageDialog(contentPanel, sb.toString(), 
											"Error de conexión a la base de datos", JOptionPane.ERROR_MESSAGE);
								} finally {
									if(LoginDialog.this.connection != null)
									{	
										fillDatabasesCombo();
										LoginDialog.this.connection.close();
									}
								
									btnTest.setEnabled(true);
									btnTest.setText("Probar conexión");
									if (LoginDialog.this.connection == null)
									{
										lblTestResults.setForeground(Color.RED);
										lblTestResults.setText("Error de conexión");
										okButton.setEnabled(false);
										cbDatabases.setModel(new DefaultComboBoxModel<String>());
										cbDatabases.setEnabled(false);
									}
								}
				            	return null;
				            }
				        }.execute();
					}
				});
				GridBagConstraints gbc_btnTest = new GridBagConstraints();
				gbc_btnTest.insets = new Insets(0, 0, 5, 5);
				gbc_btnTest.gridx = 0;
				gbc_btnTest.gridy = 5;
				pnConfiguration.add(btnTest, gbc_btnTest);
				{
					lblTestResults = new JLabel();
					lblTestResults.setFont(new Font("Tahoma", Font.BOLD, 15));
					GridBagConstraints gbc_lblTestResults = new GridBagConstraints();
					gbc_lblTestResults.fill = GridBagConstraints.BOTH;
					gbc_lblTestResults.insets = new Insets(0, 0, 5, 0);
					gbc_lblTestResults.gridx = 1;
					gbc_lblTestResults.gridy = 5;
					pnConfiguration.add(lblTestResults, gbc_lblTestResults);
				}
				{
					JLabel label = new JLabel("Base de datos:");
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.fill = GridBagConstraints.BOTH;
					gbc_label.insets = new Insets(0, 0, 0, 5);
					gbc_label.gridx = 0;
					gbc_label.gridy = 6;
					pnConfiguration.add(label, gbc_label);
				}
				{
					cbDatabases = new JComboBox<String>();
					cbDatabases.setToolTipText("Lista de bases de datos en el servidor seleccionado");
					cbDatabases.setEnabled(false);
					GridBagConstraints gbc_cbDatabases = new GridBagConstraints();
					gbc_cbDatabases.fill = GridBagConstraints.BOTH;
					gbc_cbDatabases.gridx = 1;
					gbc_cbDatabases.gridy = 6;
					pnConfiguration.add(cbDatabases, gbc_cbDatabases);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				okButton.setToolTipText("Comenzar la importaci\u00F3n del esquema de la base de datos");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						task = new ConnectionTask();
						task.execute();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	class ConnectionTask extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			okButton.setEnabled(false);		
			
			// Check if the connection is valid (In case the values of the fields have been altered after pressing Test)
			IDatabase database = LoginDialog.this.selectedDatabase = connectToServer();
			try {
				database.getConnection();
			} catch (SQLException e){
				StringBuilder sb = new StringBuilder();
				// Change Width of the panel with CSS as the JLabel supports HTML formatting
				// In case the error message is too big
				sb.append("<html><body><p style='width: 300px;'>");
				sb.append(e.getLocalizedMessage());
				JOptionPane.showMessageDialog(contentPanel, sb.toString(), 
						"Error de conexión a la base de datos", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			// select the database on the combo
			if(LoginDialog.this.selectedDatabase.setName(cbDatabases.getSelectedItem().toString()))
			{
				try {
					addNewDatabase();
				} catch (SQLException e)
				{
					JOptionPane.showMessageDialog(contentPanel, e.getLocalizedMessage(), 
							"Imposible añadir la base de datos", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			return null;
		}
		
		@Override
        public void done() {
			setCursor(null);
			okButton.setEnabled(true);
		}
	}
	
	public IDatabase connectToServer()
	{
		DBMS driver = DBMS.valueOf(cbDriver.getSelectedItem().toString());
		String server = txtServer.getText();
		String tempPort = txtPort.getText();
		int port;
		if(tempPort.trim().length()== 0 || tempPort == null)
		{
			port = -1;
		}
		else
		{
			try {
				port = Integer.valueOf(tempPort);
			} catch (NumberFormatException e)
			{
				port = -1;
			}
		}
		String username = txtUser.getText();
		char[] password = passwordField.getPassword();
		IDatabase databaseAccess = FactoryDatabaseAccess.getDatabaseAccess(driver, server, port, username, password);
		// Sends the password to the garbage collector as soon as possible to avoid security issues
		password = null;
		return databaseAccess;
	}
	
	public void fillDatabasesCombo()
	{
		this.selectedDatabase = connectToServer();
		List<String> databases = new ArrayList<String>();
		try {
			databases = selectedDatabase.getDatabasesOnThisServer();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(contentPanel, e.getLocalizedMessage(), 
					"No se puede cargar la lista de bases de datos", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println(databases);
		cbDatabases.setModel(new DefaultComboBoxModel<String>(databases.toArray(new String[0])));
		if (cbDatabases.getModel().getSize() > 0)
		{
			lblTestResults.setForeground(Color.green);
			lblTestResults.setText("OK");
			cbDatabases.setEnabled(true);
			okButton.setEnabled(true);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Se puede realizar la conexión al servidor, pero no se han detectado "
					+ "bases de datos. ¿Ha introducido el nombre de usuario y la contraseña?", "Conexión con éxito al servidor", JOptionPane.INFORMATION_MESSAGE);
			cbDatabases.setEnabled(false);
			okButton.setEnabled(false);
		}
	}
	
	private void addNewDatabase() throws SQLException
	{
		this.selectedDatabase.setName(cbDatabases.getSelectedItem().toString());
		Database db = this.selectedDatabase.getDatabaseInformation();
		
		sqliteManager.addNewDatabase(db);
		JOptionPane.showMessageDialog(contentPanel, "Base de datos añadida con éxito", "Operación finalizada", JOptionPane.INFORMATION_MESSAGE);
		this.parentApplication.triggerRefreshNavigator();
		LoginDialog.this.dispose();
	}
}
