package gui.uneatlantico.es;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataaccess.uneatlantico.es.DBMS;
import dataaccess.uneatlantico.es.FactoryDatabaseAccess;
import dataaccess.uneatlantico.es.IDatabase;
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
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class LoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403555622808592584L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtServer;
	private JTextField txtDatabase;
	private JTextField txtUser;
	private JPasswordField passwordField;
	private JLabel lblTestResults;
	private JButton okButton;
	private JComboBox<DBMS> cbDriver;
	private JTextField txtPort;
	
	private IDatabase selectedDatabase;

	/**
	 * Create the dialog.
	 */
	public LoginDialog(JFrame owner) {
		super (owner);
		
		initGUI();
	}
	private void initGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginDialog.class.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));
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
				txtServer.setText("DESKTOP-M9PG788");
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
				GridBagConstraints gbc_txtPort = new GridBagConstraints();
				gbc_txtPort.insets = new Insets(0, 0, 5, 0);
				gbc_txtPort.fill = GridBagConstraints.BOTH;
				gbc_txtPort.gridx = 1;
				gbc_txtPort.gridy = 2;
				pnConfiguration.add(txtPort, gbc_txtPort);
				txtPort.setColumns(10);
			}
			{
				JLabel lblDatabase = new JLabel("Base de datos:");
				GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
				gbc_lblDatabase.fill = GridBagConstraints.BOTH;
				gbc_lblDatabase.insets = new Insets(0, 0, 5, 5);
				gbc_lblDatabase.gridx = 0;
				gbc_lblDatabase.gridy = 3;
				pnConfiguration.add(lblDatabase, gbc_lblDatabase);
			}
			{
				txtDatabase = new JTextField();
				txtDatabase.setText("SQLEXPRESS");
				GridBagConstraints gbc_txtDatabase = new GridBagConstraints();
				gbc_txtDatabase.insets = new Insets(0, 0, 5, 0);
				gbc_txtDatabase.fill = GridBagConstraints.BOTH;
				gbc_txtDatabase.gridx = 1;
				gbc_txtDatabase.gridy = 3;
				pnConfiguration.add(txtDatabase, gbc_txtDatabase);
				txtDatabase.setColumns(10);
			}
			{
				JLabel lblUser = new JLabel("Usuario:");
				GridBagConstraints gbc_lblUser = new GridBagConstraints();
				gbc_lblUser.fill = GridBagConstraints.BOTH;
				gbc_lblUser.insets = new Insets(0, 0, 5, 5);
				gbc_lblUser.gridx = 0;
				gbc_lblUser.gridy = 4;
				pnConfiguration.add(lblUser, gbc_lblUser);
			}
			{
				txtUser = new JTextField();
				txtUser.setText("sa");
				GridBagConstraints gbc_txtUser = new GridBagConstraints();
				gbc_txtUser.insets = new Insets(0, 0, 5, 0);
				gbc_txtUser.fill = GridBagConstraints.BOTH;
				gbc_txtUser.gridx = 1;
				gbc_txtUser.gridy = 4;
				pnConfiguration.add(txtUser, gbc_txtUser);
				txtUser.setColumns(10);
			}
			{
				JLabel lblPassword = new JLabel("Contrase\u00F1a:");
				GridBagConstraints gbc_lblPassword = new GridBagConstraints();
				gbc_lblPassword.fill = GridBagConstraints.BOTH;
				gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblPassword.gridx = 0;
				gbc_lblPassword.gridy = 5;
				pnConfiguration.add(lblPassword, gbc_lblPassword);
			}
			{
				passwordField = new JPasswordField();
				GridBagConstraints gbc_passwordField = new GridBagConstraints();
				gbc_passwordField.insets = new Insets(0, 0, 5, 0);
				gbc_passwordField.fill = GridBagConstraints.BOTH;
				gbc_passwordField.gridx = 1;
				gbc_passwordField.gridy = 5;
				pnConfiguration.add(passwordField, gbc_passwordField);
			}
			{
				JButton btnTest = new JButton("Probar conexi\u00F3n");
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						IDatabase db = connectToDatabase();
						Connection conn = null;
						try {
							conn = db.getConnection();
							if(conn != null)
							{
								lblTestResults.setForeground(Color.green);
								lblTestResults.setText("OK");
								okButton.setEnabled(true);								
							}
							db.getConnection();
							//JOptionPane.showMessageDialog(contentPanel, "Se ha realizado la conexión", "Conexión establecida", JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(contentPanel, e.getLocalizedMessage(), "Error de conexión a la base de datos", JOptionPane.ERROR_MESSAGE);
						}
						finally {
							if (conn == null)
							{
								lblTestResults.setForeground(Color.RED);
								lblTestResults.setText("Error de conexión");
								okButton.setEnabled(false);
							}
						}
					}
				});
				GridBagConstraints gbc_btnTest = new GridBagConstraints();
				gbc_btnTest.fill = GridBagConstraints.VERTICAL;
				gbc_btnTest.insets = new Insets(0, 0, 0, 5);
				gbc_btnTest.gridx = 0;
				gbc_btnTest.gridy = 6;
				pnConfiguration.add(btnTest, gbc_btnTest);
			}
			{
				lblTestResults = new JLabel();
				lblTestResults.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblTestResults = new GridBagConstraints();
				gbc_lblTestResults.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblTestResults.gridx = 1;
				gbc_lblTestResults.gridy = 6;
				pnConfiguration.add(lblTestResults, gbc_lblTestResults);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						LoginDialog.this.selectedDatabase = connectToDatabase();
						LoginDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						LoginDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public IDatabase getSelectedDatabase()
	{
		return this.selectedDatabase;
	}
	
	public IDatabase connectToDatabase()
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
		String database = txtDatabase.getText();
		String username = txtUser.getText();
		char[] password = passwordField.getPassword();
		return FactoryDatabaseAccess.GetDatabaseAccess(driver, server, port, database, username, password);
	}
}
