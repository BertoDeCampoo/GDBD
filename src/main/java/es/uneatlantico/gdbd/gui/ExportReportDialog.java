package es.uneatlantico.gdbd.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import es.uneatlantico.gdbd.persistence.SQLiteManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import es.uneatlantico.es.gdbd.reports.ReportFormat;
import java.awt.Toolkit;

public class ExportReportDialog extends JDialog {

	private static final long serialVersionUID = -2657081449688463345L;
	private final JPanel contentPanel = new JPanel();
	private SQLiteManager sqliteManager;
	private JTextField txtPath;

	/**
	 * Create the dialog.
	 */
	public ExportReportDialog(SQLiteManager sqliteManager) {
		this.sqliteManager = sqliteManager;
		initGUI();
	}
	private void initGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExportReportDialog.class.getResource("/net/sf/jasperreports/view/images/print.GIF")));
		setTitle("Exportar documentaci\u00F3n");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 178, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblFormat = new JLabel("Formato del archivo:");
			GridBagConstraints gbc_lblFormat = new GridBagConstraints();
			gbc_lblFormat.fill = GridBagConstraints.VERTICAL;
			gbc_lblFormat.anchor = GridBagConstraints.EAST;
			gbc_lblFormat.insets = new Insets(0, 0, 5, 5);
			gbc_lblFormat.gridx = 0;
			gbc_lblFormat.gridy = 0;
			contentPanel.add(lblFormat, gbc_lblFormat);
		}
		{
			JComboBox<ReportFormat> cbFormat = new JComboBox<ReportFormat>();
			cbFormat.setModel(new DefaultComboBoxModel<ReportFormat>(ReportFormat.values()));
			GridBagConstraints gbc_cbFormat = new GridBagConstraints();
			gbc_cbFormat.fill = GridBagConstraints.HORIZONTAL;
			gbc_cbFormat.insets = new Insets(0, 0, 5, 0);
			gbc_cbFormat.gridx = 1;
			gbc_cbFormat.gridy = 0;
			contentPanel.add(cbFormat, gbc_cbFormat);
		}
		{
			JLabel lblPath = new JLabel("Ruta:");
			GridBagConstraints gbc_lblPath = new GridBagConstraints();
			gbc_lblPath.fill = GridBagConstraints.VERTICAL;
			gbc_lblPath.anchor = GridBagConstraints.WEST;
			gbc_lblPath.insets = new Insets(0, 0, 5, 5);
			gbc_lblPath.gridx = 0;
			gbc_lblPath.gridy = 1;
			contentPanel.add(lblPath, gbc_lblPath);
		}
		{
			JPanel pnPath = new JPanel();
			pnPath.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			GridBagConstraints gbc_pnPath = new GridBagConstraints();
			gbc_pnPath.insets = new Insets(0, 0, 5, 0);
			gbc_pnPath.fill = GridBagConstraints.BOTH;
			gbc_pnPath.gridx = 1;
			gbc_pnPath.gridy = 1;
			contentPanel.add(pnPath, gbc_pnPath);
			pnPath.setLayout(new BoxLayout(pnPath, BoxLayout.X_AXIS));
			{
				txtPath = new JTextField();
				txtPath.setBorder(null);
				txtPath.setEditable(false);
				pnPath.add(txtPath);
				txtPath.setColumns(10);
			}
			{
				JButton btnExaminar = new JButton("Examinar...");
				btnExaminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Load the JFileChooser
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Seleccione la carpeta en la que guardar el archivo");
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fileChooser.setCurrentDirectory(null);
						 
						int userSelection = fileChooser.showSaveDialog(ExportReportDialog.this);
						 
						if (userSelection == JFileChooser.APPROVE_OPTION) {						    
						    txtPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
						}
					}
				});
				pnPath.add(btnExaminar);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Generar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ExportReportDialog.this.generateReport();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void generateReport()
	{
		// Compile jrxml file.
		JasperReport jasperReport;
		
		try {
			//jasperReport = JasperCompileManager.compileReport("D:/Escritorio/JReports/MyReports/ReportTFG.jrxml");
			File file = new File(
					getClass().getClassLoader().getResource("DatabaseReport.jrxml").getFile()
				);
			
			String filePath = file.getCanonicalPath();
			System.out.println(filePath);
			jasperReport = JasperCompileManager.compileReport(filePath);
			
			// Parameters for report
			Map<String, Object> parameters = new HashMap<String, Object>();
		
       		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, sqliteManager.getConnection());
       	 
       	    
            // Make sure the output directory exists.
            File outDir = new File("C:/jasperoutput");
            outDir.mkdirs();
      
            // Export to PDF.
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/jasperoutput/InformeBBDD.pdf");
			System.out.println("Done!");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
