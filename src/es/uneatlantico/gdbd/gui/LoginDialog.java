package es.uneatlantico.gdbd.gui;

import javax.swing.*;
import es.uneatlantico.gdbd.dataaccess.*;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 403555622808592584L;
	private final javax.swing.JPanel contentPanel = new JPanel();
	private javax.swing.JTextField txtServer;
	private javax.swing.JTextField txtUser;
	private javax.swing.JPasswordField passwordField;
	private javax.swing.JButton okButton;
	private javax.swing.JComboBox<DBMS> cbDriver;
	private javax.swing.JTextField txtPort;
	private javax.swing.JComboBox<String> cbDatabases;
	private javax.swing.JLabel lblTestResults;
	private IDatabase selectedDatabase;

	/**
	 * Create the dialog.
	 * @param owner
	 */
	public LoginDialog(javax.swing.JFrame owner) {
		// TODO - implement LoginDialog.LoginDialog
		throw new UnsupportedOperationException();
	}

	private void initGUI() {
		// TODO - implement LoginDialog.initGUI
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtains the database the user has selected to login
	 * @return the given database
	 */
	public IDatabase getLogonDatabase() {
		// TODO - implement LoginDialog.getLogonDatabase
		throw new UnsupportedOperationException();
	}

	public IDatabase connectToServer() {
		// TODO - implement LoginDialog.connectToServer
		throw new UnsupportedOperationException();
	}

	public void fillDatabasesCombo() {
		// TODO - implement LoginDialog.fillDatabasesCombo
		throw new UnsupportedOperationException();
	}

	public boolean requiredFieldsFilled() {
		// TODO - implement LoginDialog.requiredFieldsFilled
		throw new UnsupportedOperationException();
	}

}