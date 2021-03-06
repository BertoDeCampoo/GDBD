package es.uneatlantico.gdbd.gui;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
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
import java.beans.PropertyChangeEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

import es.uneatlantico.gdbd.persistence.SQLiteManager;
import es.uneatlantico.gdbd.reports.FactoryReport;
import es.uneatlantico.gdbd.reports.IReport;
import es.uneatlantico.gdbd.reports.ReportFormat;
import es.uneatlantico.gdbd.util.FileNameCleaner;
import es.uneatlantico.gdbd.util.PathResolver;

import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.JProgressBar;

public class ExportReportDialog extends JDialog {
	
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
	private JLabel lblNombreDeArchivo;
	private JTextPane txtFileName;
	private JProgressBar progressBar;
	private JLabel lblGenerating;
	
	private GenerateReportTask task;

	/**
	 * Create the dialog.
	 */
	public ExportReportDialog(SQLiteManager sqliteManager) {
		this.sqliteManager = sqliteManager;
		initGUI();
	}
	private void initGUI() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExportReportDialog.class.getResource("/net/sf/jasperreports/view/images/print.GIF")));
		setTitle("Exportar documentaci\u00F3n");
		setBounds(100, 100, 512, 200);
		getContentPane().add(getPnControls(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
	}

	private JPanel getPnControls() {
		if (pnControls == null) {
			pnControls = new JPanel();
			pnControls.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnControls.add(getBtnAccept());
		}
		return pnControls;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
			GridBagLayout gbl_pnCenter = new GridBagLayout();
			gbl_pnCenter.columnWidths = new int[]{198, 264, 0};
			gbl_pnCenter.rowHeights = new int[]{24, 0, 0, 0, 0};
			gbl_pnCenter.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_pnCenter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
			GridBagConstraints gbc_lblNombreDeArchivo = new GridBagConstraints();
			gbc_lblNombreDeArchivo.anchor = GridBagConstraints.WEST;
			gbc_lblNombreDeArchivo.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombreDeArchivo.gridx = 0;
			gbc_lblNombreDeArchivo.gridy = 1;
			pnCenter.add(getLblNombreDeArchivo(), gbc_lblNombreDeArchivo);
			GridBagConstraints gbc_txtFileName = new GridBagConstraints();
			gbc_txtFileName.insets = new Insets(0, 0, 5, 0);
			gbc_txtFileName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtFileName.gridx = 1;
			gbc_txtFileName.gridy = 1;
			pnCenter.add(getTxtFileName(), gbc_txtFileName);
			GridBagConstraints gbc_lblPath = new GridBagConstraints();
			gbc_lblPath.anchor = GridBagConstraints.WEST;
			gbc_lblPath.insets = new Insets(0, 0, 5, 5);
			gbc_lblPath.gridx = 0;
			gbc_lblPath.gridy = 2;
			pnCenter.add(getLblPath(), gbc_lblPath);
			GridBagConstraints gbc_pnPath = new GridBagConstraints();
			gbc_pnPath.insets = new Insets(0, 0, 5, 0);
			gbc_pnPath.fill = GridBagConstraints.BOTH;
			gbc_pnPath.gridx = 1;
			gbc_pnPath.gridy = 2;
			pnCenter.add(getPnPath(), gbc_pnPath);
			GridBagConstraints gbc_lblGenerating = new GridBagConstraints();
			gbc_lblGenerating.anchor = GridBagConstraints.WEST;
			gbc_lblGenerating.insets = new Insets(0, 0, 0, 5);
			gbc_lblGenerating.gridx = 0;
			gbc_lblGenerating.gridy = 3;
			pnCenter.add(getLblGenerating(), gbc_lblGenerating);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.gridx = 1;
			gbc_progressBar.gridy = 3;
			pnCenter.add(getProgressBar(), gbc_progressBar);
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
			cbFormats.setToolTipText("Formato en el que generar la documentaci\u00F3n");
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
			txtPath.setToolTipText("Ruta en la que guardar el archivo");
			txtPath.setEditable(false);
			txtPath.setText(PathResolver.getCurrentDirectory());
			txtPath.setColumns(10);
		}
		return txtPath;
	}
	private JButton getBtnPath() {
		if (btnPath == null) {
			btnPath = new JButton("Examinar...");
			btnPath.setToolTipText("Seleccionar la ruta");
			btnPath.addActionListener(new ActionListener() {
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
		}
		return btnPath;
	}
	private JButton getBtnAccept() {
		if (btnAccept == null) {
			btnAccept = new JButton("Aceptar");
			btnAccept.setToolTipText("Generar la documentaci\u00F3n");
			btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Path path = Paths.get(getTxtPath().getText());
					if (Files.exists(path))
						try {
							String finalPath = getTxtPath().getText() + System.getProperty("file.separator") + FileNameCleaner.cleanString(getTxtFileName().getText());
							task = new GenerateReportTask(finalPath);
							task.execute();
						} catch (Exception re) {
							JOptionPane.showMessageDialog(ExportReportDialog.this, re.getLocalizedMessage(), "No se puede exportar", JOptionPane.ERROR_MESSAGE);
						}
					else
					{
						JOptionPane.showMessageDialog(ExportReportDialog.this, "La ruta seleccionada no es v�lida", "Ruta no v�lida", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnAccept;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
	    if (!task.isDone()) {
	        int progress = task.getProgress();
	        if (progress == 0) {
	            progressBar.setIndeterminate(true);
	            progressBar.setString("Procesando...");
	        } else {
	            progressBar.setIndeterminate(false); 
	            progressBar.setString(null);
	            progressBar.setValue(progress);
	        }
	    }
	}
	private JLabel getLblNombreDeArchivo() {
		if (lblNombreDeArchivo == null) {
			lblNombreDeArchivo = new JLabel("Nombre de archivo:");
		}
		return lblNombreDeArchivo;
	}
	private JTextPane getTxtFileName() {
		if (txtFileName == null) {
			txtFileName = new JTextPane();
			txtFileName.setToolTipText("Nombre del archivo a generar");
			txtFileName.setText(es.uneatlantico.gdbd.util.Configuration.getDefaultReportFilename());
		}
		return txtFileName;
	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setVisible(false);
			progressBar.setIndeterminate(true);
		}
		return progressBar;
	}
	private JLabel getLblGenerating() {
		if (lblGenerating == null) {
			lblGenerating = new JLabel("Generando. Por favor, espere");
			lblGenerating.setVisible(false);
		}
		return lblGenerating;
	}
	
	class GenerateReportTask extends SwingWorker<Void, Void> {
		private String exportPath;
		private String resultPath;
		
        public GenerateReportTask(String finalPath) {
			this.exportPath = finalPath;
		}

		@Override
        public Void doInBackground() {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			getLblGenerating().setVisible(true);
			getProgressBar().setVisible(true);
			getBtnAccept().setEnabled(false);
			IReport report = FactoryReport.getReport(ReportFormat.valueOf(getCbFormats().getSelectedItem().toString()));
			try {
				this.resultPath = report.export(es.uneatlantico.gdbd.util.Configuration.getDatabaseReportFilename(), this.exportPath, ExportReportDialog.this.sqliteManager.getConnection());
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ExportReportDialog.this, e.getLocalizedMessage(), 
						"No se puede conectar con la base de datos SQLite (" + es.uneatlantico.gdbd.util.Configuration.getDefaultSQLiteFilename() + ")", 
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(ExportReportDialog.this, e.getLocalizedMessage(), "Error al acceder al archivo del informe (" + es.uneatlantico.gdbd.util.Configuration.getDatabaseReportFilename() + ")", JOptionPane.ERROR_MESSAGE);
			}
			return null;
        }
        
        @Override
        public void done() {
        	getBtnAccept().setEnabled(true);
        	getLblGenerating().setVisible(false);
			getProgressBar().setVisible(false);
            Toolkit.getDefaultToolkit().beep();
            setCursor(null);
            if(isCancelled())
            	JOptionPane.showMessageDialog(null, "Generaci�n del documento cancelada" + this.exportPath, "Tarea cancelada", JOptionPane.INFORMATION_MESSAGE);
            else
            	JOptionPane.showMessageDialog(null, "Puede acceder a �l "
            			+ "en la siguiente ruta: " + this.resultPath, "Documento generado con �xito", JOptionPane.INFORMATION_MESSAGE);
        }
	}
}
