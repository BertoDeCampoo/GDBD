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

public class ExportReportDialog extends JDialog {

	private static final long serialVersionUID = -2657081449688463345L;
	private final JPanel contentPanel = new JPanel();
	private SQLiteManager sqliteManager;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			ReportExportDialog dialog = new ReportExportDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * Create the dialog.
	 */
	public ExportReportDialog(SQLiteManager sqliteManager) {
		this.sqliteManager = sqliteManager;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
			try {
				String filePath = file.getCanonicalPath();
				System.out.println(filePath);
				jasperReport = JasperCompileManager.compileReport(filePath);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
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
		}
	}

}
