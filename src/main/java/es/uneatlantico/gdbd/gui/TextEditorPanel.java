package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import es.uneatlantico.gdbd.persistence.SQLiteManager;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextEditorPanel extends JPanel implements DocumentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3107235514653954415L;
	
	private SQLiteManager sqliteManager;
	/**
	 * ID of the current element under edition (Example: ID of the database, ID of the table or ID of the column)
	 */
	private int currentID;
	private String currentElementType;
	private String currentText = "";
	private boolean elementModified = false;
	private UndoManager undoManager = new UndoManager();
	private JScrollPane scrlEditor;
	private JTextArea txtEditor;
	private JPanel pnControls;
	private JButton btnSave, btnUndo, btnRedo;
	private JPanel pnCenter;
	private JLabel lblEditorDeDocumentacin;
	private JButton btnVisorHtml;
	private JPanel pnHeader;
	private JLabel lblCurrentElement;	

	/**
	 * Create the panel.
	 */
	public TextEditorPanel(SQLiteManager sqliteManager) {
		this.sqliteManager = sqliteManager;
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout(0, 0));
		add(getPnControls(), BorderLayout.NORTH);
		add(getPnCenter(), BorderLayout.CENTER);
	}
	private JScrollPane getScrlEditor() {
		if (scrlEditor == null) {
			scrlEditor = new JScrollPane(getTxtEditor());
			scrlEditor.setColumnHeaderView(getPnHeader());
		}
		return scrlEditor;
	}
	
	int i=17;
    int j=90;
    boolean ctrlPressed=false;
    boolean zPressed=false;
    boolean yPressed=false;
	
	private JTextArea getTxtEditor() {
		if (txtEditor == null) {
			txtEditor = new JTextArea();
			txtEditor.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					System.out.println("pre"+e.getKeyCode());
			        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			        {
			            ctrlPressed=true;
			        }

			        if(e.getKeyCode() == KeyEvent.VK_Z)
			        {
			            zPressed=true;
			        }
			        
			        if(e.getKeyCode() == KeyEvent.VK_Y)
			        {
			            yPressed=true;
			        }

			        if(ctrlPressed && zPressed)
			        {
			        	if(TextEditorPanel.this.undoManager.canUndo())
						{
							TextEditorPanel.this.undoManager.undo();
							// Enable depending on whether you can undo again
							TextEditorPanel.this.getBtnUndo().setEnabled(TextEditorPanel.this.undoManager.canUndo());
						}
			        }
			        if(ctrlPressed && yPressed)
			        {
			        	if(TextEditorPanel.this.undoManager.canRedo())
						{
							TextEditorPanel.this.undoManager.redo();
							// Enable depending on whether you can undo again
							TextEditorPanel.this.getBtnRedo().setEnabled(TextEditorPanel.this.undoManager.canRedo());
						}
			        }
				}
				
				public void keyReleased(KeyEvent e) {
			        //System.out.println("re"+e.getKeyChar());

			        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			        {
			            ctrlPressed=false;
			        }

			        if(e.getKeyCode() == KeyEvent.VK_Z)
			        {
			            zPressed=false;
			        }
			        
			        if(e.getKeyCode() == KeyEvent.VK_Y)
			        {
			            zPressed=false;
			        }
			    }
			});
			txtEditor.setLineWrap(true);
			txtEditor.setFont(new Font("Consolas", Font.PLAIN, 16));
			txtEditor.getDocument().addUndoableEditListener(undoManager);
			txtEditor.getDocument().addDocumentListener(this);
		}
		return txtEditor;
	}
	private JPanel getPnControls() {
		if (pnControls == null) {
			pnControls = new JPanel();
			pnControls.setLayout(new BoxLayout(pnControls, BoxLayout.X_AXIS));
			pnControls.add(getBtnSave());
			pnControls.add(getBtnUndo());
			pnControls.add(getBtnRedo());
			pnControls.add(getBtnVisorHtml());
		}
		return pnControls;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("", new ImageIcon(TextEditorPanel.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
			btnSave.setEnabled(false);
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (currentElementType.equals("BBDD"))
						{
							TextEditorPanel.this.sqliteManager.changeDatabaseDescription(currentID, TextEditorPanel.this.getTxtEditor().getText());
						}
						else if (currentElementType.equals("TABLA"))
						{
							TextEditorPanel.this.sqliteManager.changeTableDescription(currentID, TextEditorPanel.this.getTxtEditor().getText());
						}
						else if (currentElementType.equals("COLUMNA"))
						{
							TextEditorPanel.this.sqliteManager.changeColumnDescription(currentID, TextEditorPanel.this.getTxtEditor().getText());
						}
						else
							return;
					} catch (Exception spanishInquisition) {
						JOptionPane.showMessageDialog(TextEditorPanel.this, spanishInquisition.getLocalizedMessage(), "No se puede actualizar la descripción", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					getBtnSave().setEnabled(false);
					TextEditorPanel.this.elementModified = false;
					JOptionPane.showMessageDialog(TextEditorPanel.this, "Se ha actualizado la descripción del elemento seleccionado", "Descripción actualizada", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnSave;
	}
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton("");
			btnUndo.setToolTipText("Deshacer (Ctrl+Z)");
			btnUndo.setEnabled(false);
			btnUndo.setIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
			btnUndo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(TextEditorPanel.this.undoManager.canUndo())
					{
						TextEditorPanel.this.undoManager.undo();
						// Enable depending on whether you can undo again
						TextEditorPanel.this.getBtnUndo().setEnabled(TextEditorPanel.this.undoManager.canUndo());
					}
				}
			});
		}
		return btnUndo;
	}
	private JButton getBtnRedo() {
		if (btnRedo == null) {
			btnRedo = new JButton("");
			btnRedo.setToolTipText("Rehacer (Ctrl+Y)");
			btnRedo.setEnabled(false);
			btnRedo.setIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
			btnRedo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(TextEditorPanel.this.undoManager.canRedo())
					{
						TextEditorPanel.this.undoManager.redo();
						// Enable depending on whether you can redo again
						TextEditorPanel.this.getBtnRedo().setEnabled(TextEditorPanel.this.undoManager.canRedo());
					}
					
				}
			});
		}
		return btnRedo;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new BorderLayout(0, 0));
			pnCenter.add(getScrlEditor());
		}
		return pnCenter;
	}
	private JLabel getLblEditorDeDocumentacin() {
		if (lblEditorDeDocumentacin == null) {
			lblEditorDeDocumentacin = new JLabel("Descripci\u00F3n de ");
			lblEditorDeDocumentacin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblEditorDeDocumentacin;
	}
	private JButton getBtnVisorHtml() {
		if (btnVisorHtml == null) {
			btnVisorHtml = new JButton("Visor HTML");
			btnVisorHtml.setEnabled(false);
			btnVisorHtml.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Load the HTML viewer
					HTMLViewer htmlViewer = new HTMLViewer(getTxtEditor().getText());
					htmlViewer.setVisible(true);
				}
			});
			btnVisorHtml.setSelectedIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/OrderedListNumbers_16x16_JFX.png")));
		}
		return btnVisorHtml;
	}
	private JPanel getPnHeader() {
		if (pnHeader == null) {
			pnHeader = new JPanel();
			pnHeader.setLayout(new BoxLayout(pnHeader, BoxLayout.X_AXIS));
			pnHeader.add(getLblEditorDeDocumentacin());
			pnHeader.add(getLblCurrentElement());
		}
		return pnHeader;
	}
	private JLabel getLblCurrentElement() {
		if (lblCurrentElement == null) {
			lblCurrentElement = new JLabel("<No se ha seleccionado ning\u00FAn elemento>                   ");
			lblCurrentElement.setFont(new Font("Tahoma", Font.ITALIC, 15));
		}
		return lblCurrentElement;
	}
	
	/**
	 * Changes the current editing field to the given database
	 * @param databaseID  ID of the database to edit
	 */
	public void editDatabase(int databaseID)
	{
		String newText;
		if (discardChanges())
		{
			newText = sqliteManager.getDatabaseDescription(databaseID);
			this.currentText = newText;
			this.currentElementType = "BBDD";
			this.currentID = databaseID;
			getLblCurrentElement().setText("Base de Datos \'" + sqliteManager.getDatabaseName(databaseID) + "\'  ");
		}
		else 
		{
			return;
		}
		
		getTxtEditor().setText(newText);
		changeSelectedElement();
	}
	
	public void editTable(int tableID)
	{
		String newText;
		// If the contents of the current element have been modified, ask whether to change the current element
		if (discardChanges())
		{
			newText = sqliteManager.getTableDescription(tableID);
			this.currentText = newText;
			this.currentElementType = "TABLA";
			this.currentID = tableID;
			getLblCurrentElement().setText("Tabla \'" + sqliteManager.getTableName(tableID) + "\'  ");
		}
		else
		{
			return;
		}
		
		getTxtEditor().setText(newText);
		changeSelectedElement();
	}
	
	public void editColumn(int columnID)
	{
		String newText;
		// If the contents of the current element have been modified, ask whether to change the current element
		if (discardChanges())
		{
			newText = sqliteManager.getColumnDescription(columnID);
			this.currentText = newText;
			this.currentElementType = "COLUMNA";
			this.currentID = columnID;
			getLblCurrentElement().setText("Columna \'" + sqliteManager.getColumnName(columnID) + "\'  ");
		}
		else 
		{
			return;
		}
		
		getTxtEditor().setText(newText);
		changeSelectedElement();
	}
	
	private boolean discardChanges()
	{
		String newText = getTxtEditor().getText();
		// If the contents of the current element have been modified, ask whether to change the current element
		if (this.currentText == null)
			this.currentText = "";
		if (newText == null)
			newText = "";
		if ((!(this.currentText.equals(newText))) && this.elementModified)
		{
			int response = JOptionPane.showConfirmDialog(null, "¿Descartar cambios?", "Ha modificado la descripción. Si continúa perderá los cambios no guardados",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (response == JOptionPane.NO_OPTION)
				return false;
		}
		return true;
	}
	
	public void reset()
	{
		getLblCurrentElement().setText("<No se ha seleccionado ning\u00FAn elemento>");
	}
	
	private void changeSelectedElement()
	{
		this.elementModified = false;
		undoManager.discardAllEdits();
		getBtnUndo().setEnabled(false);
		getBtnRedo().setEnabled(false);
		getBtnSave().setEnabled(false);
		getBtnVisorHtml().setEnabled(getTxtEditor().getText().length()>0);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		this.elementModified = true;
		getBtnUndo().setEnabled(true);
		getBtnRedo().setEnabled(true);
		getBtnSave().setEnabled(true);
		getBtnVisorHtml().setEnabled(getTxtEditor().getText().length()>0);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.elementModified = true;
		getBtnUndo().setEnabled(true);
		getBtnRedo().setEnabled(true);
		getBtnSave().setEnabled(true);
		getBtnVisorHtml().setEnabled(getTxtEditor().getText().length()>0);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.elementModified = true;
		getBtnUndo().setEnabled(true);
		getBtnRedo().setEnabled(true);
		getBtnSave().setEnabled(true);
		getBtnVisorHtml().setEnabled(getTxtEditor().getText().length()>0);
	}
}
