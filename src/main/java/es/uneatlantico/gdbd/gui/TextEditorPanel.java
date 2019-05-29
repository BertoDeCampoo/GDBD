package es.uneatlantico.gdbd.gui;

import javax.swing.JPanel;
import javax.swing.undo.UndoManager;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Button;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JEditorPane;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class TextEditorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3107235514653954415L;
	private UndoManager undoManager = new UndoManager();
	private JScrollPane scrlEditor;
	private JTextArea txtEditor;
	private JPanel pnControls;
	private JButton btnSave, btnUndo, btnRedo;
	private JScrollPane scrlPreview;
	private JEditorPane editorPreview;
	private JPanel pnCenter;
	private JLabel lblVistaPreviahtml;
	private JLabel lblEditorDeDocumentacin;
	private JButton btnVisorHtml;

	/**
	 * Create the panel.
	 */
	public TextEditorPanel() {

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
			scrlEditor.setColumnHeaderView(getLblEditorDeDocumentacin());
		}
		return scrlEditor;
	}
	private JTextArea getTxtEditor() {
		if (txtEditor == null) {
			txtEditor = new JTextArea();
			txtEditor.setLineWrap(true);
			txtEditor.setFont(new Font("Consolas", Font.PLAIN, 16));
			txtEditor.getDocument().addUndoableEditListener(undoManager);
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
			ImageIcon icon = new ImageIcon("save.png");
			btnSave = new JButton("", new ImageIcon(TextEditorPanel.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		}
		return btnSave;
	}
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton("");
			btnUndo.setIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
			btnUndo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TextEditorPanel.this.undoManager.undo();
				}
			});
		}
		return btnUndo;
	}
	private JButton getBtnRedo() {
		if (btnRedo == null) {
			btnRedo = new JButton("");
			btnRedo.setIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
			btnRedo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TextEditorPanel.this.undoManager.redo();
				}
			});
		}
		return btnRedo;
	}
	private JScrollPane getScrlPreview() {
		if (scrlPreview == null) {
			scrlPreview = new JScrollPane(getEditorPreview());
			scrlPreview.setColumnHeaderView(getLblVistaPreviahtml());
		}
		return scrlPreview;
	}
	private JEditorPane getEditorPreview() {
		if (editorPreview == null) {
			editorPreview = new JEditorPane();
			editorPreview.setEditable(false);
		}
		return editorPreview;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new GridLayout(0, 2, 0, 0));
			pnCenter.add(getScrlEditor());
			pnCenter.add(getScrlPreview());
		}
		return pnCenter;
	}
	private JLabel getLblVistaPreviahtml() {
		if (lblVistaPreviahtml == null) {
			lblVistaPreviahtml = new JLabel("Vista previa (HTML)");
		}
		return lblVistaPreviahtml;
	}
	private JLabel getLblEditorDeDocumentacin() {
		if (lblEditorDeDocumentacin == null) {
			lblEditorDeDocumentacin = new JLabel("Editor de documentaci\u00F3n:");
		}
		return lblEditorDeDocumentacin;
	}
	private JButton getBtnVisorHtml() {
		if (btnVisorHtml == null) {
			btnVisorHtml = new JButton("Visor HTML");
			btnVisorHtml.setSelectedIcon(new ImageIcon(TextEditorPanel.class.getResource("/com/sun/javafx/scene/web/skin/OrderedListNumbers_16x16_JFX.png")));
		}
		return btnVisorHtml;
	}
}
