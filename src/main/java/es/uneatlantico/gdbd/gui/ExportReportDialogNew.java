package es.uneatlantico.gdbd.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

import es.uneatlantico.es.gdbd.reports.FactoryReport;
import es.uneatlantico.es.gdbd.reports.IReport;
import es.uneatlantico.es.gdbd.reports.ReportFormat;
import es.uneatlantico.gdbd.persistence.SQLiteManager;
import net.sf.jasperreports.engine.JRException;

public class ExportReportDialogNew extends JDialog {
	
	private SQLiteManager sqliteManager;
	private static final long serialVersionUID = 5266394846240984786L;
	private JPanel pnControls;
	private JPanel pnCenter;
	private JLabel lblFormat;
	private JComboBox<ReportFormat> cbFormats;
	private JLabel lblPath;
	private JPanel pnPath;
	private JTextField txtPath;
	private JButton btnPath;
	private JButton btnAccept;
	private JButton btnCancel;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ExportReportDialogNew dialog = new ExportReportDialogNew();
//					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//					dialog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the dialog.
	 */
	public ExportReportDialogNew(SQLiteManager sqliteManager) {
		this.sqliteManager = sqliteManager;
		initGUI();
	}
	private void initGUI() {
		setTitle("Exportar documentaci\u00F3n");
		setBounds(100, 100, 450, 300);
		getContentPane().add(getPnControls(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
	}

	private JPanel getPnControls() {
		if (pnControls == null) {
			pnControls = new JPanel();
			pnControls.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnControls.add(getBtnAccept());
			pnControls.add(getBtnCancel());
		}
		return pnControls;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			GridBagLayout gbl_pnCenter = new GridBagLayout();
			gbl_pnCenter.columnWidths = new int[]{200, 0, 0};
			gbl_pnCenter.rowHeights = new int[]{24, 0, 0};
			gbl_pnCenter.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_pnCenter.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			pnCenter.setLayout(gbl_pnCenter);
			GridBagConstraints gbc_lblFormat = new GridBagConstraints();
			gbc_lblFormat.insets = new Insets(0, 0, 5, 5);
			gbc_lblFormat.anchor = GridBagConstraints.WEST;
			gbc_lblFormat.gridx = 0;
			gbc_lblFormat.gridy = 0;
			pnCenter.add(getLblFormat(), gbc_lblFormat);
			GridBagConstraints gbc_cbFormats = new GridBagConstraints();
			gbc_cbFormats.insets = new Insets(0, 0, 5, 0);
			gbc_cbFormats.fill = GridBagConstraints.HORIZONTAL;
			gbc_cbFormats.gridx = 1;
			gbc_cbFormats.gridy = 0;
			pnCenter.add(getCbFormats(), gbc_cbFormats);
			GridBagConstraints gbc_lblPath = new GridBagConstraints();
			gbc_lblPath.anchor = GridBagConstraints.WEST;
			gbc_lblPath.insets = new Insets(0, 0, 0, 5);
			gbc_lblPath.gridx = 0;
			gbc_lblPath.gridy = 1;
			pnCenter.add(getLblPath(), gbc_lblPath);
			GridBagConstraints gbc_pnPath = new GridBagConstraints();
			gbc_pnPath.fill = GridBagConstraints.BOTH;
			gbc_pnPath.gridx = 1;
			gbc_pnPath.gridy = 1;
			pnCenter.add(getPnPath(), gbc_pnPath);
		}
		return pnCenter;
	}
	private JLabel getLblFormat() {
		if (lblFormat == null) {
			lblFormat = new JLabel("Formato de archivo:");
		}
		return lblFormat;
	}
	private JComboBox<ReportFormat> getCbFormats() {
		if (cbFormats == null) {
			cbFormats = new JComboBox<ReportFormat>();
			cbFormats.setModel(new DefaultComboBoxModel<ReportFormat>(ReportFormat.values()));
		}
		return cbFormats;
	}
	private JLabel getLblPath() {
		if (lblPath == null) {
			lblPath = new JLabel("Ruta:");
		}
		return lblPath;
	}
	private JPanel getPnPath() {
		if (pnPath == null) {
			pnPath = new JPanel();
			pnPath.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			pnPath.setLayout(new BoxLayout(pnPath, BoxLayout.X_AXIS));
			pnPath.add(getTxtPath());
			pnPath.add(getBtnPath());
		}
		return pnPath;
	}
	private JTextField getTxtPath() {
		if (txtPath == null) {
			txtPath = new JTextField();
			txtPath.setEditable(false);
			txtPath.setText("No seleccionada");
			txtPath.setColumns(10);
		}
		return txtPath;
	}
	private JButton getBtnPath() {
		if (btnPath == null) {
			btnPath = new JButton("Examinar...");
			btnPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Load the JFileChooser
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Seleccione la carpeta en la que guardar el archivo");
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setCurrentDirectory(null);
					 
					int userSelection = fileChooser.showSaveDialog(ExportReportDialogNew.this);
					 
					if (userSelection == JFileChooser.APPROVE_OPTION) {						    
					    txtPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		return btnPath;
	}
	private JButton getBtnAccept() {
		if (btnAccept == null) {
			btnAccept = new JButton("Aceptar");
			btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					IReport report = FactoryReport.getReport(ReportFormat.valueOf(getCbFormats().getSelectedItem().toString()));
					//String path = getTxtPath().getText();
					Path path = Paths.get(getTxtPath().getText());
					if (Files.exists(path))
						try {
							report.export("DatabaseReport.jrxml", getTxtPath().getText(), ExportReportDialogNew.this.sqliteManager.getConnection());
						} catch (Exception re) {
							JOptionPane.showMessageDialog(ExportReportDialogNew.this, re.getLocalizedMessage(), "No se puede exportar", JOptionPane.ERROR_MESSAGE);
						}
					else
					{
						JOptionPane.showMessageDialog(ExportReportDialogNew.this, "La ruta seleccionada no es v�lida", "Ruta no v�lida", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnAccept;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancelar");
		}
		return btnCancel;
	}
}
