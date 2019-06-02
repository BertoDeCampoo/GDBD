package es.uneatlantico.gdbd.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class HTMLViewer extends JDialog {

	private static final long serialVersionUID = -8328017730576270617L;
	private final JPanel contentPanel = new JPanel();
	private final String text;
	
	/**
	 * Create the dialog.
	 */
	public HTMLViewer(String htmlText) {
		this.text = htmlText;
		initGUI();
	}
	private void initGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HTMLViewer.class.getResource("/com/sun/javafx/scene/web/skin/FontColor_16x16_JFX.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Visor HTML");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JEditorPane pane=new JEditorPane();
				pane.setFont(new Font("Consolas", Font.PLAIN, 13));
				pane.setContentType("text/html");
				pane.setText(text);
				pane.setEditable(false);
				scrollPane.setViewportView(pane);
			}
			{
				JLabel lblVisorHtml = new JLabel("Visor HTML");
				scrollPane.setColumnHeaderView(lblVisorHtml);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("Cerrar");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				closeButton.setActionCommand("Cancel");
				buttonPane.add(closeButton);
				getRootPane().setDefaultButton(closeButton);
			}
		}
	}

}
